package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.SelectorDataConverter;
import com.bogdansukonnov.eclinic.dao.SortBy;
import com.bogdansukonnov.eclinic.dao.TreatmentDAOOld;
import com.bogdansukonnov.eclinic.dto.SelectorDataDTO;
import com.bogdansukonnov.eclinic.dto.TreatmentDTO;
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

    private TreatmentDAOOld treatmentDAO;
    private ModelMapper modelMapper;
    private SelectorDataConverter selectorDataConverter;

    @Transactional(readOnly = true)
    public List<TreatmentDTO> getAll(SortBy sortBy) {
        return treatmentDAO.getAll(sortBy).stream()
                .map(treatment -> modelMapper.map(treatment, TreatmentDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addNew() {
        Treatment treatment = new Treatment();
        treatmentDAO.create(treatment);
    }

    @Transactional(readOnly = true)
    public TreatmentDTO getOne(Long id) {
        Treatment treatment = treatmentDAO.findOne(id);
        return modelMapper.map(treatment, TreatmentDTO.class);
    }

    @Transactional(readOnly = true)
    public SelectorDataDTO getAll(String type, String search) {
        TreatmentType treatmentType = TreatmentType.valueOf(type);
        return selectorDataConverter.toDTO(treatmentDAO.getAll(treatmentType, search).stream()
                .map(t -> (SelectorData) t)
                .collect(Collectors.toList()));
    }
}
