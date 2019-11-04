package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dto.RequestTableDTO;
import com.bogdansukonnov.eclinic.dto.SelectorDataDTO;
import com.bogdansukonnov.eclinic.dto.TableDataDTO;
import com.bogdansukonnov.eclinic.service.TimePatternService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping("doctor")
public class TimePatternController {

    private TimePatternService timePatternService;

    @PostMapping("time-pattern-selector-data")
    @ResponseBody
    public SelectorDataDTO timePatternSelectorData(
            @RequestParam(name = "search", required = false) String search) {
        return timePatternService.getAll(search);
    }

    @RequestMapping("time-patterns")
    public ModelAndView patterns() {
        return new ModelAndView("timePatterns");
    }

    @PostMapping("time-patterns-table")
    @ResponseBody
    public TableDataDTO timePatternsTable(@Validated RequestTableDTO data) {
        return timePatternService.getTable(data);
    }


}
