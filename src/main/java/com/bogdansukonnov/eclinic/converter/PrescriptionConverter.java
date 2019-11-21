package com.bogdansukonnov.eclinic.converter;

import com.bogdansukonnov.eclinic.config.EClinicConstants;
import com.bogdansukonnov.eclinic.dto.RequestPrescriptionDto;
import com.bogdansukonnov.eclinic.dto.ResponsePrescriptionDto;
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

    public ResponsePrescriptionDto toDto(Prescription prescription) {
        ResponsePrescriptionDto dto = modelMapper.map(prescription, ResponsePrescriptionDto.class);
        dto.setStartDateFormatted(prescription.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        dto.setEndDateFormatted(prescription.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        DateTimeFormatter formatter = EClinicConstants.dateNoYearFormatter;
        dto.setPeriod(prescription.getStartDate().format(formatter) + "-"
            + prescription.getEndDate().format(formatter));
        dto.setTreatmentWithDosage(prescription.getTreatment().getName()
            + (prescription.getTreatment().getType() == TreatmentType.MEDICINE
            ? (" " + prescription.getDosage()) : ""));
        return dto;
    }

    public Prescription toEntity(Prescription prescription, RequestPrescriptionDto dto,
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