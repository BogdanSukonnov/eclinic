package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dto.RequestEventTableDTO;
import com.bogdansukonnov.eclinic.dto.TableDataDTO;
import com.bogdansukonnov.eclinic.entity.EventStatus;
import com.bogdansukonnov.eclinic.exceptions.EventStatusUpdateException;
import com.bogdansukonnov.eclinic.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

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

    @PostMapping("/nurse/events-table")
    @ResponseBody
    public TableDataDTO eventsTable(@Validated RequestEventTableDTO data,
        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                LocalDateTime startDate,
        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                LocalDateTime endDate) {
        return eventService.getTable(data, startDate, endDate);
    }

    @PostMapping("/doctor/prescription-events-table")
    @ResponseBody
    public TableDataDTO eventsTable(@Validated RequestEventTableDTO data) {
        return eventService.getTable(data, null, null);
    }

    @PostMapping("/nurse/complete-event")
    public void completeEvent(@RequestParam("id") Long eventId)
            throws EventStatusUpdateException {
        eventService.updateStatus(eventId, EventStatus.COMPLETED, "");
    }

    @PostMapping("/nurse/cancel-event")
    public void completeEvent(@RequestParam("id") Long eventId
        , @RequestParam("cancel-reason") String cancelReason)
            throws EventStatusUpdateException {
        eventService.updateStatus(eventId, EventStatus.CANCELED, cancelReason);
    }

}