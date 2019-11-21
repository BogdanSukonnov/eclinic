package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Event;
import com.bogdansukonnov.eclinic.entity.Prescription;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventDao extends TableDao<Event> {
    @Override
    String getQueryConditions(String search, Long parentId);

    List<Event> getAll(Prescription prescription);

    Optional<Event> getFirstCompleted(Prescription prescription);

    Long getTotalFiltered(String search, boolean showCompleted, LocalDateTime startDate, LocalDateTime endDate
            , Long prescriptionId);

    List<Event> getAll(String search, String orderField, int offset, int limit,
                       boolean showCompleted, LocalDateTime startDate, LocalDateTime endDate,
                       Long prescriptionId);
}
