package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.EventConverter;
import com.bogdansukonnov.eclinic.dao.EventDao;
import com.bogdansukonnov.eclinic.entity.Event;
import com.bogdansukonnov.eclinic.entity.EventStatus;
import com.bogdansukonnov.eclinic.entity.Prescription;
import com.bogdansukonnov.eclinic.security.SecurityContextAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class EventServiceImplCancelAndDeleteTest {

    @Mock
    EventDao eventDao;

    @Mock
    EventConverter converter;

    @Mock
    SecurityContextAdapter securityContextAdapter;

    @Mock
    MessagingService messagingService;

    private EventService eventService;
    private Event scheduledEvent;
    private Event secondScheduledEvent;
    private Event cancelledEvent;
    private Event completedEvent;

    @BeforeEach
    void setUp() {

        eventService = new EventServiceImpl(eventDao, converter, securityContextAdapter, messagingService);

        List<Event> eventList = new ArrayList<>();

        scheduledEvent = spy(new Event());
        scheduledEvent.setEventStatus(EventStatus.SCHEDULED);
        eventList.add(scheduledEvent);

        secondScheduledEvent = spy(new Event());
        secondScheduledEvent.setEventStatus(EventStatus.SCHEDULED);
        eventList.add(secondScheduledEvent);

        Event event = new Event();
        event.setEventStatus(EventStatus.CANCELED);
        cancelledEvent = spy(event);
        eventList.add(cancelledEvent);

        completedEvent = spy(new Event());
        completedEvent.setEventStatus(EventStatus.COMPLETED);
        eventList.add(completedEvent);

        when(eventDao.getAll(any(Prescription.class))).thenReturn(eventList);
    }

    @Test
    public void cancelAllScheduledTest() {

        String reason = "meaningful reason";

        eventService.cancelAllScheduled(new Prescription(), reason);

        // set cancelled status to scheduled events
        verify(scheduledEvent, times(1)).setEventStatus(EventStatus.CANCELED);
        verify(secondScheduledEvent, times(1)).setEventStatus(EventStatus.CANCELED);
        // do not set cancelled status to events in other statuses
        verify(cancelledEvent, times(0)).setEventStatus(EventStatus.CANCELED);
        verify(cancelledEvent, times(0)).setEventStatus(EventStatus.CANCELED);
        // set cancel reason
        verify(scheduledEvent, times(1)).setCancelReason(reason);
        verify(secondScheduledEvent, times(1)).setCancelReason(reason);
        // update events in database
        verify(eventDao, times(1)).update(scheduledEvent);
        verify(eventDao, times(1)).update(secondScheduledEvent);
        // send message to queue
        verify(messagingService, times(1)).send(any());
    }

    @Test
    void deleteAllScheduledTest() {

        eventService.deleteAllScheduled(new Prescription());

        verify(eventDao, times(1)).delete(scheduledEvent);
        verify(eventDao, times(1)).delete(secondScheduledEvent);
        verify(eventDao, times(0)).delete(cancelledEvent);
        verify(eventDao, times(0)).delete(completedEvent);
        // send message to queue
        verify(messagingService, times(1)).send(any());
    }
}