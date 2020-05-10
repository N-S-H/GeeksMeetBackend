package com.lfg.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lfg.entities.MemberActivity;
import com.lfg.entities.Team;


@RestController
@RequestMapping("lowesforgeeks/team")
public class TeamController extends BaseController{
	
	@PostMapping("/{memberId}/create")
	public Team create(@PathVariable(name = "memberId") int memberId, @RequestBody Team team)
	{
		int teamId = team.getTeamId();
		if(!organization.findMemberWithIdExists(memberId))
		{
			System.out.println("Only members in the organization can form a team");
		}
		else if(organization.isMemberPartOfATeam(memberId))
		{
		System.out.println("The team cannot be created because the creater is part of an existing team in the organization");
		System.out.println("Please communicate with the organization admin or the corresponding team admin to update the participation");
		}
		else if(organization.findTeamWithIdExists(teamId))
		{
			System.out.println("The team with id: "+ teamId + " is already present in the organization");
		}
		else if(team.getTeamName().length()>100)
		{
		   System.out.println("The length of team name must be maximum of 100 characters");
		}
		else 
		{
		team.addMemberToTeam(organization.getMemberWithId(memberId));
		MemberActivity memberActivity = organization.mapTheMemberActivityToTeamAsAdmin(memberId,teamId);
		memberActivityService.updateMemberActivity(memberActivity);
		organization.addTeamToOrganization(team);
		return teamService.createTeam(team);
		}
		return null;
	}
	
	public Team update(Team team)
	{
		int teamId = team.getTeamId();
		if(organization.findTeamWithIdExists(teamId))
		{
		organization.updateTeamInOrganization(team);
		return teamService.updateTeam(team);
		}
		return null;
	}
	
	
	
	@PutMapping("/{memberId}/changename/{teamId}/{newname}")
	public Team changeTeamName(@PathVariable(name = "memberId") int memberId,@PathVariable(name = "teamId") int teamId,
			@PathVariable(name = "newname") String newname)
	{
		if(newname.length()>100)
		{
			System.out.println("The length of team name must be maximum of 100 characters");
		}
		else if(organization.AuthenticateTheMemberForTeamUpdate(memberId, teamId))
		{
			Team team = organization.getTeamWithId(teamId);
			team.setTeamName(newname);
			return this.update(team);
		}
		return null;
	}
	
	@PutMapping("/{memberId}/addmember/{teamId}/{targetmemberId}")
	public String addTeamMember(@PathVariable(name="memberId") int memberId,@PathVariable(name="teamId") int teamId,@PathVariable(name ="targetmemberId") int targetMemberId)
	{
		if(organization.AuthenticateTheMemberForTeamUpdate(memberId, teamId))
		{
			if(organization.isMemberPartOfATeam(targetMemberId))
			{
				return "The member to be added is already part of an existing team";
			}
			else
			{
				organization.mapTheMemberActivityToTeamAsMember(targetMemberId, teamId);
				Team team = organization.getTeamWithId(teamId);
				team.addMemberToTeam(organization.getMemberWithId(targetMemberId));
				this.update(team);
				memberActivityService.updateMemberActivity(organization.getMemberActivityWithId(targetMemberId));
				return "The member with id: " + targetMemberId +" is successfully added to team with id: "+teamId;
			}
		}
		return "Failed to add member to team";
	}
	
	
	@PutMapping("/{memberId}/deletemember/{teamId}/{targetmemberId}")
	public String deleteTeamMember(@PathVariable(name="memberId") int memberId,@PathVariable(name="teamId") int teamId,@PathVariable(name ="targetmemberId") int targetMemberId)
	{
		if(organization.AuthenticateTheMemberForTeamUpdate(memberId, teamId))
		{
			if(!organization.isMemberExistsInTeam(targetMemberId, teamId))
			{
				return "The member to be deleted is not a part of given team";
			}
			else
			{
				organization.removeMemberActivityFromTeam(targetMemberId);
				Team team = organization.getTeamWithId(teamId);
				team.deleteMemberFromTeam(organization.getMemberWithId(targetMemberId));
				this.update(team);
				memberActivityService.updateMemberActivity(organization.getMemberActivityWithId(targetMemberId));
				return "The member with id: " + targetMemberId + " is successfully removed from the team";
			}
		}
		return "Failed to delete member from the team";
	}
	
