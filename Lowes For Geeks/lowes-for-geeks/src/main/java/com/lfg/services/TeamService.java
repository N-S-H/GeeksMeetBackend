package com.lfg.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lfg.entities.Team;
import com.lfg.repositories.TeamRepository;
import com.lfg.serviceinterfaces.TeamServiceInterface;


@Service
public class TeamService implements TeamServiceInterface{
	
	@Autowired
	private TeamRepository teamRepository;

	@Override
	public Team createTeam(Team team) {
		return teamRepository.save(team);
	}

	@Override
	public Team updateTeam(Team team) {
		return teamRepository.save(team);
	}

	@Override
	public String deleteTeamById(int id) {
	    teamRepository.deleteById(id);
		return "The team with id:" + id +" is successfully deleted";
	}

	@Override
	public List<Team> getAllTeams() {
		return teamRepository.findAll();
	}

	@Override
	public boolean CollectionIsEmpty() {
		if( teamRepository.count() ==0) return true;
		else return false;
	}
	


}
