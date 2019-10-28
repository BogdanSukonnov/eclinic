package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.EventDAO;
import com.bogdansukonnov.eclinic.dto.EventDTO;
import com.bogdansukonnov.eclinic.entity.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventService {

    private EventDAO eventDAO;
    private ModelMapper modelMapper;

    /**
     * gets all events for given prescription
     * @param prescription prescription of events
     * @return list of EventDTO
     */
    @Transactional
    public List<EventDTO> getAll(Prescription prescription) {
        return eventDAO.getAll(prescription).stream()
                .map(event -> modelMapper.map(event, EventDTO.class))
                .collect(Collectors.toList());
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

}
