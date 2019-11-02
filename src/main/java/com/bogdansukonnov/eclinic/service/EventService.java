package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.EventConverter;
import com.bogdansukonnov.eclinic.dao.EventDAOOld;
import com.bogdansukonnov.eclinic.dao.SortBy;
import com.bogdansukonnov.eclinic.dto.EventDTO;
import com.bogdansukonnov.eclinic.dto.TableDataDTO;
import com.bogdansukonnov.eclinic.entity.*;
import com.bogdansukonnov.eclinic.exceptions.EventStatusUpdateException;
import com.bogdansukonnov.eclinic.security.UserGetter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class EventService {

    private EventDAOOld eventDAO;
    private EventConverter converter;
    private UserGetter userGetter;

    /**
     * <p>Cross-service communication.Checks if prescription has events</p>
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
     * <p>Cancels all scheduled event for given prescription</p>
     * @param prescription the prescription
     */
    @Transactional
    public void cancelAllScheduled(Prescription prescription) {
        eventDAO.getAll(prescription).stream()
                .filter(event -> event.getEventStatus().equals(EventStatus.SCHEDULED))
                .forEach(event -> {
                    event.setEventStatus(EventStatus.CANCELED);
                    event.setCancelReason("Prescription canceled");
                    eventDAO.update(event);
                });
    }

    /**
     * <p>Creates all scheduled events for prescription from max(now, last completed event)
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
                .filter(event -> event.getEventStatus().equals(EventStatus.COMPLETED))
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
                .filter(event -> event.getEventStatus().equals(EventStatus.COMPLETED))
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
            event.setEventStatus(EventStatus.SCHEDULED);
            event.setPatient(prescription.getPatient());
            event.setPrescription(prescription);
            event.setDosage(prescription.getDosage());
            event.setTimePattern(prescription.getTimePattern());
            event.setTreatment(prescription.getTreatment());
            event.setDoctor(userGetter.getCurrentUser());
            eventDAO.create(event);
        }
    }

    /**
     * <p>Calculates all scheduled event dates for prescription</p>
     * @param items pattern items, holds dayOfCycle and time
     * @param periodStart start day of prescription
     * @param endDate end date of prescription (exclusive)
     * @param cycleLength length of pattern in days
     * @param isWeekCycle if true, cycle is 7 days long and starts at the beginning of the week
     * @param notSooner do not plan event before this moment
     * @return list of all scheduled event dates
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

        Integer draw = Integer.parseInt(data.get("draw"));
        Integer start = Integer.parseInt(data.get("start"));
        Integer length = Integer.parseInt(data.get("length"));
        boolean showCompleted = Boolean.parseBoolean(data.get("showCompleted"));
        String search = data.get("search[value]");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(data.get("startDate"), formatter);
        LocalDateTime endDate = LocalDateTime.parse(data.get("endDate"), formatter);

        TableData<Event> tableData = eventDAO.getTableData(SortBy.NAME, start, length, showCompleted
                , search, startDate, endDate);

        List<EventDTO> eventDTOS = tableData.getData().stream()
                .map(event -> converter.toDTO(event))
                .collect(Collectors.toList());

        return new TableDataDTO<>(eventDTOS, draw
                , tableData.getRecordsTotal(), tableData.getRecordsFiltered());
    }

    /**
     * <p>REST service. Generates data for the table of given
     * prescription's events.</p>
     * @param data request parameter
     * @return TableDataDTO
     */
    @Transactional(readOnly = true)
    public TableDataDTO getTable(Long prescriptionId, Map<String, String> data) {

        Integer draw = Integer.parseInt(data.get("draw"));
        Integer start = Integer.parseInt(data.get("start"));
        Integer length = Integer.parseInt(data.get("length"));
        String search = data.get("search");

        List<Event> events = eventDAO.getAll(prescriptionId);

        List<EventDTO> list = events.stream()
                .map(event -> converter.toDTO(event))
                .collect(Collectors.toList());

        return new TableDataDTO<>(list , draw, (long) list.size(), (long) list.size());
    }

    /**
     * <p>updates event status</p>
     * @param id event id
     * @param status new event status
     * @param cancelReason mandatory reason for cancel status
     */
    @Transactional
    public void updateStatus(Long id, EventStatus status, String cancelReason) throws EventStatusUpdateException {
        // check reason for cancel
        if (status.equals(EventStatus.CANCELED) && StringUtils.isBlank(cancelReason)) {
            throw new EventStatusUpdateException("Can't cancel event without reason.");
        }
        // check current status
        Event event = eventDAO.findOne(id);
        if (!event.getEventStatus().equals(EventStatus.SCHEDULED)) {
            throw new EventStatusUpdateException("Can't change event status. It's allready "
                    + event.getEventStatus());
        }
        // we're good to go
        event.setEventStatus(status);
        event.setCancelReason(status.equals(EventStatus.CANCELED) ? cancelReason : "");
        // save current user
        event.setNurse(userGetter.getCurrentUser());
        eventDAO.update(event);
    }

}
