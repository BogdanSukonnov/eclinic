package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dto.*;
import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionCreateException;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionUpdateException;
import com.bogdansukonnov.eclinic.exceptions.VersionConflictException;

import java.time.LocalDateTime;
import java.util.List;

public interface PrescriptionService {

    List<ResponsePrescriptionDto> getAll(OrderType orderType);

    IdDto save(RequestPrescriptionDto dto,
               LocalDateTime startDate, LocalDateTime endDate)
            throws PrescriptionCreateException, VersionConflictException;

    ResponsePrescriptionDto getOne(Long id);

    TableDataDto getPrescriptionTable(RequestTableDto data);

    void cancelPrescription(Long id, Integer version) throws PrescriptionUpdateException, VersionConflictException;

    void completeAllActive(Patient patient);
}
