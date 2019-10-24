package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Event;
import com.bogdansukonnov.eclinic.entity.Prescription;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    protected String getOrderField() {
        return "dateTime";
    }
}
