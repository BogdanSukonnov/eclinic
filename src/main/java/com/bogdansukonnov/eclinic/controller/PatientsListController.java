package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dto.PatientDTO;
import com.bogdansukonnov.eclinic.service.NewPatientService;
import com.bogdansukonnov.eclinic.service.PatientsListService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PatientsListController {

    @NonNull
    private PatientsListService patientsListService;

    @NonNull
    private NewPatientService newPatientService;

    @GetMapping("/doctor/patients")
    public ModelAndView patients() {
        List<PatientDTO> patients = patientsListService.getAll();
        ModelAndView model = new ModelAndView("patients");
        model.addObject("patients", patients);
        return model;
    }

    @PostMapping("/doctor/newPatient")
    public void newPatient(@RequestParam("fullName") String fullName) {
        newPatientService.addNew(fullName);
    }

}
