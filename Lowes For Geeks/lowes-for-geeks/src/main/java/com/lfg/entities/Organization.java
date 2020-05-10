package com.lfg.entities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lfg.comparators.PopularEventsComparator;
import com.lfg.comparators.TrendingEventsComparator;
import com.lfg.comparators.UpcomingEventsComparator;
import com.lfg.serviceinterfaces.OrganizationInterface;
import com.lfg.services.MemberService;
import com.lfg.services.TeamService;

public class Organization implements OrganizationInterface{
	
	@Autowired
	protected MemberService memberService;
	
	@Autowired
	protected TeamService teamService;
	
	List<Member> members = new ArrayList<Member>();
	List<Team> teams = new ArrayList<Team>(); 
	List<Event> events = new ArrayList<Event>();
	List<MemberActivity> memberActivities = new ArrayList<MemberActivity>();
	List<Integer> organizationAdmins = new ArrayList<Integer>();
	
	@Override
	public void addMemberToOrganization(Member member) {
		members.add(member);
		return;
	}
	
	@Override
	public void addTeamToOrganization(Team team) {
		teams.add(team);
		return;
	}
	
	@Override
	public boolean findMemberWithIdExists(int memberId) {
		if(members.isEmpty()) return false;
	    for(Member member:members)
	    {
	    	if(member.getMemberId() == memberId)
	    		return true;
	    }
	    return false;
	}
	
	@Override
	public boolean findTeamWithIdExists(int teamId) {
		if(teams.isEmpty()) return false;
		for(Team team:teams)
		{
			if(team.getTeamId() == teamId)
				return true;
		}
		return false;
	}
	
	@Override
	public void deleteMemberFromOrganization(Member member) {
		members.remove(member);
		return;
	}
	
	@Override
	public void deleteTeamFromOrganization(Team team) {
		
		teams.remove(team);
		return;
	}
	
	@Override
	public void updateMemberInOrganization(Member member) {
		for(Member memberItr:members)
		{
			if(memberItr.getMemberId() == member.getMemberId())
			{
				members.remove(memberItr);
				break;
			}
		}
		members.add(member);
		return;
	}
	
	@Override
	public void updateTeamInOrganization(Team team) {
		for(Team teamItr:teams)
		{
			if(teamItr.getTeamId() == team.getTeamId())
			{
			    teams.remove(teamItr);
				break;
			}
		}
		teams.add(team);
		return;
	}
	
	@Override
	public Member getMemberWithId(int memberId) {
		for(Member member:members)
		{
			if(member.getMemberId() == memberId)
				return member;
		}
		return null;
	}
	
	@Override
	public Team getTeamWithId(int teamId) {
		for(Team team:teams)
		{
			if(team.getTeamId() == teamId)
				return team;
		}
		return null;
	}
	
	@Override
	public List<Member> getAllMembersInOrganization() {
		return members;
	}
	
	@Override
	public List<Team> getAllTeamsInOrganization() {
		return teams;
	}

	@Override
	public void obtainTheExistingMembers(List<Member> membersGroup) {
		
		for(Member eachMember : membersGroup)
		{
		  	members.add(eachMember);
		}
		
		if(members.size() == 0)
		{
		Member member = createTheOrganizationAdmin();
		members.add(member);
		System.out.println("Created the organization admin initially!");
		}
		
		System.out.println("The members in the organization are: "+members.size());
		return;
	}
	
	@Override
	public Member createTheOrganizationAdmin()
	{
		Member member = new Member();
		member.setMemberId(1);
		member.setFirstname("Sriharsha");
		member.setLastname("Namilakonda");
		member.setMailId("sriharsha.namilakonda@gmail.com");
		member.setOrganizationAdmin(true);
		return member;
	}

	@Override
	public void obtainTheExistingTeams(List<Team> presentTeams) {
		for(Team eachTeam : presentTeams)
		{
			teams.add(eachTeam);
		}
		
		System.out.println("The teams in the organization are: "+teams.size());
		return;
	}
	

	@Override
	public void deleteEventFromOrganization(Event event) {
		events.remove(event);
		return;
	}

