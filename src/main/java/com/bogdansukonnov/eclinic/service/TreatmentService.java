package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.TreatmentConverter;
import com.bogdansukonnov.eclinic.dao.SortBy;
import com.bogdansukonnov.eclinic.dao.TreatmentDAO;
import com.bogdansukonnov.eclinic.dto.TreatmentDTO;
import com.bogdansukonnov.eclinic.entity.Treatment;
import com.bogdansukonnov.eclinic.entity.TreatmentType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TreatmentService {

    private TreatmentDAO treatmentDAO;

    private TreatmentConverter converter;

    @Transactional(readOnly = true)
    public List<TreatmentDTO> getAll(SortBy sortBy) {
        return treatmentDAO.getAll(sortBy).stream()
                .map(treatment -> converter.toDTO(treatment))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TreatmentDTO> getAll(TreatmentType treatmentType) {
        return treatmentDAO.getAll(treatmentType).stream()
                .map(treatment -> converter.toDTO(treatment))
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
        return converter.toDTO(treatment);
    }

}
