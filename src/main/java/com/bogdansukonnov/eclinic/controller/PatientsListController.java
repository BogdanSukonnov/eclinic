package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.entity.Patient;
import com.bogdansukonnov.eclinic.service.PatientsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PatientsListController {

    private PatientsListService patientsListService;

    @Autowired
    public PatientsListController(PatientsListService patientsListService) {
        this.patientsListService = patientsListService;
    }

    @RequestMapping("/patients")
    public ModelAndView patients() {
        List<Patient> patients = patientsListService.getAll();
        ModelAndView model = new ModelAndView("patients");
        model.addObject("patients", patients);
        return model;
    }

    @RequestMapping(value = "/newPatient", method = RequestMethod.POST)
    public void newPatient(Patient patient) {
        patientsListService.save(patient);
    }

}