	@Override
	public boolean findEventWithIdExists(int eventId) {
		for(Event eachEvent : events)
		{
			if(eachEvent.getEventId() == eventId)
				return true;
		}
		return false;
	}

	

	@Override
	public List<Event> getAllEventsInOrganization() {
		return events;
	}

	@Override
	public void updateEventInOrganization(Event event) {
		for(Event eachEvent : events)
		{
			if(eachEvent.getEventId() == event.getEventId())
			{
				events.remove(event);
				break;
			}
		}
		events.add(event);
		return;
	}

	@Override
	public Event getEventWithId(int eventId) {
		for(Event eachEvent : events)
		{
			if(eachEvent.getEventId() == eventId)
			  return eachEvent;
		}
		return null;
	}

	@Override
	public void obtainTheExistingEvents(List<Event> currentEvents) {
	
		for(Event eachEvent : currentEvents)
		{
			events.add(eachEvent);
		}
		
		System.out.println("The events in the organization are: "+events.size());
		return;
	}

	@Override
	public void obtainTheMemberActivities(List<MemberActivity> allmemberActivities) {
		for(MemberActivity eachMemberActivity: allmemberActivities)
		{
			memberActivities.add(eachMemberActivity);
		}
		if(memberActivities.size() == 0)
		{
			memberActivities.add(theInitialAdminActivity());
		}
		System.out.println("The activities found in the organization initially are " + memberActivities.size());
	}
	
	@Override
	public MemberActivity theInitialAdminActivity() 
	{
		MemberActivity memberActivity = new MemberActivity();
		memberActivity.setMemberId(1);
		return memberActivity;
	}

	@Override
	public void loadTheOrganizationAdmins() {
		for(Member member: members)
		{
			if(member.isOrganizationAdmin())
				organizationAdmins.add(member.getMemberId());
		}
	}

	@Override
	public MemberActivity addMemberActivityToOrganization(int memberId) {
	    MemberActivity memberActivity = new MemberActivity();
		memberActivity.setMemberId(memberId);
		memberActivities.add(memberActivity);
		return memberActivity;
	}

	@Override
	public void deleteMemberActivityFromOrganization(int memberId) {
		
		for(MemberActivity memberActivity : memberActivities)
		{
			if(memberActivity.getMemberId() == memberId)
			{
				memberActivities.remove(memberActivity);
				break;
			}
		}
		return;
	}

	@Override
	public boolean isOrganizationAdmin(int memberId) {
		return organizationAdmins.contains(memberId);
	}

	@Override
	public boolean isMemberPartOfATeam(int memberId) {
		MemberActivity memberActivity = getMemberActivityWithId(memberId);
		return memberActivity.isPartOfATeam();
	}

	@Override
	public MemberActivity mapTheMemberActivityToTeamAsAdmin(int memberId, int teamId) {
		 MemberActivity memberActivity = getMemberActivityWithId(memberId);
		 memberActivity.setPartOfATeam(true);
		 memberActivity.setTeamId(teamId);
		 memberActivity.setTeamAdmin(true);
		 deleteExistingAndAddUpdatedActivity(memberId,memberActivity);
		 return memberActivity;
	}
	
	@Override
	public void deleteExistingAndAddUpdatedActivity(int memberId, MemberActivity memberActivity)
	{
		this.deleteMemberActivityFromOrganization(memberId);
		this.addMemberActivityToOrganizationWithObject(memberActivity);
	}
	
	@Override
	public void addMemberActivityToOrganizationWithObject(MemberActivity memberActivity)
	{
		memberActivities.add(memberActivity);
		return;
	}

	@Override
	public MemberActivity mapTheMemberActivityToTeamAsMember(int memberId, int teamId) {
		MemberActivity memberActivity = getMemberActivityWithId(memberId);
		memberActivity.setPartOfATeam(true);
		memberActivity.setTeamId(teamId);
		memberActivity.setTeamAdmin(false);
		this.deleteExistingAndAddUpdatedActivity(memberId,memberActivity);
		return memberActivity;
	}

