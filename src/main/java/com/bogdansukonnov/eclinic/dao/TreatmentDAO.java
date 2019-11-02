package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Treatment;
import com.bogdansukonnov.eclinic.entity.TreatmentType;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TreatmentDAO extends AbstractDAO<Treatment> implements IDAO<Treatment> {

    public TreatmentDAO() {
        setClazz(Treatment.class);
    }

    public List<Treatment> getAll(TreatmentType treatmentType, String search) {
        boolean needSearch = search != null && !search.isEmpty();
        String queryStr = "from Treatment t where t.type=:treatmentType";
        if (needSearch) {
            queryStr += " and lower(t.name) like lower(:search)";
        }
        queryStr += "  order by " + getOrderField();
        Query query = getCurrentSession().createQuery(queryStr);
        query.setParameter("treatmentType", treatmentType);
        if (needSearch) {
            query.setParameter("search", "%" + search + "%");
        }
        return query.list();
    }

    @Override
    protected String getOrderField() {
        return "name";
    }
}
