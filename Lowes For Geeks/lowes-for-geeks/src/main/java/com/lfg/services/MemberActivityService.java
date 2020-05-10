package com.lfg.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lfg.entities.MemberActivity;
import com.lfg.repositories.MemberActivityRepository;
import com.lfg.serviceinterfaces.MemberActivityServiceInterface;


@Service
public class MemberActivityService implements MemberActivityServiceInterface {

	@Autowired
	private MemberActivityRepository memberActivityRepository;
	
	@Override
	public Optional<MemberActivity> findById(int memberId) {
		return memberActivityRepository.findById(memberId);
	}

	@Override
	public String deleteById(int memberId) {
		memberActivityRepository.deleteById(memberId);
		return "The activities of member with Id: " + memberId +" have been erased from the organization";
	}

	@Override
	public MemberActivity createMemberActivity(MemberActivity memberActivity) {
	    return memberActivityRepository.save(memberActivity);
	}

	@Override
	public MemberActivity updateMemberActivity(MemberActivity memberActivity) {
		return memberActivityRepository.save(memberActivity);
	}

	@Override
	public List<MemberActivity> getAllActivities() {
		return memberActivityRepository.findAll();
	}

	@Override
	public boolean isCollectionEmpty() {
		if(memberActivityRepository.count() == 0) return true;
		return false;
	}
	

}
