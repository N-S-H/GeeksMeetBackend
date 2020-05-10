package com.lfg.serviceinterfaces;

import java.util.List;

import com.lfg.entities.Team;

public interface TeamServiceInterface {
	
	Team createTeam(Team team);
	
	Team updateTeam(Team team);
	
	String deleteTeamById(int id);
	
	List<Team> getAllTeams();
	
	boolean CollectionIsEmpty();
	
	
}
