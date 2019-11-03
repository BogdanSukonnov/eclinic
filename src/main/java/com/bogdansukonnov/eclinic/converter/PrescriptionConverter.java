package com.bogdansukonnov.eclinic.converter;

import com.bogdansukonnov.eclinic.dto.RequestPrescriptionDTO;
import com.bogdansukonnov.eclinic.dto.ResponsePrescriptionDTO;
import com.bogdansukonnov.eclinic.entity.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class PrescriptionConverter {

    private ModelMapper modelMapper;

    public ResponsePrescriptionDTO toDTO(Prescription prescription) {
        ResponsePrescriptionDTO dto = modelMapper.map(prescription, ResponsePrescriptionDTO.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        dto.setStartDateFormatted(prescription.getStartDate().format(formatter));
        dto.setEndDateFormatted(prescription.getEndDate().format(formatter));
        formatter = DateTimeFormatter.ofPattern("dd.MM");
        dto.setPeriod(prescription.getStartDate().format(formatter) + " - "
            + prescription.getEndDate().format(formatter));
        dto.setTreatmentWithDosage(prescription.getTreatment().getName()
            + (prescription.getTreatment().getType() == TreatmentType.MEDICINE
            ? (" " + prescription.getDosage()) : ""));
        return dto;
    }

    public Prescription toEntity(Prescription prescription, RequestPrescriptionDTO dto,
                 Patient patient, Treatment treatment, TimePattern timePattern,
                    LocalDateTime startDate, LocalDateTime endDate) {
        prescription.setDosage(dto.getDosage());
        prescription.setStartDate(startDate);
        prescription.setEndDate(endDate);
        prescription.setTreatment(treatment);
        prescription.setTimePattern(timePattern);
        prescription.setPatient(patient);
        return prescription;
    }

}