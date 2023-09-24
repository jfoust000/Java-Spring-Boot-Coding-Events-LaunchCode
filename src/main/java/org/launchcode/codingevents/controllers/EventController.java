package org.launchcode.codingevents.controllers;

import jakarta.validation.Valid;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("events")
public class  EventController {

    @Autowired
    private EventRepository eventRepository;

    // findAll, save, findbyId

    @GetMapping
    public String displayAllEvents(Model model) {

        model.addAttribute("events", eventRepository.findAll());
        return "events/index";

    }
    // lives at /events/create
    @GetMapping("create")
    public String renderCreateEventForm(Model model) {

        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
        model.addAttribute("eventTypeValues", EventType.values());
        return "events/create";

    }
    // lives at /events/create
    @PostMapping
    public String createEvent(@Valid Event newEvent, Errors errors, Model model) {

        if (errors.hasErrors()) {

            model.addAttribute("title", "Create Event");
            return "events/create";

        }
        eventRepository.save(newEvent);
        return "redirect:/events";

    }

    @GetMapping("delete")
    public String renderDeleteEventForm(Model model) {


        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", eventRepository.findAll());
        return "events/delete";

    }

    @PostMapping("delete")
    public String deleteEvent(@RequestParam(required = false) int[] eventIds) {

        if (eventIds != null) {

            for (int id : eventIds) {

                eventRepository.deleteById(id);

            }

        }

        return "redirect:/events";

    }

}
