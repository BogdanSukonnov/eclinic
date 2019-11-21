package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dto.*;
import com.bogdansukonnov.eclinic.service.TimePatternService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@Log4j2
@RequestMapping("doctor")
public class TimePatternController {

    private TimePatternService timePatternService;

    @GetMapping("new-time-pattern")
    public String newTimePattern() {
        return "newTimePattern";
    }

    @PutMapping("new-time-pattern")
    @ResponseBody
    public IdDto addTimePattern(@Validated @RequestBody TimePatternDto dto) {
        return timePatternService.addNew(dto);
    }

    @PostMapping("time-pattern-selector-data")
    @ResponseBody
    public SelectorDataDto timePatternSelectorData(
            @RequestParam(name = "search", required = false) String search) {
        return timePatternService.getAll(search);
    }

    @RequestMapping("time-patterns")
    public ModelAndView patterns() {
        return new ModelAndView("timePatterns");
    }

    @PostMapping("time-patterns-table")
    @ResponseBody
    public TableDataDto timePatternsTable(@Validated RequestTableDto data) {
        return timePatternService.getTimePatternTable(data);
    }

    @RequestMapping("time-pattern")
    public ModelAndView timePattern(@RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView("timePattern");
        mv.addObject("timePattern", timePatternService.getOne(id));
        return mv;
    }


}