	@PutMapping("/{memberId}/makeadmin/{teamId}/{targetmemberId}")
	public String makeTeamAdmin(@PathVariable(name="memberId") int memberId,@PathVariable(name="teamId") int teamId,@PathVariable(name ="targetmemberId") int targetMemberId)
	{
		if(organization.AuthenticateTheMemberForTeamUpdate(memberId, teamId))
		{
			if(!organization.isMemberExistsInTeam(targetMemberId, teamId))
			{
				return "The member is not part of the given team inorder to make admin";
			}
			else if(organization.isTeamAdmin(targetMemberId, teamId))
			{
				return "The member is already the admin of given team";
			}
			else
			{
				organization.removeMemberActivityFromTeam(targetMemberId);
				organization.mapTheMemberActivityToTeamAsAdmin(targetMemberId, teamId);
				MemberActivity memberActivity = organization.getMemberActivityWithId(targetMemberId);
				memberActivityService.updateMemberActivity(memberActivity);
				return "The member with id: " + targetMemberId + " is made admin of team with id: " + teamId;
			}
		}
		return "Failed to add team admin";
	}
	
	@PutMapping("/{memberId}/removeadmin/{teamId}/{targetmemberId}")
	public String removeTeamAdmin(@PathVariable(name="memberId") int memberId,@PathVariable(name="teamId") int teamId,@PathVariable(name ="targetmemberId") int targetMemberId)
	{
		if(organization.AuthenticateTheMemberForTeamUpdate(memberId, teamId))
		{
			if(!organization.isMemberExistsInTeam(targetMemberId, teamId))
			{
				return "The member is not part of the given team inorder to make admin";
			}
			else if(!organization.isTeamAdmin(targetMemberId, teamId))
			{
				return "The member is not the admin of given team";
			}
			else
			{
				organization.removeMemberActivityFromTeam(targetMemberId);
				organization.mapTheMemberActivityToTeamAsMember(targetMemberId, teamId);
				MemberActivity memberActivity = organization.getMemberActivityWithId(targetMemberId);
				memberActivityService.updateMemberActivity(memberActivity);
				return "The member with id: " + targetMemberId + " is removed as admin from team with id: " + teamId;
			}
		}
		return "Failed to remove team admin";
	}
	
	
	
	@GetMapping("/{memberId}/all")
	public List<Team> findall(@PathVariable(name = "memberId") int memberId)
	{
		if(organization.findMemberWithIdExists(memberId))
		return organization.getAllTeamsInOrganization();
		else
		{
			System.out.println("Only members in the organization can view all the teams");

		}
		return null;
	}
	
	@GetMapping("/{memberId}/{teamId}")
	public Team findById(@PathVariable(name="memberId") int memberId, @PathVariable(name="teamId") int teamId)
	{
		if(organization.findMemberWithIdExists(memberId) && organization.findTeamWithIdExists(teamId))
			return organization.getTeamWithId(teamId);
		else
		{
				System.out.println("Only members in the organization can view the details of an existing team");

		}
			return null;
	}
	
	public String delete(int teamId)
	{
		if(organization.findTeamWithIdExists(teamId))
		{
		organization.deleteTeamFromOrganization(organization.getTeamWithId(teamId));
		return teamService.deleteTeamById(teamId);
		}
		else
			return "The team with id: " +teamId +" is not present in the datacase";
	}
	
	@DeleteMapping("/{memberId}/delete/{teamId}")
	public void deleteTeam(@PathVariable(name="memberId") int memberId,@PathVariable(name="teamId") int teamId)
	{
        if(organization.AuthenticateTheMemberForTeamUpdate(memberId, teamId))
        {
        	this.delete(teamId);
        	List<MemberActivity> teamMemberActivities = organization.removeAllActivitiesInTeam(teamId);
        	
        	for(MemberActivity teamMemberActivity : teamMemberActivities)
        	{
        		memberActivityService.updateMemberActivity(teamMemberActivity);
        	}
        }
        return;
	}
	
}
