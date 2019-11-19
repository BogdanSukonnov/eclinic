package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.SelectorDataConverter;
import com.bogdansukonnov.eclinic.dao.TreatmentDAO;
import com.bogdansukonnov.eclinic.dto.RequestTableDto;
import com.bogdansukonnov.eclinic.dto.SelectorDataDto;
import com.bogdansukonnov.eclinic.dto.TableDataDto;
import com.bogdansukonnov.eclinic.dto.TreatmentDto;
import com.bogdansukonnov.eclinic.entity.SelectorData;
import com.bogdansukonnov.eclinic.entity.Treatment;
import com.bogdansukonnov.eclinic.entity.TreatmentType;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TreatmentService {

    private TreatmentDAO treatmentDAO;
    private ModelMapper modelMapper;
    private SelectorDataConverter selectorDataConverter;

    @Transactional(readOnly = true)
    public List<TreatmentDto> getAll(OrderType orderType) {
        return treatmentDAO.getAll(orderType).stream()
                .map(treatment -> modelMapper.map(treatment, TreatmentDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addNew() {
        Treatment treatment = new Treatment();
        treatmentDAO.create(treatment);
    }

    @Transactional(readOnly = true)
    public TreatmentDto getOne(Long id) {
        Treatment treatment = treatmentDAO.findOne(id);
        return modelMapper.map(treatment, TreatmentDto.class);
    }

    @Transactional(readOnly = true)
    public SelectorDataDto getAll(String type, String search) {
        TreatmentType treatmentType = TreatmentType.valueOf(type);
        return selectorDataConverter.toDto(treatmentDAO.getAll(treatmentType, search).stream()
                .map(t -> (SelectorData) t)
                .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public TableDataDto getTable(RequestTableDto data) {

        List<Treatment> treatments = treatmentDAO.getAll("name", data.getSearch(),
                data.getOffset(), data.getLimit(), null);

        Long totalFiltered = treatmentDAO.getTotalFiltered(data.getSearch(), null);

        List<TreatmentDto> list = treatments.stream()
                .map(treatment -> modelMapper.map(treatment, TreatmentDto.class))
                .collect(Collectors.toList());

        return new TableDataDto<>(list, data.getDraw(), totalFiltered, totalFiltered);
    }

    @Transactional
    public Long newTreatment(TreatmentType treatmentType, String name) {
        Treatment treatment = new Treatment();
        treatment.setType(treatmentType);
        treatment.setName(name);
        return treatmentDAO.create(treatment).getId();
    }

}
