package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.Patient;

import java.util.Optional;

public interface PatientDao extends TableDao<Patient> {
    @Override
    String getQueryConditions(String search, Long parentId);

    Optional<Patient> findOne(String fullName);
}
