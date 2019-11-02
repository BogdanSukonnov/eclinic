package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dto.PatientDTO;
import com.bogdansukonnov.eclinic.dto.RequestTableDTO;
import com.bogdansukonnov.eclinic.dto.TableDataDTO;
import com.bogdansukonnov.eclinic.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor
@RequestMapping("doctor")
public class PatientController {

    private PatientService patientService;

    @GetMapping("patients")
    public String patients() {
        return "patients";
    }

    @GetMapping("patient")
    public ModelAndView patient(@RequestParam("id") Long id, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("patient");
        model.addObject("patient", patientService.getOne(id));
        return model;
    }

    @PostMapping("newPatient")
    public String newPatient(PatientDTO patientDTO) {
        patientService.addNew(patientDTO);
        return "redirect:/doctor/patients";
    }

    @PostMapping("/patients-table")
    @ResponseBody
    public TableDataDTO patientsTable(@Validated RequestTableDTO data) {
        return patientService.getTable(data);
    }

}
