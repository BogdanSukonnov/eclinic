package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.EventConverter;
import com.bogdansukonnov.eclinic.dao.EventDao;
import com.bogdansukonnov.eclinic.security.SecurityContextAdapter;
import org.mockito.Mock;

public abstract class EventServiceImplTest {
    @Mock
    EventDao eventDao;
    @Mock
    EventConverter converter;
    @Mock
    SecurityContextAdapter securityContextAdapter;
    @Mock
    MessagingService messagingService;
    EventService eventService;

    void initEventService() {
        eventService = new EventServiceImpl(eventDao, converter, securityContextAdapter, messagingService);
    }

}
