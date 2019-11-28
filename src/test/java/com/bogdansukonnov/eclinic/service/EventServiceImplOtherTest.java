package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dto.EventInfoListDto;
import com.bogdansukonnov.eclinic.dto.RequestEventTableDto;
import com.bogdansukonnov.eclinic.dto.TableDataDto;
import com.bogdansukonnov.eclinic.entity.AppUser;
import com.bogdansukonnov.eclinic.entity.Event;
import com.bogdansukonnov.eclinic.entity.EventStatus;
import com.bogdansukonnov.eclinic.exceptions.EventStatusUpdateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class EventServiceImplOtherTest extends EventServiceImplTest {

    private static final String ORDER_FIELD = "dateTime";
    private long id = 7L;
    private int version = 9;
    private Event scheduledEvent;
    private Event canceledEvent;
    private Event completedEvent;

    @BeforeEach
    void setUp() {

        super.initEventService();

        scheduledEvent = new Event();
        scheduledEvent.setVersion(version);
        scheduledEvent.setEventStatus(EventStatus.SCHEDULED);

        canceledEvent = new Event();
        canceledEvent.setVersion(version);
        canceledEvent.setEventStatus(EventStatus.CANCELED);

        completedEvent = new Event();
        completedEvent.setVersion(version);
        completedEvent.setEventStatus(EventStatus.COMPLETED);

    }

    @Test
    void getEventTableTest() {

        int draw = 3;

        RequestEventTableDto data = new RequestEventTableDto();
        data.setDraw(draw);
        data.setShowCompleted(false);
        data.setLimit(0);
        data.setOffset(0);
        data.setOrderDirection("");
        data.setOrderField("");
        data.setParentId(0L);
        data.setSearch("");

        LocalDateTime startDate = LocalDateTime.parse("2020-01-01T10:00");
        LocalDateTime endDate = LocalDateTime.parse("2020-01-02T10:00");

        when(eventDao.getAll("", ORDER_FIELD, 0, 0, false, startDate, endDate, 0L))
                .thenReturn(Collections.singletonList(scheduledEvent));
        scheduledEvent.setDateTime(startDate);

        TableDataDto tableDataDto = eventService.getEventTable(data, startDate, endDate);

        verify(eventDao).getAll("", ORDER_FIELD, 0, 0, false, startDate, endDate, 0L);
        verify(converter).toDto(scheduledEvent, false);
        assertEquals(tableDataDto.getDraw(), draw);

    }

    @Test
    void updateStatusEmptyReasonCancelTest() {

        assertThrows(EventStatusUpdateException.class, () ->
                eventService.updateStatus(id, EventStatus.CANCELED, null, null)
        );

        assertThrows(EventStatusUpdateException.class, () ->
                eventService.updateStatus(id, EventStatus.CANCELED, "", null)
        );
    }

    @Test
    void updateStatusCompletedStatusTest() {

        when(eventDao.findOne(id)).thenReturn(completedEvent);

        assertThrows(EventStatusUpdateException.class, () ->
                eventService.updateStatus(id, EventStatus.COMPLETED, null, null)
        );

        when(eventDao.findOne(id)).thenReturn(canceledEvent);

        assertThrows(EventStatusUpdateException.class, () ->
                eventService.updateStatus(id, EventStatus.COMPLETED, null, null)
        );
    }

    @Test
    void updateStatusWrongVersionTest() {

        when(eventDao.findOne(id)).thenReturn(scheduledEvent);

        // wrong version test
        assertThrows(EventStatusUpdateException.class, () ->
                eventService.updateStatus(id, EventStatus.COMPLETED, null, (int) id)
        );

    }

    @Test
    void updateStatusCompleteScheduledTest() throws EventStatusUpdateException {

        Event scheduledEventSpy = spy(scheduledEvent);
        when(eventDao.findOne(id)).thenReturn(scheduledEventSpy);

        eventService.updateStatus(id, EventStatus.COMPLETED, null, version);

        verify(scheduledEventSpy).setEventStatus(EventStatus.COMPLETED);

    }

    @Test
    void updateStatusCancelScheduledTest() throws EventStatusUpdateException {

        Event scheduledEventSpy = spy(scheduledEvent);
        when(eventDao.findOne(id)).thenReturn(scheduledEventSpy);

        AppUser appUser = new AppUser();
        when(securityContextAdapter.getCurrentUser()).thenReturn(appUser);

        String reason = "The reason";
        eventService.updateStatus(id, EventStatus.CANCELED, reason, version);

        verify(scheduledEventSpy).setEventStatus(EventStatus.CANCELED);
        verify(scheduledEventSpy).setCancelReason(reason);
        verify(scheduledEventSpy).setNurse(appUser);
        verify(eventDao).update(scheduledEventSpy);
        verify(messagingService).send(anyString());

    }

    @Test
    void eventsInfoTest() {

        when(eventDao.getAll(null, ORDER_FIELD, 0, 100, false,
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                LocalDateTime.of(LocalDate.now(), LocalTime.MAX), null))
                .thenReturn(Collections.singletonList(scheduledEvent));

        EventInfoListDto eventsDto = eventService.eventsInfo();

        verify(eventDao).getAll(null, ORDER_FIELD, 0, 100, false,
                LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                LocalDateTime.of(LocalDate.now(), LocalTime.MAX), null);

        verify(converter).toInfoDto(scheduledEvent);

        assertEquals(1, eventsDto.getEvents().size());

    }

    @Test
    void patientEventsTest() {
        Long patientId = 97L;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.ofEpochSecond(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + 500,
                1,
                ZoneOffset.MAX);
        when(eventDao.getAllScheduledByPatient(patientId, startDate, endDate)).thenReturn(Collections.singletonList(scheduledEvent));

        eventService.patientEvents(patientId, startDate, endDate);

        verify(eventDao).getAllScheduledByPatient(patientId, startDate, endDate);
        verify(converter).toEventDateDto(scheduledEvent);
    }

}
