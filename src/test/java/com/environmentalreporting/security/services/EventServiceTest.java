package com.environmentalreporting.security.services;

import com.environmentalreporting.models.Event;
import com.environmentalreporting.models.User;
import com.environmentalreporting.payload.requests.EventRequest;
import com.environmentalreporting.payload.responses.EventResponse;
import com.environmentalreporting.repositories.EventRepository;
import com.environmentalreporting.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest

public class EventServiceTest {

    @Autowired
    EventService eventService;

    @MockBean
    EventRepository eventRepository;
    @MockBean
    UserRepository userRepository;

    Event event = new Event( "mock", "mock", new Date(), 2, "mock", "mock", 4, "mock", new HashSet<>());
    EventRequest eventRequest = new EventRequest( "mock", "mock", new Date(), 2, "mock", "mock", 4, "mock", new ArrayList<>());
    EventResponse eventResponse = new EventResponse( null,"mock", "mock", new Date(), 2, "mock", "mock", 4, "mock", new HashSet<>());

    @Test
    public void testCreateEvent() {
        // given
        when(eventRepository.save(any())).thenReturn(event);
        // when
        Event response = eventService.createEvent(eventRequest);
        assertEquals(event, response);
    }

    @Test
    public void testUpdateEvent() {
        when(eventRepository.save(any())).thenReturn(event);
        Event response = eventService.updateEvent(event);
        assertEquals(event,response);
    }

    @Test
    public void testGetEvents() {
        List<Event> events = new ArrayList<>();
        events.add(event);
        List<EventResponse> eventResponses = new ArrayList<>();
        eventResponses.add(eventResponse);
        when(eventRepository.findAll()).thenReturn(events);
        List<EventResponse> response = eventService.getEvents();
        assertEquals(eventResponses,response);
    }

    @Test
    public void testDeleteEvents() {
        doNothing().when(eventRepository).deleteById(any());
        eventService.deleteEvent(1);
        verify(eventRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testAddUserToEvent() {
        // given
        User user = new User(1L, "mock", "mockMail", "mockPassword", 0L, "mock", new HashSet<>(),new HashSet<>());
        when(eventRepository.findById(anyLong())).thenReturn(Optional.ofNullable(event));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        // when
        Event response = eventService.addUserToEvent(1, 1);

        // then
        assertEquals(this.event, response);

    }
}

