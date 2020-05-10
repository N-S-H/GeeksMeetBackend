package com.lfg.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lfg.entities.Event;

public interface EventRepository extends MongoRepository<Event,Integer>{
	

}
