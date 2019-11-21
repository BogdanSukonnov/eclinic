package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dto.RequestPrescriptionDto;
import com.bogdansukonnov.eclinic.dto.RequestTableDto;
import com.bogdansukonnov.eclinic.dto.ResponsePrescriptionDto;
import com.bogdansukonnov.eclinic.dto.TableDataDto;
import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionCreateException;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionUpdateException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface PrescriptionService {
    @Transactional(readOnly = true)
    List<ResponsePrescriptionDto> getAll(OrderType orderType);

    @Transactional
    Long save(RequestPrescriptionDto dto,
              LocalDateTime startDate, LocalDateTime endDate)
            throws PrescriptionCreateException, PrescriptionUpdateException;

    @Transactional(readOnly = true)
    ResponsePrescriptionDto getOne(Long id);

    @Transactional(readOnly = true)
    TableDataDto getPrescriptionTable(RequestTableDto data);

    @Transactional
    void cancelPrescription(Long id) throws PrescriptionUpdateException;

    @Transactional
    void completeAllActive(Patient patient);
}
