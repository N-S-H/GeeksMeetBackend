package com.lfg.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lfg.entities.Event;
import com.lfg.repositories.EventRepository;
import com.lfg.serviceinterfaces.EventServiceInterface;

@Service
public class EventService implements EventServiceInterface{
	
	@Autowired
	private EventRepository eventRepository;

	@Override
	public Event createEvent(Event event) {
		return eventRepository.save(event);
	}

	@Override
	public String deleteEventById(int id) {
		eventRepository.deleteById(id);
		return "The event with id: "+ id + " is successfully deleted";
	}

	@Override
	public Event updateEvent(Event event) {
		return eventRepository.save(event);
	}

	@Override
	public Optional<Event> getEventbyId(int eventId) {
		return eventRepository.findById(eventId);
	}

	@Override
	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}

	@Override
	public boolean CollectionIsEmpty() {
		if(eventRepository.count() == 0) return true;
		return false;
	}
	

}
