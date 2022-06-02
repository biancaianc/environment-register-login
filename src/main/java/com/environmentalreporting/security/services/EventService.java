package com.environmentalreporting.security.services;

import com.environmentalreporting.models.Event;
import com.environmentalreporting.models.User;
import com.environmentalreporting.payload.requests.EventRequest;
import com.environmentalreporting.payload.responses.EventResponse;
import com.environmentalreporting.repositories.EventRepository;
import com.environmentalreporting.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class EventService {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    public Event createEvent(EventRequest eventRequest) {
        Event event = new Event(eventRequest.getTitle(), eventRequest.getDescription(), eventRequest.getDate(), eventRequest.getMaxNumberOfParticipants(), eventRequest.getLocation(), eventRequest.getTelephone(), eventRequest.getDuration(), eventRequest.getImagePath(), new HashSet<>(0));
        eventRepository.save(event);
        return event;
    }

    public Event updateEvent(Event event) {
        eventRepository.save(event);

        return event;
    }

    public List<EventResponse> getEvents() {
        List<EventResponse> eventResponses = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);
        events.forEach(x -> eventResponses.add(new EventResponse(x.getId(), x.getTitle(), x.getDescription(),
                x.getDate(), x.getMaxNumberOfParticipants(), x.getLocation(), x.getTelephone(), x.getDuration(), x.getImagePath(), x.getUsers())));
        return eventResponses;
    }

    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }

    public Event addUserToEvent(long eventId, long userId) {
        Event e = eventRepository.findById(eventId).get();
        User u = userRepository.findById(userId).get();
        Set<User> users = e.getUsers();
        users.add(u);
        e.setUsers(users);
        eventRepository.save(e);
        return e;
    }
}

