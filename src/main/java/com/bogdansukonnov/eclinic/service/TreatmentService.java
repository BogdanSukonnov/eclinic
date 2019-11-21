package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dto.RequestTableDto;
import com.bogdansukonnov.eclinic.dto.SelectorDataDto;
import com.bogdansukonnov.eclinic.dto.TableDataDto;
import com.bogdansukonnov.eclinic.dto.TreatmentDto;
import com.bogdansukonnov.eclinic.entity.TreatmentType;

import java.util.List;

public interface TreatmentService {

    List<TreatmentDto> getAll(OrderType orderType);

    void addNew();

    TreatmentDto getOne(Long id);

    SelectorDataDto getAll(String type, String search);

    TableDataDto getTreatmentTable(RequestTableDto data);

    Long newTreatment(TreatmentType treatmentType, String name);
}
