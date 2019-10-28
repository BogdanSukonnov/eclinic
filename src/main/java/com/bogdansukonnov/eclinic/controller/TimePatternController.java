package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dto.SelectorDataDTO;
import com.bogdansukonnov.eclinic.service.TimePatternService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
@RequestMapping("doctor")
public class TimePatternController {

    private TimePatternService timePatternService;

    // REST controller
    @PostMapping("time-pattern-selector-data")
    @ResponseBody
    public SelectorDataDTO timePatternSelectorData(
            @RequestParam(name = "search", required = false) String search) {
        return timePatternService.getAll(search);
    }

}
