package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Treatment;
import com.bogdansukonnov.eclinic.entity.TreatmentType;

import java.util.List;

public interface TreatmentDao extends TableDao<Treatment> {

    List<Treatment> getAll(TreatmentType treatmentType, String search);

    @Override
    String getQueryConditions(String search, Long parentId);
}
