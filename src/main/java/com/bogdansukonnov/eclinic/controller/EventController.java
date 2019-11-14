package com.bogdansukonnov.eclinic.controller;

import com.bogdansukonnov.eclinic.dto.EventsInfoDTO;
import com.bogdansukonnov.eclinic.dto.RequestEventTableDTO;
import com.bogdansukonnov.eclinic.dto.TableDataDTO;
import com.bogdansukonnov.eclinic.entity.EventStatus;
import com.bogdansukonnov.eclinic.exceptions.EventStatusUpdateException;
import com.bogdansukonnov.eclinic.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
public class EventController {

    private EventService eventService;

    @RequestMapping("/nurse/events")
    public String events() {
        return "events";
    }

    @RequestMapping("/nurse/event")
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
    @ResponseStatus(HttpStatus.OK)
    public void completeEvent(@RequestParam("id") Long eventId,
                              @RequestParam("version") Integer version)
            throws EventStatusUpdateException {
        eventService.updateStatus(eventId, EventStatus.COMPLETED, "", version);
    }

    @PostMapping("/nurse/cancel-event")
    @ResponseStatus(HttpStatus.OK)
    public void cancelEvent(@RequestParam("id") Long eventId,
                              @RequestParam("cancel_reason") String cancelReason,
                              @RequestParam("version") Integer version)
            throws EventStatusUpdateException {
        eventService.updateStatus(eventId, EventStatus.CANCELED, cancelReason, version);
    }

    @GetMapping("/info/events")
    @ResponseBody
    public EventsInfoDTO eventsInfo(@RequestParam("eventId") Long eventId,
                                    @RequestParam("lastMessageId") Long lastMessageId) {
        return eventService.eventsInfo(eventId, lastMessageId);
    }

}
