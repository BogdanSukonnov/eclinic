package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dto.PrescriptionDTO;
import com.bogdansukonnov.eclinic.dto.RequestTableDTO;
import com.bogdansukonnov.eclinic.dto.TableDataDTO;
import com.bogdansukonnov.eclinic.dto.Update;
import com.bogdansukonnov.eclinic.exceptions.PrescriptionUpdateException;
import com.bogdansukonnov.eclinic.service.PrescriptionService;
import com.bogdansukonnov.eclinic.service.SaveType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("doctor")
public class PrescriptionController {

    private PrescriptionService prescriptionService;

    @GetMapping("prescriptions")
    public ModelAndView prescriptions() {
        List<PrescriptionDTO> prescriptions = prescriptionService.getAll(PrescriptionService.OrderType.CREATION);
        ModelAndView model = new ModelAndView("prescriptions");
        model.addObject("prescriptions", prescriptions);
        return model;
    }

    @GetMapping("prescription")
    public ModelAndView prescription(@RequestParam("id") Long id) {
        ModelAndView model = new ModelAndView("prescription");
        PrescriptionDTO prescription = prescriptionService.getOne(id);
        model.addObject("isNew", false);
        model.addObject("prescription", prescription);
        model.addObject("patientId", prescription.getPatient().getId());
        model.addObject("patientFullName", prescription.getPatient().getFullName());
        return model;
    }

    @PostMapping("newPrescription")
    public ModelAndView newPrescription(@RequestParam("patient_id") Long patient_id,
                                        @RequestParam("patient_fullName") String patient_fullName) {
        ModelAndView model = new ModelAndView("prescription");
        model.addObject("isNew", true);
        model.addObject("patientId", patient_id);
        model.addObject("patientFullName", patient_fullName);
        return model;
    }

    @PostMapping("saveNewPrescription")
    public String newPrescription(@Validated PrescriptionDTO prescriptionDTO) {
        prescriptionService.save(SaveType.CREATE, prescriptionDTO);
        String page = "redirect:/doctor/patients";
        return page;
    }

    @PostMapping("updatePrescription")
    public String updatePrescription(@Validated(Update.class) PrescriptionDTO prescriptionDTO) {
        prescriptionService.save(SaveType.UPDATE, prescriptionDTO);
        String page = "redirect:/doctor/prescriptions";
        return page;
    }

    @PostMapping("prescriptions-table")
    @ResponseBody
    public TableDataDTO prescriptionsTable(@Validated RequestTableDTO data) {
        return prescriptionService.getTable(data);
    }

    @PostMapping("cancel-prescription")
    public void cancelPrescription(@RequestParam("id") Long id, HttpServletResponse response)
            throws PrescriptionUpdateException {
        prescriptionService.cancelPrescription(id);
    }

}
