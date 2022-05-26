package com.environmentalreporting.registerlogin.controllers;

import com.environmentalreporting.registerlogin.exceptions.AlreadyRegistered;
import com.environmentalreporting.registerlogin.exceptions.AlreadyReportedInThatArea;
import com.environmentalreporting.registerlogin.models.Event;
import com.environmentalreporting.registerlogin.models.Report;
import com.environmentalreporting.registerlogin.models.User;
import com.environmentalreporting.registerlogin.payload.requests.EventRequest;
import com.environmentalreporting.registerlogin.payload.requests.ReportRequest;
import com.environmentalreporting.registerlogin.payload.requests.UserRequest;
import com.environmentalreporting.registerlogin.payload.responses.EventResponse;
import com.environmentalreporting.registerlogin.repositories.EventRepository;
import com.environmentalreporting.registerlogin.security.services.EventService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/event")
@Slf4j
public class EventController {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventService eventService;

    @GetMapping("/events")
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        log.info("Event Get All");
        List<Event> events = new ArrayList<>();
        List<EventResponse> eventResponses = new ArrayList<>();
        try {
            eventRepository.findAll().forEach(events::add);
            events.forEach(x -> eventResponses.add(new EventResponse(x.getId(), x.getTitle(), x.getDescription(),
                    x.getDate(), x.getMaxNumberOfParticipants(), x.getLocation(), x.getTelephone(), x.getDuration(), x.getImageName(), x.getUsers())));
            return new ResponseEntity<>(eventResponses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/event")
    public ResponseEntity<Event> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        try {
            log.info("Event Post");
            return new ResponseEntity<>(eventService.createEvent(eventRequest),HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateEvent/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable("id") String eventId, @Valid @RequestBody UserRequest user) {
        try {
            log.info("In update");
            return new ResponseEntity<>(eventService.updateEvent(Long.valueOf(eventId), user), HttpStatus.OK);
        }catch (AlreadyRegistered alreadyRegistered){
            log.error(alreadyRegistered.getMessage());
            return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}