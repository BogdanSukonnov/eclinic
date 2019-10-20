package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dao.SortBy;
import com.bogdansukonnov.eclinic.dto.PrescriptionDTO;
import com.bogdansukonnov.eclinic.entity.TreatmentType;
import com.bogdansukonnov.eclinic.service.PrescriptionService;
import com.bogdansukonnov.eclinic.service.TimePatternService;
import com.bogdansukonnov.eclinic.service.TreatmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        model.addObject("prescription", prescriptionService.getOne(id));
        return model;
    }

    @PostMapping("newPrescription")
    public ModelAndView newPrescription(@RequestParam("patient_id") Long patient_id,
                                        @RequestParam("patient_fullName") String patient_fullName) {
        ModelAndView model = new ModelAndView("prescription");
        model.addObject("patient_id", patient_id);
        model.addObject("patient_fullName", patient_fullName);
        model.addObject("allProcedures", treatmentService.getAll(TreatmentType.Procedure));
        model.addObject("allMedicine", treatmentService.getAll(TreatmentType.Medicine));
        model.addObject("allPatterns", timePatternService.getAll(SortBy.NAME));
        return model;
    }

    @PostMapping("saveNewPrescription")
    public String newPrescription(@RequestParam("pattern_id") Long pattern_id,
                                  @RequestParam("patient_id") Long patient_id) {
        String page = "redirect:/doctor/patients";
        return page;
    }

}
