package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dto.*;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionCreateException;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionUpdateException;
import com.bogdansukonnov.eclinic.service.OrderType;
import com.bogdansukonnov.eclinic.service.PrescriptionService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("doctor")
public class PrescriptionController {

    private PrescriptionService prescriptionService;

    @RequestMapping("prescriptions")
    public ModelAndView prescriptions() {
        List<ResponsePrescriptionDTO> prescriptions = prescriptionService.getAll(OrderType.CREATION);
        ModelAndView model = new ModelAndView("prescriptions");
        model.addObject("prescriptions", prescriptions);
        return model;
    }

    @RequestMapping("prescription")
    public ModelAndView prescription(@RequestParam("id") Long id) {
        ModelAndView model = new ModelAndView("prescription");
        ResponsePrescriptionDTO prescription = prescriptionService.getOne(id);
        model.addObject("isNew", false);
        model.addObject("prescription", prescription);
        model.addObject("patientId", prescription.getPatient().getId());
        model.addObject("patientFullName", prescription.getPatient().getFullName());
        return model;
    }

    @RequestMapping("newPrescription")
    public ModelAndView newPrescription(@RequestParam("patient_id") Long patientId,
                                        @RequestParam("patient_fullName") String patientFullName) {
        ModelAndView model = new ModelAndView("prescription");
        model.addObject("isNew", true);
        model.addObject("patientId", patientId);
        model.addObject("patientFullName", patientFullName);
        return model;
    }

    @PostMapping("saveNewPrescription")
    public String newPrescription(@Validated RequestPrescriptionDTO dto,
                  @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                          LocalDateTime startDate,
                  @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                          LocalDateTime endDate
    )
            throws PrescriptionCreateException, PrescriptionUpdateException {
        Long id = prescriptionService.save(dto, startDate, endDate);
        return "redirect:/doctor/prescription?id=" + id;
    }

    @PostMapping("updatePrescription")
    public String updatePrescription(@Validated(Update.class) RequestPrescriptionDTO dto,
                 @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                         LocalDateTime startDate,
                 @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                             LocalDateTime endDate)
            throws PrescriptionCreateException, PrescriptionUpdateException {
        prescriptionService.save(dto, startDate, endDate);
        return "redirect:/doctor/patient?id=" + dto.getPatientId();
    }

    @PostMapping("prescriptions-table")
    @ResponseBody
    public TableDataDTO prescriptionsTable(@Validated RequestTableDTO data) {
        return prescriptionService.getTable(data);
    }

    @PostMapping("cancel-prescription")
    @ResponseStatus(HttpStatus.OK)
    public void cancelPrescription(@RequestParam("id") Long id, HttpServletResponse response)
            throws PrescriptionUpdateException {
        prescriptionService.cancelPrescription(id);
    }

}
