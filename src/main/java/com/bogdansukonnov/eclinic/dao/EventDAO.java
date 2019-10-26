package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Event;
import com.bogdansukonnov.eclinic.entity.EventStatus;
import com.bogdansukonnov.eclinic.entity.Prescription;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

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
        query.setParameter("eventStatus", EventStatus.DONE);
        return query.uniqueResultOptional();
    }

    @Override
    protected String getOrderField() {
        return "dateTime";
    }
}
