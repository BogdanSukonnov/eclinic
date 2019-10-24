package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dao.SortBy;
import com.bogdansukonnov.eclinic.dto.PrescriptionDTO;
import com.bogdansukonnov.eclinic.service.PrescriptionService;
import com.bogdansukonnov.eclinic.service.SaveType;
import com.bogdansukonnov.eclinic.service.TimePatternService;
import com.bogdansukonnov.eclinic.service.TreatmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("doctor")
public class PrescriptionController {

    private PrescriptionService prescriptionService;

    private TimePatternService timePatternService;

    private TreatmentService treatmentService;

    @GetMapping("prescriptions")
    public ModelAndView prescriptions() {
        List<PrescriptionDTO> prescriptions = prescriptionService.getAll(SortBy.CREATION);
        ModelAndView model = new ModelAndView("prescriptions");
        model.addObject("prescriptions", prescriptions);
        return model;
    }

    @GetMapping("prescription")
    public ModelAndView prescription(@RequestParam("id") Long id) {
        ModelAndView model = new ModelAndView("prescription");
        PrescriptionDTO prescription = prescriptionService.getOne(id);
        model.addObject("prescription", prescription);
        model.addObject("patient_id", prescription.getPatient().getId());
        model.addObject("patient_fullName", prescription.getPatient().getFullName());
        model.addObject("allTreatments", treatmentService.getAll(SortBy.NAME));
        model.addObject("allPatterns", timePatternService.getAll(SortBy.NAME));
        return model;
    }

    @PostMapping("newPrescription")
    public ModelAndView newPrescription(@RequestParam("patient_id") Long patient_id,
                                        @RequestParam("patient_fullName") String patient_fullName) {
        ModelAndView model = new ModelAndView("prescription");
        model.addObject("prescription", null);
        model.addObject("patient_id", patient_id);
        model.addObject("patient_fullName", patient_fullName);
        model.addObject("allTreatments", treatmentService.getAll(SortBy.NAME));
        model.addObject("allPatterns", timePatternService.getAll(SortBy.NAME));
        return model;
    }

    @PostMapping("saveNewPrescription")
    public String newPrescription(PrescriptionDTO prescriptionDTO) {
        prescriptionService.save(SaveType.CREATE, prescriptionDTO);
        String page = "redirect:/doctor/patients";
        return page;
    }

    @PostMapping("updatePrescription")
    public String updatePrescription(PrescriptionDTO prescriptionDTO) {
        prescriptionService.save(SaveType.UPDATE, prescriptionDTO);
        String page = "redirect:/doctor/prescriptions";
        return page;
    }

    // REST controller
    @PostMapping("patientPrescriptions")
    public @ResponseBody List<PrescriptionDTO> patientPrescriptions(@RequestParam("patientId") Long patientId) {
        return prescriptionService.getAllByPatient(patientId);
    }

}
