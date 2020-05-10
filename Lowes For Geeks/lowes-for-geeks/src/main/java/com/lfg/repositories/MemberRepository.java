package com.lfg.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lfg.entities.Member;

public interface MemberRepository extends MongoRepository<Member,Integer>{

}
