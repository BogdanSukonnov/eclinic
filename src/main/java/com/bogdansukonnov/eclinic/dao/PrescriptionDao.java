package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.entity.Prescription;

import java.util.List;

public interface PrescriptionDao extends TableDao<Prescription> {

    @Override
    String getQueryConditions(String search, Long parentId);

    List<Prescription> getAll(Patient patient);
}
