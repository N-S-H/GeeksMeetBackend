package com.lfg.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lfg.entities.Team;

public interface TeamRepository extends MongoRepository<Team,Integer>{

	
}
