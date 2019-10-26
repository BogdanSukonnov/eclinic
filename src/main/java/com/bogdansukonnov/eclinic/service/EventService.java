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

    @Transactional
    public List<EventDTO> getAll(Prescription prescription) {
        return eventDAO.getAll(prescription).stream()
                .map(event -> modelMapper.map(event, EventDTO.class))
                .collect(Collectors.toList());
    }

    public void createEvents(Prescription prescription) {
        final TimePattern timePattern = prescription.getTimePattern();
        final List<TimePatternItem> items = timePattern.getItems();
        // sorted from the database, but its critical, so sort again
        Collections.sort(items);

        // period starts from today or from the first completed event
        Optional<Event> firstCompleted = eventDAO.getFirstCompleted(prescription);
        LocalDate periodStart = firstCompleted.isPresent()
                ? LocalDate.from(firstCompleted.get().getDateTime())
                : LocalDate.now();

        final LocalDateTime endDate = LocalDateTime.from(periodStart.plusDays(prescription.getDuration()));

        Short cycleLength = timePattern.getCycleLength();
        Boolean isWeekCycle = timePattern.getIsWeekCycle();

        List<LocalDateTime> dates = patternDates(items, periodStart, endDate, cycleLength, isWeekCycle, LocalDateTime.now());

        for (LocalDateTime date : dates) {
            Event event = new Event();
            event.setDateTime(date);
            event.setEventStatus(EventStatus.PLANNED);
            event.setPatient(prescription.getPatient());
            event.setPrescription(prescription);
            eventDAO.create(event);
        }
    }

    public List<LocalDateTime> patternDates(List<TimePatternItem> items, LocalDate periodStart
            , LocalDateTime endDate, Short cycleLength, Boolean isWeekCycle, LocalDateTime now) {

        LocalDate cycleStart = periodStart;
        if (isWeekCycle) {
            // weeks cycle starts last Monday
            // ToDo: what if the first day of week is Saturday?
            cycleStart = now.toLocalDate().with(DayOfWeek.MONDAY);
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
                    || itemDate.isBefore(now)) {
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
