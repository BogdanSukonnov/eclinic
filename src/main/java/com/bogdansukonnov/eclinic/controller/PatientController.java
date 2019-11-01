package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dao.SortBy;
import com.bogdansukonnov.eclinic.dto.PatientDTO;
import com.bogdansukonnov.eclinic.dto.TableDataDTO;
import com.bogdansukonnov.eclinic.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("doctor")
public class PatientController {

    private PatientService patientService;

    @GetMapping("patients")
    public ModelAndView patients() {
        List<PatientDTO> patients = patientService.getAll(SortBy.CREATION);
        ModelAndView model = new ModelAndView("patients");
        model.addObject("patients", patients);
        return model;
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

    // REST controller
    @PostMapping("/patients-table")
    @ResponseBody
    public TableDataDTO patientsTable(@RequestParam Map<String, String> data) {
        return patientService.getTable(data);
    }


}
