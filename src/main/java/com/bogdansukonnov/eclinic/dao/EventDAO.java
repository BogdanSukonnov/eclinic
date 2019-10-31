package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Event;
import com.bogdansukonnov.eclinic.entity.EventStatus;
import com.bogdansukonnov.eclinic.entity.Prescription;
import com.bogdansukonnov.eclinic.entity.TableData;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class EventDAO extends AbstractDAO<Event> {

    public EventDAO() {
        setClazz(Event.class);
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
//        query.setParameter("prescription", prescription);
        return query.list();
    }

    public List<Event> getAll(Long prescriptionId) {
        String queryStr = "from Event e where e.prescription.id=:prescriptionId order by " + getOrderField();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("prescriptionId", prescriptionId);
        return query.list();
    }

    @Override
    protected String getOrderField() {
        return "dateTime";
    }

    public TableData<Event> getTableData(SortBy sortBy, Integer start, Integer length, boolean showCompleted
            , String search, LocalDateTime startDate, LocalDateTime endDate) {
        // count rows
        String countQueryStr = "Select count (t.id) from Event t" +
                " where t.dateTime >= :startDate and t.dateTime <= :endDate";
        if (!showCompleted) {
            countQueryStr += " and t.eventStatus=:status";
        }
        if (!StringUtils.isBlank(search)) {
            countQueryStr += " and lower(t.patient.fullName) like lower(:search)";
        }
        Query countQuery = getCurrentSession().createQuery(countQueryStr);
        countQuery.setParameter("startDate", startDate);
        countQuery.setParameter("endDate", endDate);
        if (!showCompleted) {
            countQuery.setParameter("status", EventStatus.SCHEDULED);
        }
        if (!StringUtils.isBlank(search)) {
            countQuery.setParameter("search", "%" + search + "%");
        }
        Long countResults = (Long) countQuery.uniqueResult();

        // get sort column
        String orderField;
        if (sortBy == SortBy.NAME) {
            orderField = getOrderField();
        }
        else {
            orderField = "createdDateTime desc";
        }

        // do query
        String queryStr = "from Event t where t.dateTime >= :startDate and t.dateTime <= :endDate";
        if (!showCompleted) {
            queryStr += " and t.eventStatus=:status";
        }
        if (!StringUtils.isBlank(search)) {
            queryStr += " and lower(t.patient.fullName) like lower(:search)";
        }
        queryStr += " order by " + orderField;
        Query query = getCurrentSession().createQuery(queryStr)
                .setFirstResult(start)
                .setMaxResults(length);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        if (!showCompleted) {
            query.setParameter("status", EventStatus.SCHEDULED);
        }
        if (!StringUtils.isBlank(search)) {
            query.setParameter("search", "%" + search + "%");
        }
        List<Event> data = query.list();

        // to table data
        TableData<Event> tableData = new TableData<>(data, 0, countResults, countResults);

        return tableData;
    }
}
