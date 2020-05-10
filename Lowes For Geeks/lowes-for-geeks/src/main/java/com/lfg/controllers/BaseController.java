package com.lfg.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lfg.entities.Event;
import com.lfg.entities.Organization;
import com.lfg.services.EventService;
import com.lfg.services.MemberActivityService;
import com.lfg.services.MemberService;
import com.lfg.services.TeamService;

@RestController
@RequestMapping("lowesforgeeks")
public class BaseController {

	@Autowired
	protected MemberService memberService;
	
	@Autowired
	protected TeamService teamService;
	
	@Autowired
	protected EventService eventService;
	
	@Autowired
	protected MemberActivityService memberActivityService;
	
	protected static Organization organization = new Organization();
	
	@GetMapping("")
	public String updateCollections()
	{
		if(organization.isInitialState())
		{
		
		organization.obtainTheExistingMembers(memberService.getAllMembers());
		organization.obtainTheExistingTeams(teamService.getAllTeams());
		organization.obtainTheExistingEvents(eventService.getAllEvents());
		organization.obtainTheMemberActivities(memberActivityService.getAllActivities());
		organization.loadTheOrganizationAdmins();
		
		if(memberService.CollectionIsEmpty())
		{
			memberService.createMember(organization.createTheOrganizationAdmin());
			System.out.println("The organization admin is added to the database initially");
		}
		
		if(memberActivityService.isCollectionEmpty())
		{
			memberActivityService.createMemberActivity(organization.theInitialAdminActivity());
			System.out.println("The member activity of oraganization admin has been created in the database initially");
		}
		
		List<Event> expiredEvents = organization.markTheNonRecurringExpiredEvents();
		for(Event eachExpiredEvent : expiredEvents)
		{
			eventService.updateEvent(eachExpiredEvent);
		}
		
		}
		return "Hello User! Welcome Lowes For Geeks application! \n All the data is restored and functionalities are initiated in the application!";
	}
	
}
