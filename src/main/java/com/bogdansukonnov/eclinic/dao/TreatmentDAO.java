package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Treatment;
import com.bogdansukonnov.eclinic.entity.TreatmentType;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TreatmentDAO extends AbstractDAO<Treatment> {

    public TreatmentDAO() {
        setClazz(Treatment.class);
    }

    public List<Treatment> getAll(TreatmentType treatmentType) {
        String queryStr = "from Treatment t where t.type=:treatmentType order by " + getOrderField();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("treatmentType", treatmentType);
        return query.list();
    }

    @Override
    protected String getOrderField() {
        return "name";
    }
}