	@Override
	public MemberActivity getMemberActivityWithId(int memberId) {
		for(MemberActivity memberActivity : memberActivities)
		{
			if(memberActivity.getMemberId() == memberId)
				return memberActivity;
		}
		System.out.println("Something went wrong! Unable to retrieve the member activity");
		return null;
	}

	@Override
	public boolean isMemberExistsInTeam(int memberId, int teamId) {
		MemberActivity memberActivity = this.getMemberActivityWithId(memberId);
		return memberActivity.getTeamId() == teamId;
	}

	@Override
	public boolean isTeamAdmin(int memberId, int teamId) {
		MemberActivity memberActivity = this.getMemberActivityWithId(memberId);
		return memberActivity.isTeamAdmin();
	}

	@Override
	public void removeMemberActivityFromTeam(int targetMemberId) {
		MemberActivity memberActivity = this.getMemberActivityWithId(targetMemberId);
		memberActivity.setPartOfATeam(false);
		memberActivity.setTeamId(0);
		memberActivity.setTeamAdmin(false);
		this.deleteExistingAndAddUpdatedActivity(targetMemberId, memberActivity);
		return;
	}
	
	
    @Override
	public List<MemberActivity> removeAllActivitiesInTeam(int teamId) {
		List<MemberActivity> updatedActivities = new ArrayList<MemberActivity>();
		for(MemberActivity memberActivity : memberActivities)
		{
			if(memberActivity.getTeamId() == teamId)
			{
				updatedActivities.add(memberActivity);
			}
		}
		
		for(MemberActivity teamMemberActivity : updatedActivities)
		{
			teamMemberActivity.setPartOfATeam(false);
			teamMemberActivity.setTeamAdmin(false);
			teamMemberActivity.setTeamId(0);
			this.deleteExistingAndAddUpdatedActivity(teamMemberActivity.getMemberId(),teamMemberActivity);
		}
		return updatedActivities;
	}

    @Override
	public void addAsOrganizationAdmin(int newMemberId) {
	     if(!this.isOrganizationAdmin(newMemberId))
	     {
	    	 this.organizationAdmins.add(newMemberId);
	     }
	}

    @Override
	public void removeAsOrganizationAdmin(Integer targetMemberId) {
		this.organizationAdmins.remove(targetMemberId);
	}

	@Override
	public Event addEventToOrganization(int memberId, Event event) {
		event.checkRecurrence();
		if(event.isTeamEvent())
		{
			event.setTeamIdForTeamEvent(this.getMemberActivityWithId(memberId).getTeamId());
		}
		event.setCreatedBy(this.getMemberWithId(memberId));
		event.setCreatedDate(LocalDateTime.now().toString());
		events.add(event);
		return event;
	}

	@Override
	public boolean isOrganizationEvent(Event event) {
		return event.isOrganizationEvent();
	}

	@Override
	public boolean isTeamEvent(Event event) {
		return event.isTeamEvent();
	}

	@Override
	public boolean isPrivateEvent(Event event) {
		return event.isPrivateEvent();
	}

	@Override
	public boolean isMemberATeamAdmin(int memberId) {
		MemberActivity memberActivity = this.getMemberActivityWithId(memberId);
		return memberActivity.isTeamAdmin();
	}

	@Override
	public boolean AuthenticateEventCreation(Event event)
	{
		LocalDateTime eventStartDate = event.getParsedStartDate();
		LocalDateTime presentDate = LocalDateTime.now();
		long duration = ChronoUnit.DAYS.between(presentDate,eventStartDate);
		if(eventStartDate.compareTo(presentDate) > 0)
		{
			if(event.isOrganizationEvent() && duration < 60)
			{
				System.out.println("Organization event should be created atleast two months prior to the event start date");
				return false;
			}
			else if(event.isTeamEvent() && duration < 7)
			{
				System.out.println("Team event should be created atleast one week prior to the event start date");
				return false;
			}
			else if(event.isPrivateEvent() && duration < 2)
			{
				System.out.println("Private event should be created atleast 2 days prior to the start date");
				return false;
			}
			else if(!event.isOrganizationEvent() && !event.isTeamEvent() && !event.isPrivateEvent())
			{
				System.out.println("The event type is not valid");
				return false;
			}
			return true;
		}
		System.out.println("Failed to create the event");
		return false;
	}
	
