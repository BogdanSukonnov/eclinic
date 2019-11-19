package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dto.RequestTableDto;
import com.bogdansukonnov.eclinic.dto.ResponsePatientDto;
import com.bogdansukonnov.eclinic.dto.TableDataDto;
import com.bogdansukonnov.eclinic.exceptions.PatientUpdateException;
import com.bogdansukonnov.eclinic.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @RequestMapping("patients")
    public String patients() {
        return "patients";
    }

    @RequestMapping("patient")
    public ModelAndView patient(@RequestParam("id") Long id) {
        ModelAndView model = new ModelAndView("patient");
        model.addObject("patient", patientService.getOne(id));
        return model;
    }

    @PostMapping("newPatient")
    public String newPatient(ResponsePatientDto responsePatientDto) {
        Long id = patientService.addNew(responsePatientDto);
        return "redirect:/doctor/patient?id=" + id;
    }

    @PostMapping("patients-table")
    @ResponseBody
    public TableDataDto patientsTable(@Validated RequestTableDto data) {
        return patientService.getTable(data);
    }

    @PostMapping("discharge-patient")
    @ResponseStatus(HttpStatus.OK)
    public void dischargePatient(@RequestParam("patient_id") Long id,
                                 @RequestParam("version") Integer version)
            throws PatientUpdateException {
        patientService.dischargePatient(id, version);
    }

    @PostMapping("patient-name-is-unique")
    public void patientNameIsUnique(@RequestParam("fullName") String fullName,
                                    HttpServletResponse response) {
        if (patientService.patientNameIsBusy(fullName)) {
            response.setStatus(HttpStatus.CONFLICT.value());
        }
        else response.setStatus(HttpStatus.OK.value());
    }

}
