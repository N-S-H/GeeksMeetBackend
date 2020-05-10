package com.lfg.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lfg.entities.MemberActivity;

public interface MemberActivityRepository extends MongoRepository<MemberActivity,Integer>{
	
}