	@Override
	public boolean AuthenticateEventUpdation(Event currentEvent) {
		LocalDateTime eventStartDate = currentEvent.getParsedStartDate();
		LocalDateTime presentDate = LocalDateTime.now();
		long duration = ChronoUnit.DAYS.between(presentDate,eventStartDate);
		if(eventStartDate.compareTo(presentDate) > 0)
		{
			if(currentEvent.isOrganizationEvent() && duration < 30)
			{
				System.out.println("Organization event should be updated atleast one month prior to the event start date");
				return false;
			}
			else if(currentEvent.isTeamEvent() && duration < 7)
			{
				System.out.println("Team event should be updated atleast one week prior to the event start date");
				return false;
			}
			else if(currentEvent.isPrivateEvent() && duration < 2)
			{
				System.out.println("Private event should be updated atleast 2 days prior to the start date");
				return false;
			}
			return true;
		}
		System.out.println("Failed to update the event");
		return false;
	}
	
	@Override
	public boolean AuthenticateEventDeletion(Event event)
	{
		LocalDateTime eventStartDate = event.getParsedStartDate();
		LocalDateTime presentDate = LocalDateTime.now();
		long duration = ChronoUnit.DAYS.between(presentDate,eventStartDate);
		if(eventStartDate.compareTo(presentDate)<0)
		{
			System.out.println("The event that is already started cannot be cancelled");
			return false;
		}
		else
		{
             if(event.isOrganizationEvent() && duration < 14)
             {
            	 System.out.println("The organization event must be cancelled atleast two weeks prior to the start date");
            	 return false;
             }
             else if(event.isTeamEvent() && duration < 7)
             {
            	 System.out.println("The team event must be cancelled atleast one week prior to the start date");
            	 return false; 
             }
             else if(event.isPrivateEvent() && duration < 2)
             {
            	 System.out.println("The private event must be cancelled atleast two days prior to the start date");
            	 return false; 
             }
             return true;
		}
		
	}

	@Override
	public Event incrementViews(int eventId) {
		Event event=this.getEventWithId(eventId);
		event.setNumberOfViews(event.getNumberOfViews()+1);
		this.updateEventInOrganization(event);
		return event;
	}
	
	@Override
	public List<Event> getTrendingEvents() {
		List<Event> trendingEvents = new ArrayList<Event>();
		for(Event event:events)
		{
			if(this.isTrendingEvent(event))
				trendingEvents.add(event);
		}
		Collections.sort(trendingEvents,new TrendingEventsComparator());
		Collections.reverse(trendingEvents);
		return trendingEvents;
	}

	@Override
	public boolean isTrendingEvent(Event event) {
		if(event.isPrivateEvent()||event.isExpired()) return false;
		LocalDateTime eventStartDate = event.getParsedStartDate();
		LocalDateTime presentDate = LocalDateTime.now();
		LocalDateTime eventCreatedDate = event.getParsedCreatedDate();
		long durationToStart = ChronoUnit.DAYS.between(presentDate,eventStartDate);
		long durationFromCreated = ChronoUnit.DAYS.between(eventCreatedDate, presentDate);
		if(durationFromCreated < 60 || durationToStart < 8 || event.isRecurring())
			return false;
		return true;
	}

	@Override
	public List<Event> getPopularEvents() {
		List<Event> popularEvents = new ArrayList<Event>();
		for(Event event:events)
		{
			if(this.ispopularEvent(event))
				popularEvents.add(event);
		}
		Collections.sort(popularEvents,new PopularEventsComparator());
		Collections.reverse(popularEvents);
		return popularEvents;
	}

	@Override
	public boolean ispopularEvent(Event event) {
		if(event.isPrivateEvent() || event.isExpired()) return false;
		LocalDateTime eventStartDate = event.getParsedStartDate();
		LocalDateTime presentDate = LocalDateTime.now();
		LocalDateTime eventCreatedDate = event.getParsedCreatedDate();
		long durationToStart = ChronoUnit.DAYS.between(presentDate,eventStartDate);
		long durationFromCreated = ChronoUnit.DAYS.between(eventCreatedDate, presentDate);
		if(durationFromCreated < 30 || durationToStart < 3)
			return false;
		return true;
	}

