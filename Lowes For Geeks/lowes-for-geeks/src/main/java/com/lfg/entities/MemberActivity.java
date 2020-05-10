package com.lfg.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection="member-activity")
public class MemberActivity {
 
	@Id
	private int memberId;
	private boolean partOfATeam = false;
	private int teamId = 0;
	private List<Integer> participatingEvents=new ArrayList<Integer>();
	private List<Integer> LikedEvents = new ArrayList<Integer>();
	private List<Integer> watchedEvents = new ArrayList<Integer>();
	private boolean teamAdmin = false;
	
	
	public int getMemberId() {
		return memberId;
	}
	
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	
	public boolean isPartOfATeam() {
		return partOfATeam;
	}
	
	public void setPartOfATeam(boolean partOfATeam) {
		this.partOfATeam = partOfATeam;
	}
	
	public int getTeamId() {
		return teamId;
	}
	
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	
	public List<Integer> getParticipatingEvents() {
		return participatingEvents;
	}
	
	public void setParticipatingEvents(List<Integer> participatingEvents) {
		this.participatingEvents = participatingEvents;
	}
	
	public List<Integer> getLikedEvents() {
		return LikedEvents;
	}
	
	public void setLikedEvents(List<Integer> likedEvents) {
		LikedEvents = likedEvents;
	}
	
	public List<Integer> getWatchedEvents() {
		return watchedEvents;
	}
	
	public void setWatchedEvents(List<Integer> watchedEvents) {
		this.watchedEvents = watchedEvents;
	}
	
	public boolean isTeamAdmin() {
		return teamAdmin;
	}
	
	public void setTeamAdmin(boolean teamAdmin) {
		this.teamAdmin = teamAdmin;
	}

	public void addToLikedEvents(int eventId) {
	    this.LikedEvents.add(eventId);
	}

	public void deleteFromLikedEvents(Integer eventId) {
		this.LikedEvents.remove(eventId);
	}
	
	public void addToParticipatingEvents(int eventId) {
		this.participatingEvents.add(eventId);
	}
	
	public void deleteFromParticipatingEvents(Integer eventId) {
		this.participatingEvents.remove(eventId);
	}
	
	public void addToWatchedEvents(int eventId) {
		this.watchedEvents.add(eventId);
	}
	
	public void deleteFromWatchedEvents(Integer eventId) {
		this.watchedEvents.remove(eventId);
	}
}
