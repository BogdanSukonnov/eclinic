package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.EventConverter;
import com.bogdansukonnov.eclinic.dao.EventDAO;
import com.bogdansukonnov.eclinic.dao.SortBy;
import com.bogdansukonnov.eclinic.dto.EventDTO;
import com.bogdansukonnov.eclinic.dto.TableDataDTO;
import com.bogdansukonnov.eclinic.entity.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class EventService {

    private EventDAO eventDAO;
    private ModelMapper modelMapper;
    private EventConverter converter;

    /**
     * <p>inter service communication
     * checks if prescription has events</p>
     * @param prescription prescription of events
     * @return boolean
     */
    @Transactional(readOnly = true)
    public boolean hasEvents(Prescription prescription) {
        return !eventDAO.getAll(prescription).isEmpty();
    }

    /**
     * <p>finds event by it's id</p>
     * @param id event id
     * @return EventDTO
     */
    @Transactional(readOnly = true)
    public EventDTO getOne(Long id) {
        return converter.toDTO(eventDAO.findOne(id));
    }

    /**
     * <p>Deletes app planned event for prescription</p>
     * @param prescription the prescription of deleted events
     */
    @Transactional
    public void deleteAllPlanned(Prescription prescription) {
        eventDAO.getAll(prescription).stream()
                .filter(event -> event.getEventStatus().equals(EventStatus.PLANNED))
                .forEach(event -> eventDAO.delete(event));
    }

    /**
     * <p>Creates all planned events for prescription from max(now, last completed event)
     * to the end of prescription period, which starts from min(now, first completed event)
     * and continues for prescription.duration days
     * </p>
     * @param prescription the prescription of created events
     */
    @Transactional
    public void createEvents(Prescription prescription) {
        final TimePattern timePattern = prescription.getTimePattern();
        final List<TimePatternItem> items = timePattern.getItems();
        // sorted from the database, but its critical, so sort again
        Collections.sort(items);

        // period starts from today or from the first completed event
        List<Event> events = eventDAO.getAll(prescription);

        Optional<Event> firstCompleted = events.stream()
                .filter(event -> event.getEventStatus().equals(EventStatus.DONE))
                .findAny();

        LocalDate periodStart = firstCompleted.isPresent()
                ? LocalDate.from(firstCompleted.get().getDateTime())
                : LocalDate.now();


        // event should not be created sooner than the last completed event and now
        // find last completed event
        ArrayList<Event> reversedEvents = new ArrayList<>();
        Collections.copy(events, reversedEvents);
        Collections.reverse(reversedEvents);
        Optional<Event> lastCompleted = reversedEvents.stream()
                .filter(event -> event.getEventStatus().equals(EventStatus.DONE))
                .findAny();

        LocalDateTime notSooner = lastCompleted.isPresent()
                ? lastCompleted.get().getDateTime().isBefore(LocalDateTime.now())
                    ? LocalDateTime.now()
                    : lastCompleted.get().getDateTime()
                : LocalDateTime.now();

        // end of period - the day, prescription stops
        final LocalDateTime endDate = LocalDateTime.of(periodStart.plusDays(prescription.getDuration())
                                        , LocalTime.MIN);

        // find all dates when to create events
        List<LocalDateTime> dates = patternDates(items, periodStart, endDate
                , timePattern.getCycleLength(), timePattern.getIsWeekCycle(), notSooner);

        // actually create all events
        for (LocalDateTime date : dates) {
            Event event = new Event();
            event.setDateTime(date);
            event.setEventStatus(EventStatus.PLANNED);
            event.setPatient(prescription.getPatient());
            event.setPrescription(prescription);
            event.setDosage(prescription.getDosage());
            event.setTimePattern(prescription.getTimePattern());
            event.setTreatment(prescription.getTreatment());
            eventDAO.create(event);
        }
    }

    /**
     * <p>Calculates all planned event dates for prescription</p>
     * @param items pattern items, holds dayOfCycle and time
     * @param periodStart start day of prescription
     * @param endDate end date of prescription (exclusive)
     * @param cycleLength length of pattern in days
     * @param isWeekCycle if true, cycle is 7 days long and starts at the beginning of the week
     * @param notSooner do not plan event before this moment
     * @return list of all planned event dates
     */
    public List<LocalDateTime> patternDates(List<TimePatternItem> items, LocalDate periodStart
            , LocalDateTime endDate, Short cycleLength, Boolean isWeekCycle, LocalDateTime notSooner) {

        LocalDate cycleStart = periodStart;
        if (isWeekCycle) {
            // weeks cycle starts last Monday
            // ToDo: what if the first day of week is Saturday?
            cycleStart = notSooner.toLocalDate().with(DayOfWeek.MONDAY);
        }
        // this variable brakes the infinite while loop
        LocalDateTime itemDate = cycleStart.atStartOfDay();
        // return value
        List<LocalDateTime> dates = new ArrayList<>();
        // repeat the pattern till the endDate
        while (items.size() > 0 && itemDate.isBefore(endDate)) {
            // loop thru the pattern
            for (TimePatternItem item : items) {
                itemDate = cycleStart.plusDays(item.getDayOfCycle()).atTime(item.getTime());
                if (itemDate.isBefore(LocalDateTime.of(cycleStart, LocalTime.of(0, 0)))
                    || itemDate.isBefore(notSooner)) {
                    continue;
                }
                if (itemDate.isAfter(endDate) || itemDate.isEqual(endDate)) {
                    break;
                }
                dates.add(itemDate);
            }
            // next iteration, move the cycle start
            cycleStart = cycleStart.plusDays(cycleLength);
        }
        return dates;
    }

    /**
     * <p>REST service. Generates data for the table of
     * all events with given filters</p>
     * @param data request parameter
     * @return TableDataDTO
     */
    @Transactional(readOnly = true)
    public TableDataDTO getTable(Map<String, String> data) {

        List<Event> events = eventDAO.getAll();

        List<EventDTO> list = events.stream()
                .map(event -> converter.toDTO(event))
                .collect(Collectors.toList());

        return new TableDataDTO<>(list
                , Integer.parseInt(data.get("draw")), list.size(), list.size());
    }

    /**
     * <p>REST service. Generates data for the table of given
     * prescription's events.</p>
     * @param data request parameter
     * @return TableDataDTO
     */
    @Transactional(readOnly = true)
    public TableDataDTO getTable(Long prescriptionId, Map<String, String> data) {

        List<Event> events = eventDAO.getAll(prescriptionId);

        List<EventDTO> list = events.stream()
                .map(event -> converter.toDTO(event))
                .collect(Collectors.toList());

        return new TableDataDTO<>(list
                , Integer.parseInt(data.get("draw")), list.size(), list.size());
    }

}
