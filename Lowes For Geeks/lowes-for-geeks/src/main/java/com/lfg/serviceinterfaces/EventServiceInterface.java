package com.lfg.serviceinterfaces;

import java.util.List;
import java.util.Optional;

import com.lfg.entities.Event;


public interface EventServiceInterface {

	 Event createEvent(Event event);
     
     String deleteEventById(int id);
     
     Event updateEvent(Event event);
     
     Optional<Event> getEventbyId(int eventId);
     
     List<Event> getAllEvents();
     
     boolean CollectionIsEmpty();
}
