package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class EventServiceImplCreateEventsTest extends EventServiceImplTest {

    @Mock
    Prescription prescription;
    @Mock
    Patient patient;
    @Mock
    TimePattern timePattern;
    @Mock
    Treatment treatment;
    @Mock
    AppUser appUser;

    @BeforeEach
    void setUp() {

        super.initEventService();

    }

    private void addEvent(List<Event> eventList, EventStatus status, LocalDateTime dateTime) {
        Event event = new Event();
        event.setDateTime(dateTime);
        event.setEventStatus(status);
        eventList.add(event);
    }

    private void addItem(List<TimePatternItem> itemList, Short dayOfCycle, LocalTime time) {
        TimePatternItem item = new TimePatternItem();
        item.setId(new Random().nextLong());
        item.setTime(time);
        item.setDayOfCycle(dayOfCycle);
        itemList.add(item);
    }

    @Test
    void createEventsTest() {

        List<Event> eventList = new ArrayList<>();
        addEvent(eventList, EventStatus.COMPLETED, LocalDateTime.parse("2020-01-01T10:00"));
        addEvent(eventList, EventStatus.SCHEDULED, LocalDateTime.parse("2020-01-01T18:00"));
        addEvent(eventList, EventStatus.COMPLETED, LocalDateTime.parse("2020-01-04T10:00"));
        addEvent(eventList, EventStatus.SCHEDULED, LocalDateTime.parse("2020-01-04T18:00"));

        List<TimePatternItem> itemList = new ArrayList<>();
        addItem(itemList, (short) 0, LocalTime.parse("10:00"));
        addItem(itemList, (short) 0, LocalTime.parse("18:00"));

        String dosage = "dosage";

        when(prescription.getTimePattern()).thenReturn(timePattern);
        when(prescription.getPatient()).thenReturn(patient);
        when(prescription.getDosage()).thenReturn(dosage);
        when(prescription.getTreatment()).thenReturn(treatment);
        when(securityContextAdapter.getCurrentUser()).thenReturn(appUser);
        when(timePattern.getItems()).thenReturn(itemList);
        when(eventDao.getAll(prescription)).thenReturn(eventList);
        when(prescription.getStartDate()).thenReturn(LocalDateTime.parse("2020-01-01T00:00"));
        when(prescription.getEndDate()).thenReturn(LocalDateTime.parse("2020-01-08T23:59"));
        when(timePattern.getCycleLength()).thenReturn((short) 3);
        when(timePattern.getIsWeekCycle()).thenReturn(false);

        eventService.createEvents(prescription);

        // create events
        verify(eventDao, times(3)).create(any());

        // send message to queue
        verify(messagingService).send(any());
    }
}