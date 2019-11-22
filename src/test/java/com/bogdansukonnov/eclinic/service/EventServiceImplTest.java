package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.EventConverter;
import com.bogdansukonnov.eclinic.dao.EventDao;
import com.bogdansukonnov.eclinic.dto.RequestEventTableDto;
import com.bogdansukonnov.eclinic.dto.TableDataDto;
import com.bogdansukonnov.eclinic.entity.AppUser;
import com.bogdansukonnov.eclinic.entity.Event;
import com.bogdansukonnov.eclinic.entity.EventStatus;
import com.bogdansukonnov.eclinic.exceptions.EventStatusUpdateException;
import com.bogdansukonnov.eclinic.security.SecurityContextAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceImplTest {

    @Mock
    EventDao eventDao;

    @Mock
    EventConverter converter;

    @Mock
    SecurityContextAdapter securityContextAdapter;

    @Mock
    MessagingService messagingService;

    private EventService eventService;

    @BeforeEach
    void setUp() {

        eventService = new EventServiceImpl(eventDao, converter, securityContextAdapter, messagingService);

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

        Event event = new Event();
        event.setId(7L);

        when(eventDao.getAll("", "dateTime", 0, 0, false, startDate, endDate, 0L))
                .thenReturn(Collections.singletonList(event));


        TableDataDto tableDataDto = eventService.getEventTable(data, startDate, endDate);

        verify(eventDao).getAll("", "dateTime", 0, 0, false, startDate, endDate, 0L);
        verify(converter).toDto(event);
        assertEquals(tableDataDto.getDraw(), draw);

    }

    @Test
    void updateStatusTest() throws EventStatusUpdateException {

        long id = 7L;
        int version = 9;

        assertThrows(EventStatusUpdateException.class, () -> {
            eventService.updateStatus(id, EventStatus.CANCELED, null, null);
        });

        assertThrows(EventStatusUpdateException.class, () -> {
            eventService.updateStatus(id, EventStatus.CANCELED, "", null);
        });

        Event event = new Event();
        event.setVersion(version);
        event.setEventStatus(EventStatus.COMPLETED);

        when(eventDao.findOne(id)).thenReturn(event);

        assertThrows(EventStatusUpdateException.class, () -> {
            eventService.updateStatus(id, EventStatus.COMPLETED, null, null);
        });

        event.setEventStatus(EventStatus.CANCELED);

        assertThrows(EventStatusUpdateException.class, () -> {
            eventService.updateStatus(id, EventStatus.COMPLETED, null, null);
        });

        assertThrows(EventStatusUpdateException.class, () -> {
            eventService.updateStatus(id, EventStatus.COMPLETED, null, (int) id);
        });

        event.setEventStatus(EventStatus.SCHEDULED);

        Event spyEvent = spy(event);
        when(eventDao.findOne(id)).thenReturn(spyEvent);

        eventService.updateStatus(id, EventStatus.COMPLETED, null, version);

        verify(spyEvent).setEventStatus(EventStatus.COMPLETED);

        event.setEventStatus(EventStatus.SCHEDULED);

        AppUser appUser = new AppUser();
        when(securityContextAdapter.getCurrentUser()).thenReturn(appUser);

        Event spyEvent1 = spy(event);
        when(eventDao.findOne(id)).thenReturn(spyEvent1);

        String reason = "The reason";
        eventService.updateStatus(id, EventStatus.CANCELED, reason, version);

        verify(spyEvent).setEventStatus(EventStatus.CANCELED);
        verify(spyEvent).setCancelReason(reason);
        verify(spyEvent).setNurse(appUser);
        verify(eventDao).update(event);
        verify(messagingService).send(anyString());

    }

}
