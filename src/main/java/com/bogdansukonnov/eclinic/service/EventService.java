package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dto.EventDto;
import com.bogdansukonnov.eclinic.dto.EventInfoListDto;
import com.bogdansukonnov.eclinic.dto.RequestEventTableDto;
import com.bogdansukonnov.eclinic.dto.TableDataDto;
import com.bogdansukonnov.eclinic.entity.EventStatus;
import com.bogdansukonnov.eclinic.entity.Prescription;
import com.bogdansukonnov.eclinic.entity.TimePatternItem;
import com.bogdansukonnov.eclinic.exceptions.EventStatusUpdateException;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventDto getOne(Long id);

    void cancelAllScheduled(Prescription prescription, String reason);

    void deleteAllScheduled(Prescription prescription);

    void createEvents(Prescription prescription);

    List<LocalDateTime> patternDates(List<TimePatternItem> items, LocalDateTime periodStart
            , LocalDateTime endDate, Short cycleLength, Boolean isWeekCycle, LocalDateTime notSooner);

    TableDataDto getEventTable(RequestEventTableDto data, LocalDateTime startDate, LocalDateTime endDate);

    void updateStatus(Long id, EventStatus status, String cancelReason, Integer version) throws EventStatusUpdateException;

    EventInfoListDto eventsInfo();
}
