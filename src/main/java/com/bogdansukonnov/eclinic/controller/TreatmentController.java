package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dto.SelectorDataDTO;
import com.bogdansukonnov.eclinic.service.TreatmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
@RequestMapping("doctor")
public class TreatmentController {

    private TreatmentService treatmentService;

    // REST controller
    @PostMapping("treatments-selector-data")
    @ResponseBody
    public SelectorDataDTO treatmentsSelectorData(@RequestParam("type") String type
            , @RequestParam(name = "search", required = false) String search) {
        return treatmentService.getAll(type, search);
    }

}
