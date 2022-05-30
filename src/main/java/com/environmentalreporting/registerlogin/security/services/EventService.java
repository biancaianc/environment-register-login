package com.environmentalreporting.registerlogin.security.services;

import com.environmentalreporting.registerlogin.exceptions.AlreadyRegistered;
import com.environmentalreporting.registerlogin.models.Event;
import com.environmentalreporting.registerlogin.models.User;
import com.environmentalreporting.registerlogin.payload.requests.EventRequest;
import com.environmentalreporting.registerlogin.payload.requests.UserRequest;
import com.environmentalreporting.registerlogin.payload.responses.EventResponse;
import com.environmentalreporting.registerlogin.repositories.EventRepository;
import com.environmentalreporting.registerlogin.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;

@Service
@Slf4j
public class EventService {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    public Event createEvent(EventRequest eventRequest) {
        log.info("Creating an event with title " + eventRequest.getTitle());
        Event event = new Event(eventRequest.getTitle(), eventRequest.getDescription(), eventRequest.getDate(), eventRequest.getMaxNumberOfParticipants(), eventRequest.getLocation(), eventRequest.getTelephone(), eventRequest.getDuration(), eventRequest.getImageName(), new HashSet<>());
        eventRepository.save(event);
        return event;
    }

    public Event updateEvent(Long id, @Valid UserRequest user) throws AlreadyRegistered {
        log.info("Add an user to an event");
        Event e = eventRepository.getById(id);
        User u = userRepository.getById(user.getId());
        if(e.getUsers().contains(u)) throw new AlreadyRegistered("Already registered");
        Set<User> users = e.getUsers();
        users.add(u);
        e.setUsers(users);
        eventRepository.save(e);
        return e;
    }

    public List<EventResponse> getEvents() {
        List<EventResponse> eventResponses = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);
        events.forEach(x -> eventResponses.add(new EventResponse(x.getId(), x.getTitle(), x.getDescription(),
                x.getDate(), x.getMaxNumberOfParticipants(), x.getLocation(), x.getTelephone(), x.getDuration(), x.getImageName(), x.getUsers())));
        return eventResponses;
    }

    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }
}

