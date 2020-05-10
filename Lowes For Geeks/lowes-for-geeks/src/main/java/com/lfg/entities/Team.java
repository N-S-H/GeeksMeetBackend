package com.lfg.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Document(collection = "teams")
public class Team {
    
	@Id
	private int teamId;
	private String teamName;
	
	/*
	   No need input the following field, the creator of team will be added as team administrator automatically,
	   The other members will be added as they join the team 
	 */
	
	private List<Member> teamMembers = new ArrayList<Member>();
	
	public int getTeamId() {
		return teamId;
	}
	
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public List<Member> getTeamMembers() {
		return teamMembers;
	}
	
	public void setTeamMembers(List<Member> teamMembers) {
		this.teamMembers = teamMembers;
	}
	
	public void addMemberToTeam(Member member) {
		if(!teamMembers.contains(member))
		{
		teamMembers.add(member);
		}
	}
	
	public void deleteMemberFromTeam(Member member) {
		
		if(teamMembers.contains(member))
		{
			teamMembers.remove(member);
		}
	}
	
}
