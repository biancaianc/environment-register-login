package com.environmentalreporting.controllers;

import com.environmentalreporting.models.Event;
import com.environmentalreporting.payload.requests.EventRequest;
import com.environmentalreporting.payload.responses.EventResponse;
import com.environmentalreporting.repositories.EventRepository;
import com.environmentalreporting.security.services.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        log.info("Get all events");
        List<EventResponse> eventResponses;
        try {
            eventResponses = eventService.getEvents();
            return new ResponseEntity<>(eventResponses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Event> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        try {
            log.info("Creating event with title: " + eventRequest.getTitle());
            return new ResponseEntity<>(eventService.createEvent(eventRequest),HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") long id) {
        log.info("Deleting event with id " + id);
        try {
            eventService.deleteEvent(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/")
    public ResponseEntity<Event> updateEvent(@Valid @RequestBody Event event) {
        try {
            log.info("Updating event with id " + event.getId());
            return new ResponseEntity<>(eventService.updateEvent(event), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{eventId}/user/{userId}")
    public ResponseEntity<Event> addUserToEvent(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
        try {
            log.info("Adding user with id " + userId + " to event with id: " + eventId);
            return new ResponseEntity<>(eventService.addUserToEvent(eventId, userId), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
