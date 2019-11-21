package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dto.RequestTableDto;
import com.bogdansukonnov.eclinic.dto.SelectorDataDto;
import com.bogdansukonnov.eclinic.dto.TableDataDto;
import com.bogdansukonnov.eclinic.dto.TreatmentDto;
import com.bogdansukonnov.eclinic.entity.TreatmentType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TreatmentService {
    @Transactional(readOnly = true)
    List<TreatmentDto> getAll(OrderType orderType);

    @Transactional
    void addNew();

    @Transactional(readOnly = true)
    TreatmentDto getOne(Long id);

    @Transactional(readOnly = true)
    SelectorDataDto getAll(String type, String search);

    @Transactional(readOnly = true)
    TableDataDto getTreatmentTable(RequestTableDto data);

    @Transactional
    Long newTreatment(TreatmentType treatmentType, String name);
}
