package com.lfg.serviceinterfaces;

import java.util.List;
import java.util.Optional;

import com.lfg.entities.MemberActivity;

public interface MemberActivityServiceInterface {
	
	Optional<MemberActivity> findById(int memberId);
	String deleteById(int memberId);
	MemberActivity createMemberActivity(MemberActivity memberActivity);
	MemberActivity updateMemberActivity(MemberActivity memberActivity);
	List<MemberActivity> getAllActivities();
	boolean isCollectionEmpty();
}
