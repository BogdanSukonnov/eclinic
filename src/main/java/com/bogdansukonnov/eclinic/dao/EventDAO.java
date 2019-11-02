package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Event;
import com.bogdansukonnov.eclinic.entity.EventStatus;
import com.bogdansukonnov.eclinic.entity.Prescription;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EventDAO extends AbstractTableDAO<Event> implements ITableDAO<Event> {

    public EventDAO() {
        setClazz(Event.class);
    }

    @Override
    public String getQueryConditions(String search) {
        String conditions = "";
        if (!StringUtils.isBlank(search)) {
            conditions += " where (lower(t.patient.fullName) like lower(:search))";
        }
        return conditions;
    }

    @Override
    protected String getOrderField() {
        return "dateTime";
    }

    public List<Event> getAll(Prescription prescription) {
        String queryStr = "from Event e where e.prescription=:prescription order by " + getOrderField();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("prescription", prescription);
        return query.list();
    }

    public Optional<Event> getFirstCompleted(Prescription prescription) {
        String queryStr = "from Event e where e.prescription=:prescription" +
                " and e.eventStatus=:eventStatus order by " + getOrderField();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("prescription", prescription);
        query.setParameter("eventStatus", EventStatus.COMPLETED);
        return query.uniqueResultOptional();
    }

    public List<Event> getAll() {
        String queryStr = "from Event e order by " + getOrderField();
        Query query = getCurrentSession().createQuery(queryStr);
        return query.list();
    }

    public Long getCount(String search, boolean showCompleted, LocalDateTime startDate, LocalDateTime endDate
        , Long prescriptionId) {
        String queryStr = "Select count (t.id) from " + getClazz().getName() + " t";
        queryStr += getQueryConditions(search, showCompleted, prescriptionId, startDate, endDate);
        Query query = getCurrentSession().createQuery(queryStr);
        setParameters(query, search, showCompleted, startDate, endDate, prescriptionId);
        return (Long) query.uniqueResult();
    }

    public List<Event> getAll(String search, String orderField, int offset, int limit,
            boolean showCompleted, LocalDateTime startDate, LocalDateTime endDate,
            Long prescriptionId) {
        String queryStr = "from Event t";
        queryStr += getQueryConditions(search, showCompleted, prescriptionId, startDate, endDate);
        Query query = getCurrentSession().createQuery(queryStr)
                .setFirstResult(offset)
                .setMaxResults(limit);
        setParameters(query, search, showCompleted, startDate, endDate, prescriptionId);
        return query.list();
    }

    private String getQueryConditions(String search, boolean showCompleted, Long prescriptionId,
                                      LocalDateTime startDate, LocalDateTime endDate) {
        List<String> conditionsList = new ArrayList<>();
        if (startDate != null && endDate != null) {
            conditionsList.add(" (t.dateTime >= :startDate and t.dateTime <= :endDate)");
        }
        if (!StringUtils.isBlank(search)) {
            conditionsList.add(" (lower(t.patient.fullName) like lower(:search))");
        }
        if (!showCompleted) {
            conditionsList.add(" (t.eventStatus=:status)");
        }
        if (prescriptionId != null) {
            conditionsList.add(" (t.prescription.id=:prescriptionId)");
        }
        String conditionsStr = "";
        if (conditionsList.size() > 0) {
            conditionsStr = " where" + String.join(" and ", conditionsList);
        }
        return conditionsStr;
    }

    private void setParameters(Query query, String search, boolean showCompleted,
                               LocalDateTime startDate, LocalDateTime endDate, Long prescriptionId) {
        if (!StringUtils.isBlank(search)) {
            query.setParameter("search", "%" + search + "%");
        }
        if (startDate != null && endDate != null) {
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
        }
        if (!showCompleted) {
            query.setParameter("status", EventStatus.SCHEDULED);
        }
        if (prescriptionId != null) {
            query.setParameter("prescriptionId", prescriptionId);
        }
    }

}
