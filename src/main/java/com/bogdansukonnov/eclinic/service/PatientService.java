package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dto.RequestTableDto;
import com.bogdansukonnov.eclinic.dto.ResponsePatientDto;
import com.bogdansukonnov.eclinic.dto.TableDataDto;
import com.bogdansukonnov.eclinic.exceptions.PatientUpdateException;

import java.util.List;

public interface PatientService {
    List<ResponsePatientDto> getAll(OrderType orderType);

    ResponsePatientDto getOne(Long id);

    Long addNew(ResponsePatientDto responsePatientDto);

    TableDataDto getPatientTable(RequestTableDto data);

    void dischargePatient(Long id, Integer version) throws PatientUpdateException;

    boolean patientNameIsBusy(String fullName);
}