	@Override
	public List<Event> getUpcomingEvents() {
		List<Event> upcomingEvents = new ArrayList<Event>();
		for(Event event:events)
		{
			if(this.isUpcomingEvent(event))
				upcomingEvents.add(event);
		}
		Collections.sort(upcomingEvents,new UpcomingEventsComparator());
		Collections.reverse(upcomingEvents);
		return upcomingEvents;
	}
	
	@Override
	public boolean isUpcomingEvent(Event event) {
		if(event.isExpired()) return false;
		LocalDateTime eventStartDate = event.getParsedStartDate();
		LocalDateTime presentDate = LocalDateTime.now();
		LocalDateTime eventCreatedDate = event.getParsedCreatedDate();
		long durationToStart = ChronoUnit.DAYS.between(presentDate,eventStartDate);
		long durationFromCreated = ChronoUnit.DAYS.between(eventCreatedDate, presentDate);
		if(durationFromCreated < 7 || durationToStart < 2)
			return false;
		return true;
	}

	@Override
	public boolean didMemberLikeEvent(int memberId, int eventId) {
		return this.getMemberActivityWithId(memberId).getLikedEvents().contains(eventId);
	}

	@Override
	public Event incrementLikes(int eventId) {
		Event event=this.getEventWithId(eventId);
		event.setNumberOfLikes(event.getNumberOfLikes()+1);
		this.updateEventInOrganization(event);
		return event;
	}

	@Override
	public MemberActivity addToLikedEventsOfMember(int memberId, int eventId) {
		MemberActivity memberActivity = this.getMemberActivityWithId(memberId);
		memberActivity.addToLikedEvents(eventId);
		this.deleteExistingAndAddUpdatedActivity(memberId, memberActivity);
		return memberActivity;
	}

	@Override
	public Event decrementLikes(int eventId) {
		Event event=this.getEventWithId(eventId);
		event.setNumberOfLikes(event.getNumberOfLikes()-1);
		this.updateEventInOrganization(event);
		return event;
	}

	@Override
	public MemberActivity deleteFromLikedEventsOfMember(int memberId, int eventId) {
		MemberActivity memberActivity = this.getMemberActivityWithId(memberId);
		memberActivity.deleteFromLikedEvents(eventId);
		this.deleteExistingAndAddUpdatedActivity(memberId, memberActivity);
		return memberActivity;
	}

	@Override
	public boolean isMemberWatchingEvent(int memberId, int eventId) {
		return this.getMemberActivityWithId(memberId).getWatchedEvents().contains(eventId);
	}

	@Override
	public Event incrementWatches(int eventId) {
		Event event = this.getEventWithId(eventId);
		event.setNumberOfWatchers(event.getNumberOfWatchers()+1);
		this.updateEventInOrganization(event);
		return event;
	}

	@Override
	public MemberActivity addToWatchedEventsOfMember(int memberId, int eventId) {
		MemberActivity memberActivity = this.getMemberActivityWithId(memberId);
		memberActivity.addToWatchedEvents(eventId);
		this.deleteExistingAndAddUpdatedActivity(memberId, memberActivity);
		return memberActivity;
	}

	@Override
	public Event decrementWatches(int eventId) {
		Event event = this.getEventWithId(eventId);
		event.setNumberOfWatchers(event.getNumberOfWatchers()-1);
		this.updateEventInOrganization(event);
		return event;
	}

	@Override
	public MemberActivity deleteFromWatchedEventsOfMember(int memberId, int eventId) {
		MemberActivity memberActivity = this.getMemberActivityWithId(memberId);
		memberActivity.deleteFromWatchedEvents(eventId);
		this.deleteExistingAndAddUpdatedActivity(memberId, memberActivity);
		return memberActivity;
	}

	@Override
	public boolean isMemberParticipatingInEvent(int memberId, int eventId) {
		return this.getMemberActivityWithId(memberId).getParticipatingEvents().contains(eventId);
	}

