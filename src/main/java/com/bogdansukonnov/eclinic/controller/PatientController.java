package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dto.RequestTableDTO;
import com.bogdansukonnov.eclinic.dto.ResponsePatientDTO;
import com.bogdansukonnov.eclinic.dto.TableDataDTO;
import com.bogdansukonnov.eclinic.exceptions.PatientUpdateException;
import com.bogdansukonnov.eclinic.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public ModelAndView patient(@RequestParam("id") Long id, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("patient");
        model.addObject("patient", patientService.getOne(id));
        return model;
    }

    @PostMapping("newPatient")
    public String newPatient(ResponsePatientDTO responsePatientDTO) {
        Long id = patientService.addNew(responsePatientDTO);
        return "redirect:/doctor/patient?id=" + id;
    }

    @PostMapping("/patients-table")
    @ResponseBody
    public TableDataDTO patientsTable(@Validated RequestTableDTO data) {
        return patientService.getTable(data);
    }

    @PostMapping("/discharge-patient")
    public void dischargePatient(@RequestParam("patient_id") Long id,
                                 @RequestParam("version") Integer version)
            throws PatientUpdateException {
        patientService.dischargePatient(id, version);
    }

}
