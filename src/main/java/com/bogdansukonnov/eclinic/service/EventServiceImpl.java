package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.EventConverter;
import com.bogdansukonnov.eclinic.dao.EventDao;
import com.bogdansukonnov.eclinic.dto.EventDto;
import com.bogdansukonnov.eclinic.dto.EventInfoListDto;
import com.bogdansukonnov.eclinic.dto.RequestEventTableDto;
import com.bogdansukonnov.eclinic.dto.TableDataDto;
import com.bogdansukonnov.eclinic.entity.*;
import com.bogdansukonnov.eclinic.exceptions.EventStatusUpdateException;
import com.bogdansukonnov.eclinic.security.SecurityContextAdapter;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class EventServiceImpl implements EventService {

    private EventDao eventDao;
    private EventConverter converter;
    private SecurityContextAdapter securityContextAdapter;
    private MessagingService messagingService;

    /**
     * <p>finds event by it's id</p>
     *
     * @param id event id
     * @return EventDto
     */
    @Override
    @Transactional(readOnly = true)
    public EventDto getOne(Long id) {
        return converter.toDto(eventDao.findOne(id));
    }

    /**
     * <p>Cancels all scheduled event for given prescription</p>
     *
     * @param prescription the prescription
     * @param reason       the reason
     */
    @Override
    @Transactional
    public void cancelAllScheduled(Prescription prescription, String reason) {
        eventDao.getAll(prescription).stream()
                .filter(event -> event.getEventStatus().equals(EventStatus.SCHEDULED))
                .forEach(event -> {
                    event.setEventStatus(EventStatus.CANCELED);
                    event.setCancelReason(reason);
                    eventDao.update(event);
                });
        // send message about data update
        messagingService.send("update");
    }

    /**
     * <p>Cancels all scheduled event for given prescription</p>
     *
     * @param prescription the prescription
     */
    @Override
    @Transactional
    public void deleteAllScheduled(Prescription prescription) {
        eventDao.getAll(prescription).stream()
                .filter(event -> event.getEventStatus().equals(EventStatus.SCHEDULED))
                .forEach(event -> eventDao.delete(event));
        // send message about data update
        messagingService.send("update");
    }

    /**
     * <p>Creates all scheduled events for prescription from max(now, last completed event)
     * to the end of prescription period, which starts from min(now, first completed event)
     * and continues for prescription.duration days
     * </p>
     *
     * @param prescription the prescription of created events
     */
    @Override
    @Transactional
    public void createEvents(Prescription prescription) {
        final TimePattern timePattern = prescription.getTimePattern();
        final List<TimePatternItem> items = timePattern.getItems();
        // sorted from the database, but its critical, so sort again
        Collections.sort(items);

        List<Event> events = eventDao.getAll(prescription);

        // event should not be created sooner than the last completed event
        // find last completed event
        ArrayList<Event> reversedEvents = new ArrayList<>();
        Collections.copy(events, reversedEvents);
        Collections.reverse(reversedEvents);
        Optional<Event> lastCompleted = reversedEvents.stream()
                .filter(event -> event.getEventStatus().equals(EventStatus.COMPLETED))
                .findAny();

        LocalDateTime notSooner = lastCompleted.filter(value ->
                !value.getDateTime().isBefore(prescription.getStartDate()))
                .map(Event::getDateTime)
                .orElseGet(prescription::getStartDate);

        // find all dates when to create events
        List<LocalDateTime> dates = patternDates(items, prescription.getStartDate(), prescription.getEndDate()
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
            event.setDoctor(securityContextAdapter.getCurrentUser());
            eventDao.create(event);
        }
        // send message about data update
        messagingService.send("update");
    }

    /**
     * <p>Calculates all scheduled event dates for prescription</p>
     *
     * @param items       pattern items, holds dayOfCycle and time
     * @param periodStart start day of prescription
     * @param endDate     end date of prescription (exclusive)
     * @param cycleLength length of pattern in days
     * @param isWeekCycle if true, cycle is 7 days long and starts at the beginning of the week
     * @param notSooner   do not plan event before this moment
     * @return list of all scheduled event dates
     */
    @Override
    public List<LocalDateTime> patternDates(List<TimePatternItem> items, LocalDateTime periodStart
            , LocalDateTime endDate, Short cycleLength, Boolean isWeekCycle, LocalDateTime notSooner) {

        LocalDate cycleStart = LocalDate.from(periodStart);
        if (isWeekCycle) {
            // weeks cycle starts last Monday
            // ToDo: what if the first day of week is Saturday?
            cycleStart = periodStart.toLocalDate().with(DayOfWeek.MONDAY);
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
     *
     * @param data request parameter
     * @return TableDataDto
     */
    @Override
    @Transactional(readOnly = true)
    public TableDataDto getEventTable(RequestEventTableDto data, LocalDateTime startDate, LocalDateTime endDate) {

        String orderField = "dateTime";

        List<Event> events = eventDao.getAll(data.getSearch(), orderField, data.getOffset(), data.getLimit()
                , data.getShowCompleted(), startDate, endDate, data.getParentId());

        List<EventDto> eventDtoS = events.stream()
                .map(event -> converter.toDto(event))
                .collect(Collectors.toList());

        Long totalFiltered = eventDao.getTotalFiltered(data.getSearch(), data.getShowCompleted(), startDate,
                endDate, null);

        return new TableDataDto<>(eventDtoS, data.getDraw(), totalFiltered, totalFiltered);
    }

    /**
     * <p>updates event status</p>
     *
     * @param id           event id
     * @param status       new event status
     * @param cancelReason mandatory reason for cancel status
     * @param version      data version
     */
    @Override
    public void updateStatus(Long id, EventStatus status, String cancelReason, Integer version) throws EventStatusUpdateException {
        // check reason for cancel
        if (status.equals(EventStatus.CANCELED) && StringUtils.isBlank(cancelReason)) {
            throw new EventStatusUpdateException("Can't cancel event without reason.");
        }
        // check current status
        Event event = eventDao.findOne(id);
        if (!event.getEventStatus().equals(EventStatus.SCHEDULED)) {
            throw new EventStatusUpdateException("Can't change event status. It's allready "
                    + event.getEventStatus());
        }
        // check version
        if (!event.getVersion().equals(version)) {
            throw new EventStatusUpdateException("Can't change event status. Version conflict");
        }
        // we're good to go
        event.setEventStatus(status);
        event.setCancelReason(status.equals(EventStatus.CANCELED) ? cancelReason : "");
        // save current user
        event.setNurse(securityContextAdapter.getCurrentUser());
        eventDao.update(event);
        // send message about data update
        messagingService.send("update");
    }

    /**
     * return all today's scheduled events
     *
     * @return EventInfoListDto
     */
    @Override
    @Transactional(readOnly = true)
    public EventInfoListDto eventsInfo() {
        // request all scheduled today's events
        List<Event> events = eventDao.getAll(null, "dateTime", 0, 100, false,
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                LocalDateTime.of(LocalDate.now(), LocalTime.MAX), null);
        // convert to DTO
        EventInfoListDto eventsDto = new EventInfoListDto();
        eventsDto.setEvents(events.stream()
                .map(event -> converter.toInfoDto(event))
                .collect(Collectors.toList()));
        return eventsDto;
    }

}
