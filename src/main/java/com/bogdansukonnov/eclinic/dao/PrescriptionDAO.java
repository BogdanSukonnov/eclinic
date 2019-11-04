package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.entity.Prescription;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PrescriptionDAO extends AbstractTableDAO<Prescription> implements ITableDAO<Prescription> {

    public PrescriptionDAO() {
        setClazz(Prescription.class);
    }

    @Override
    public String getQueryConditions(String search, Long parentId) {
        List<String> conditionsList = new ArrayList<>();
        if (!StringUtils.isBlank(search)) {
            conditionsList.add(" (lower(t.patient.fullName) like lower(:search))");
        }
        if (parentId != null) {
            conditionsList.add(" (t.patient.id=:parentId)");
        }
        String conditionsStr = "";
        if (conditionsList.size() > 0) {
            conditionsStr = " where" + String.join(" and ", conditionsList);
        }
        return conditionsStr;
    }

    public List<Prescription> getAll(Patient patient) {
        Query query = getCurrentSession().createQuery(
                "from Prescription t where t.patient=:patient");
        query.setParameter("patient", patient);
        return query.list();
    }

}
