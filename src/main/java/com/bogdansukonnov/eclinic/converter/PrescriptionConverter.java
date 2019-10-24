package com.bogdansukonnov.eclinic.converter;

import com.bogdansukonnov.eclinic.dto.PrescriptionDTO;
import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.entity.Prescription;
import com.bogdansukonnov.eclinic.entity.TimePattern;
import com.bogdansukonnov.eclinic.entity.Treatment;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class PrescriptionConverter {

    private ModelMapper modelMapper;

    public PrescriptionDTO toDTO(Prescription prescription) {
        PrescriptionDTO dto = modelMapper.map(prescription, PrescriptionDTO.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        dto.setDateTimeFormatted(prescription.getCreatedDateTime().format(formatter));
        return dto;
    }

    public Prescription toEntity(Prescription prescription, PrescriptionDTO dto, Patient patient, Treatment treatment, TimePattern timePattern) {
        prescription.setId(dto.getId());
        prescription.setDosage(dto.getDosage());
        prescription.setDuration(dto.getDuration());
        prescription.setTreatment(treatment);
        prescription.setTimePattern(timePattern);
        prescription.setPatient(patient);
        return prescription;
    }

}