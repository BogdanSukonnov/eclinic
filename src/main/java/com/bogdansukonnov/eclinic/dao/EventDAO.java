package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Event;
import com.bogdansukonnov.eclinic.entity.EventStatus;
import com.bogdansukonnov.eclinic.entity.Prescription;
import com.bogdansukonnov.eclinic.entity.TableData;
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
        String countQueryStr = "Select count (t.id) from Event t";
        if (!showCompleted) {
            countQueryStr += " where t.eventStatus=:status";
        }
        Query countQuery = getCurrentSession().createQuery(countQueryStr);
        if (!showCompleted) {
            countQuery.setParameter("status", EventStatus.SCHEDULED);
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
        String queryStr = "from Event t";
        if (!showCompleted) {
            queryStr += " where t.eventStatus=:status";
        }
        queryStr += " order by " + orderField;
        Query query = getCurrentSession().createQuery(queryStr)
                .setFirstResult(start)
                .setMaxResults(length);
        if (!showCompleted) {
            query.setParameter("status", EventStatus.SCHEDULED);
        }
        List<Event> data = query.list();

        // to table data
        TableData<Event> tableData = new TableData<>(data, 0, countResults, countResults);

        return tableData;
    }
}