	@Override
	public Event incrementParticipants(int eventId) {
		Event event=this.getEventWithId(eventId);
		event.setNumberOfParticipants(event.getNumberOfParticipants()+1);
		this.updateEventInOrganization(event);
		return event;
	}

	@Override
	public MemberActivity addToPartcipatingEventsOfMember(int memberId, int eventId) {
	      MemberActivity memberActivity = this.getMemberActivityWithId(memberId);
	      memberActivity.addToParticipatingEvents(eventId);
	      this.deleteExistingAndAddUpdatedActivity(memberId, memberActivity);
	      return memberActivity;
	}

	@Override
	public Event decrementParticipants(int eventId) {
		Event event = this.getEventWithId(eventId);
		event.setNumberOfParticipants(event.getNumberOfParticipants() - 1);
		this.updateEventInOrganization(event);
		return event;
	}

	@Override
	public MemberActivity deleteFromParticipatedEventsOfMember(int memberId, int eventId) {
		MemberActivity memberActivity = this.getMemberActivityWithId(memberId);
		memberActivity.deleteFromParticipatingEvents(eventId);
		this.deleteExistingAndAddUpdatedActivity(memberId, memberActivity);
		return memberActivity;
	}

	@Override
	public boolean checkExpired(Event currentEvent) {
		LocalDateTime eventendDate = currentEvent.getParsedEndDate();
		LocalDateTime presentDate = LocalDateTime.now();
		if(eventendDate.compareTo(presentDate) < 0)
			return true;
		return false;
	}

	@Override
	public Event markEventExpired(Event currentEvent) {
		currentEvent.setExpired(true);
		this.updateEventInOrganization(currentEvent);
		return currentEvent;
	}

	@Override
	public List<Event> markTheNonRecurringExpiredEvents() {
		List<Event> expiredEvents = new ArrayList<Event>();
		for(Event event:events)
		{
			if(!event.isRecurring() && !event.isExpired() && this.checkExpired(event))
			{
				expiredEvents.add(this.markEventExpired(event));
			}
		}
		return expiredEvents;
	}

	@Override
	public boolean isInitialState() {
		return members.isEmpty() && events.isEmpty() && teams.isEmpty();
	}

	@Override
	public List<MemberActivity> filterOutActivitiesUponEventDeletion(int eventId) {
		List<MemberActivity> involvedMemberActivities = new ArrayList<MemberActivity>();
		for(MemberActivity memberActivity : memberActivities)
		{
			boolean involvedInEvent = false;
			int memberId = memberActivity.getMemberId();
			if(this.didMemberLikeEvent(memberId, eventId))
			{
				memberActivity.deleteFromLikedEvents(eventId);
				involvedInEvent=true;
			}
			if(this.isMemberWatchingEvent(memberId, eventId))
			{
				memberActivity.deleteFromWatchedEvents(eventId);
				involvedInEvent=true;
			}
			if(this.isMemberParticipatingInEvent(memberId, eventId))
			{
				memberActivity.deleteFromParticipatingEvents(eventId);
				involvedInEvent = true;
			}
			
			if(involvedInEvent)
			{
				involvedMemberActivities.add(memberActivity);
				this.deleteExistingAndAddUpdatedActivity(memberId, memberActivity);
			}
			
		}
		return involvedMemberActivities;
	}
	
	@Override
	public boolean AuthenticateTheMemberForTeamUpdate(int memberId,int teamId)
	{
		if(!this.findMemberWithIdExists(memberId))
		{
			System.out.println("The member is not even the part of organization to update or delete team");
		}
		else if(!this.findTeamWithIdExists(teamId))
		{
		  System.out.println("The entered team is not present in the organization");
		}
		else if(!this.isMemberExistsInTeam(memberId,teamId) && !this.isOrganizationAdmin(memberId))
		{
			System.out.println("The member is neither the part of the mentioned team nor the admin of organization");
		}
		else if(!this.isOrganizationAdmin(memberId) && !this.isTeamAdmin(memberId,teamId))
		{
			System.out.println("Only organization admins and team admins can update or delete the information of the team");
		}
		else return true;
		
		return false;
	}
	
}
