package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.PrescriptionDAO;
import com.bogdansukonnov.eclinic.dto.PrescriptionDTO;
import com.bogdansukonnov.eclinic.entity.Prescription;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrescriptionService {

    private PrescriptionDAO prescriptionDAO;

    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<PrescriptionDTO> getAll() {
        return prescriptionDAO.getAll().stream()
                .map(prescription -> modelMapper.map(prescription, PrescriptionDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addNew() {
        Prescription prescription = new Prescription();
        // ToDo: insurance, doctor
        prescriptionDAO.create(prescription);
    }

    @Transactional(readOnly = true)
    public PrescriptionDTO getOne(Long id) {
        Prescription prescription = prescriptionDAO.findOne(id);
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
        modelMapper.map(prescription, prescriptionDTO);
        return prescriptionDTO;
    }

}
