package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dto.PatientDTO;
import com.bogdansukonnov.eclinic.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {

    private PatientService patientService;

    @GetMapping("/doctor/patients")
    public ModelAndView patients() {
        List<PatientDTO> patients = patientService.getAll();
        ModelAndView model = new ModelAndView("patients");
        model.addObject("patients", patients);
        return model;
    }

    @PostMapping("/doctor/newPatient")
    public void newPatient(@RequestParam("fullName") String fullName) {
        patientService.addNew(fullName);
    }

}
