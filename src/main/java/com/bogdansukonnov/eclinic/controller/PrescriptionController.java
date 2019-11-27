package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dto.*;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionCreateException;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionUpdateException;
import com.bogdansukonnov.eclinic.exceptions.VersionConflictException;
import com.bogdansukonnov.eclinic.service.OrderType;
import com.bogdansukonnov.eclinic.service.PrescriptionService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("doctor")
public class PrescriptionController {

    private PrescriptionService prescriptionService;
    private static final String PRESCRIPTION = "prescription";
    private static final String PRESCRIPTION_LIST = "prescriptions";

    @GetMapping("prescriptions")
    public ModelAndView prescriptions() {
        List<ResponsePrescriptionDto> prescriptions = prescriptionService.getAll(OrderType.CREATION);
        ModelAndView model = new ModelAndView(PRESCRIPTION_LIST);
        model.addObject(PRESCRIPTION_LIST, prescriptions);
        return model;
    }

    @GetMapping("prescription")
    public ModelAndView prescription(@RequestParam("id") Long id) {
        ModelAndView model = new ModelAndView(PRESCRIPTION);
        ResponsePrescriptionDto prescription = prescriptionService.getOne(id);
        model.addObject("isNew", false);
        model.addObject(PRESCRIPTION, prescription);
        model.addObject("patientId", prescription.getPatient().getId());
        model.addObject("patientFullName", prescription.getPatient().getFullName());
        return model;
    }

    @GetMapping("newPrescription")
    public ModelAndView newPrescription(@RequestParam("patient_id") Long patientId,
                                        @RequestParam("patient_fullName") String patientFullName) {
        ModelAndView model = new ModelAndView(PRESCRIPTION);
        model.addObject("isNew", true);
        model.addObject("patientId", patientId);
        model.addObject("patientFullName", patientFullName);
        return model;
    }

    @PostMapping("new-prescription")
    @ResponseBody
    public IdDto newPrescription(@Validated RequestPrescriptionDto dto,
                  @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                          LocalDateTime startDate,
                  @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                          LocalDateTime endDate
    )
            throws PrescriptionCreateException, VersionConflictException {
        return prescriptionService.save(dto, startDate, endDate);
    }

    @PostMapping("prescription")
    @ResponseStatus(HttpStatus.OK)
    public void updatePrescription(@Validated(Update.class) RequestPrescriptionDto dto,
                 @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                         LocalDateTime startDate,
                 @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                             LocalDateTime endDate)
            throws PrescriptionCreateException, VersionConflictException {
        prescriptionService.save(dto, startDate, endDate);
    }

    @PostMapping("prescriptions-table")
    @ResponseBody
    public TableDataDto prescriptionsTable(@Validated RequestTableDto data) {
        return prescriptionService.getPrescriptionTable(data);
    }

    @PostMapping("cancel-prescription")
    @ResponseStatus(HttpStatus.OK)
    public void cancelPrescription(@RequestParam("id") Long id, @RequestParam("version") Integer version)
            throws PrescriptionUpdateException, VersionConflictException {
        prescriptionService.cancelPrescription(id, version);
    }

}
