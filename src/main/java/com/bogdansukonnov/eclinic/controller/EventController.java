package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dto.TableDataDTO;
import com.bogdansukonnov.eclinic.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@AllArgsConstructor
public class EventController {

    private EventService eventService;

    @GetMapping("/nurse/events")
    public String events() {
        return "events";
    }

    @GetMapping("/nurse/event")
    public ModelAndView event(@RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView("event");
        mv.addObject("event", eventService.getOne(id));
        return mv;
    }

    // REST controller
    @PostMapping("/nurse/events-table")
    @ResponseBody
    public TableDataDTO eventsTable(
            @RequestParam Map<String, String> data) {
        return eventService.getTable(data);
    }

    // REST controller
    @PostMapping("/doctor/prescription-events-table")
    @ResponseBody
    public TableDataDTO eventsTable(@RequestParam("prescription-id") Long prescriptionId,
            @RequestParam Map<String, String> data) {
        return eventService.getTable(prescriptionId, data);
    }

}
