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

import com.lfg.entities.Event;
import com.lfg.entities.MemberActivity;

@RestController
@RequestMapping("lowesforgeeks/event")
public class EventController extends BaseController{
	

	@PostMapping("/{memberId}/create")
	public Event create(@PathVariable(name="memberId") int memberId, @RequestBody Event event)
	{
		int eventId = event.getEventId();
		if(!organization.findMemberWithIdExists(memberId))
		{
			System.out.println("Only members in the organization can create events!");
			
		}
		else if(organization.findEventWithIdExists(eventId))
		{
			System.out.println("The event with given id is already created in the organization");
		}
		else if(organization.isOrganizationEvent(event) && !organization.isOrganizationAdmin(memberId))
		{
			System.out.println("Only the organization admins can create the organization events");
		}
		else if(organization.isTeamEvent(event) && !organization.isMemberATeamAdmin(memberId) && !organization.isOrganizationAdmin(memberId))
		{
			System.out.println("Only the organization admins or team admins can create team events");
		}
		else if(event.getParsedEndDate().compareTo(event.getParsedStartDate()) < 0)
		{
			System.out.println("The event end date should not be less than event start date");
		}
		else if(event.getName().length()>100)
		{
			System.out.println("The event name should contain maximum of 100 characters");
		}
		else if(organization.AuthenticateEventCreation(event))
		{
			Event modifiedEvent= organization.addEventToOrganization(memberId,event);
			return eventService.createEvent(modifiedEvent);
		}
		return null;
	}
	
	@PutMapping("/{memberId}/update")
	public Event update(@PathVariable(name="memberId") int memberId, @RequestBody Event modifiedEvent)
	{
		int eventId = modifiedEvent.getEventId();
		if(!organization.findEventWithIdExists(eventId))
		{
		System.out.println("The event with given id does not exist in the organization");
		return null;
		}
		
		Event currentEvent = organization.getEventWithId(eventId);
		if(!organization.findMemberWithIdExists(memberId))
		{
			System.out.println("The member with given id is not a part of organization to update event");
		}
		else if(currentEvent.getEventType() != modifiedEvent.getEventType())
		{
			System.out.println("The type of event cannot be modified");
		}
		else if(currentEvent.isOrganizationEvent() && !organization.isOrganizationAdmin(memberId))
		{
			System.out.println("Only organization admins can update the organization events!");
		}
		else if(currentEvent.isPrivateEvent() && (currentEvent.getCreatedBy().getMemberId() !=memberId))
		{
			System.out.println("Only creaters can update the details regarding the private event");
		}
		else if(modifiedEvent.getParsedEndDate().compareTo(modifiedEvent.getParsedStartDate()) < 0)
		{
			System.out.println("The event end date should not be less than event start date");
		}
		else if(currentEvent.isTeamEvent() && !organization.isOrganizationAdmin(memberId) && !organization.isTeamAdmin(memberId, currentEvent.getTeamIdForTeamEvent()))
		{
			System.out.println("Only Team Admins and Organization admins can update the team event");
		}
		else if(modifiedEvent.getParsedStartDate().compareTo(currentEvent.getParsedStartDate()) < 0)
		{
			System.out.println("The event can be postponed but it can never be preponed");
		}
		else if(modifiedEvent.getName().length()>100)
		{
			System.out.println("The event name should contain maximum of 100 characters");
		}
		else if(organization.AuthenticateEventUpdation(currentEvent))
		{
			currentEvent.setName(modifiedEvent.getName());
			currentEvent.setDescription(modifiedEvent.getDescription());
			currentEvent.setStartDate(modifiedEvent.getStartDate());
			currentEvent.setEndDate(modifiedEvent.getEndDate());
			currentEvent.setLocation(modifiedEvent.getLocation());
			currentEvent.checkRecurrence();
			organization.updateEventInOrganization(currentEvent);
			return eventService.updateEvent(currentEvent);
		}
		return null;
	}
	

	@DeleteMapping("/{memberId}/cancel/{eventId}")
	public String delete(@PathVariable(name="memberId") int memberId,@PathVariable(name = "eventId") int eventId)
	{
		if(!organization.findMemberWithIdExists(memberId))
		{
			return "The member is not the part of organization to delete the event";
		}
		else if(!organization.findEventWithIdExists(eventId))
		{
			return "The event with the given id does not exist in the organization";
		}
		else if(organization.AuthenticateEventDeletion(organization.getEventWithId(eventId)))
		{
			Event event=organization.getEventWithId(eventId);
			if(event.isPrivateEvent() && event.getCreatedBy().getMemberId()!=memberId)
			{
				return "The private events can only be cancelled by creaters";
			}
			else if(event.isTeamEvent() && !organization.isOrganizationAdmin(memberId)) 
			{
			   if(!organization.isMemberATeamAdmin(memberId))
			   {
				   return "Only team admins or organization admins can delete team events";
			   }
			   else if(event.getTeamIdForTeamEvent() != organization.getMemberActivityWithId(memberId).getTeamId())
			   {
				   return "The team admin is not a part of corresponding team event to delete it";
			   }
			   else
				{
				   List<MemberActivity> updatedMemberActivities = organization.filterOutActivitiesUponEventDeletion(eventId);
				   this.updateMemberActivitiesOnDeletion(updatedMemberActivities);
				   return this.deleteEvent(eventId);
				}
			}
			else if(event.isOrganizationEvent() && !organization.isOrganizationAdmin(memberId))
			{
				return "Only organization admins can delete organization events";
			}
			else 
			{
				List<MemberActivity> updatedMemberActivities = organization.filterOutActivitiesUponEventDeletion(eventId);
				this.updateMemberActivitiesOnDeletion(updatedMemberActivities);
				return this.deleteEvent(eventId);
			}
		
		}
		return "Failed to delete the event";
	}
	
	
	public void updateMemberActivitiesOnDeletion(List<MemberActivity> updatedMemberActivities) {
		for(MemberActivity memberActivity:updatedMemberActivities)
		{
			memberActivityService.updateMemberActivity(memberActivity);
		}
		return;
	}

	@GetMapping("/{memberId}/view/{eventId}")
	public Event view(@PathVariable(name="memberId") int memberId,@PathVariable(name="eventId") int eventId)
	{
		if(!organization.findMemberWithIdExists(memberId))
		{
			System.out.println("Only the members who are part of organization can view the events");
			return null;
		}
		else if(!organization.findEventWithIdExists(eventId))
		{
			System.out.println("The event to be viewed does not exist in the organization");
			return null;
		}
		Event currentEvent = organization.getEventWithId(eventId);
		if(currentEvent.isPrivateEvent() && currentEvent.getCreatedBy().getMemberId()!=memberId)
		{
			System.out.println("The private events can only be viewed by the creaters");
			return null;
		}
		else
		{
			 if(!currentEvent.isRecurring() && organization.checkExpired(currentEvent) && !organization.getEventWithId(eventId).isExpired())
			 {
			    currentEvent = organization.markEventExpired(currentEvent);
			 }
		     	currentEvent=organization.incrementViews(eventId);
		}
		return eventService.updateEvent(currentEvent);
	}
	
	@GetMapping("/{memberId}/trendingevents")
	public List<Event> trendingEvents(@PathVariable(name="memberId") int memberId)
	{
		if(!organization.findMemberWithIdExists(memberId))
		{
			System.out.println("Only members in the organization can view the trending events");
		}
		else
		{
			return organization.getTrendingEvents();
		}
		return null;
	}
	
	@GetMapping("/{memberId}/popularevents")
	public List<Event> popularEvents(@PathVariable(name="memberId") int memberId)
	{
		if(!organization.findMemberWithIdExists(memberId))
		{
			System.out.println("Only members in the organization can view the popular events");
		}
		else
		{
			return organization.getPopularEvents();
		}
		return null;
	}
	
	@GetMapping("/{memberId}/upcomingevents")
	public List<Event> upcomingEvents(@PathVariable(name="memberId") int memberId)
	{
		if(!organization.findMemberWithIdExists(memberId))
		{
			System.out.println("Only members in the organization can view the upcoming events");
		}
		else
		{
			return organization.getUpcomingEvents();
		}
		return null;
	}
	
	@PutMapping("/{memberId}/like/{eventId}")
	public String likeEvent(@PathVariable(name="memberId") int memberId, @PathVariable(name="eventId") int eventId)
	{
		if(!organization.findMemberWithIdExists(memberId))
		{
			return "The member with given Id does not exist in the organization";
		}
		else if(!organization.findEventWithIdExists(eventId))
		{
			return "The mentioned event does not exist in the organization";
		}
		else if(organization.didMemberLikeEvent(memberId,eventId))
		{
			return "The member already liked the event!";
		}
		else
		{
			Event currentEvent = organization.incrementLikes(eventId);
			eventService.updateEvent(currentEvent);
			MemberActivity memberActivity = organization.addToLikedEventsOfMember(memberId,eventId);
			memberActivityService.updateMemberActivity(memberActivity);
			return "Member with id: " + memberId + " sucessfully liked the event with id: " + eventId;
		}
	}
	
	@PutMapping("/{memberId}/unlike/{eventId}")
	public String unLikeEvent(@PathVariable(name="memberId") int memberId, @PathVariable(name="eventId") int eventId)
	{
		if(!organization.findMemberWithIdExists(memberId))
		{
			return "The member with given Id does not exist in the organization";
		}
		else if(!organization.findEventWithIdExists(eventId))
		{
			return "The mentioned event does not exist in the organization";
		}
		else if(!organization.didMemberLikeEvent(memberId,eventId))
		{
			return "The member never liked the event in the past to dislike it now!";
		}
		else
		{
			Event currentEvent = organization.decrementLikes(eventId);
			eventService.updateEvent(currentEvent);
			MemberActivity memberActivity = organization.deleteFromLikedEventsOfMember(memberId,eventId);
			memberActivityService.updateMemberActivity(memberActivity);
			return "Member with id: " + memberId + " sucessfully disliked the event with id: " + eventId;
		}
	}
	
	@PutMapping("/{memberId}/watch/{eventId}")
	public String watchEvent(@PathVariable(name="memberId") int memberId, @PathVariable(name="eventId") int eventId)
	{
		if(!organization.findMemberWithIdExists(memberId))
		{
			return "The member with given Id does not exist in the organization";
		}
		else if(!organization.findEventWithIdExists(eventId))
		{
			return "The mentioned event does not exist in the organization";
		}
		else if(organization.isMemberWatchingEvent(memberId,eventId))
		{
			return "The member is already watching the given event!";
		}
		else if(organization.getEventWithId(eventId).isExpired())
		{
			return "The event which is to be watched by the member is expired now";
		}
		else
		{
			Event currentEvent = organization.incrementWatches(eventId);
			eventService.updateEvent(currentEvent);
			MemberActivity memberActivity = organization.addToWatchedEventsOfMember(memberId,eventId);
			memberActivityService.updateMemberActivity(memberActivity);
			return "Member with id: " + memberId + " can sucessfully watch the event with id: " + eventId;
		}
	}
	
	@PutMapping("/{memberId}/unwatch/{eventId}")
	public String unWatchEvent(@PathVariable(name="memberId") int memberId, @PathVariable(name="eventId") int eventId)
	{
		if(!organization.findMemberWithIdExists(memberId))
		{
			return "The member with given Id does not exist in the organization";
		}
		else if(!organization.findEventWithIdExists(eventId))
		{
			return "The mentioned event does not exist in the organization";
		}
		else if(!organization.isMemberWatchingEvent(memberId,eventId))
		{
			return "The member never enrolled to watch the event in the past!";
		}
		else
		{
			Event currentEvent = organization.decrementWatches(eventId);
			eventService.updateEvent(currentEvent);
			MemberActivity memberActivity = organization.deleteFromWatchedEventsOfMember(memberId,eventId);
			memberActivityService.updateMemberActivity(memberActivity);
			return "Member with id: " + memberId + " cannot watch the event with id: " + eventId + " now ";
		}
	}
	
	@PutMapping("/{memberId}/participate/{eventId}")
	public String participateInEvent(@PathVariable(name="memberId") int memberId, @PathVariable(name="eventId") int eventId)
	{
		if(!organization.findMemberWithIdExists(memberId))
		{
			return "The member with given Id does not exist in the organization";
		}
		else if(!organization.findEventWithIdExists(eventId))
		{
			return "The mentioned event does not exist in the organization";
		}
		else if(organization.isMemberParticipatingInEvent(memberId,eventId))
		{
			return "The member has already enrolled for participating in the given event in past!";
		}
		else if(organization.getEventWithId(eventId).isExpired())
		{
			return "The event in which the member is intended to participate is expired now";
		}
		else
		{
			Event currentEvent = organization.incrementParticipants(eventId);
			eventService.updateEvent(currentEvent);
			MemberActivity memberActivity = organization.addToPartcipatingEventsOfMember(memberId,eventId);
			memberActivityService.updateMemberActivity(memberActivity);
			return "Member with id: " + memberId + " can participate in the event with id: " + eventId + " now ";
		}
	}
	
	@PutMapping("/{memberId}/unparticipate/{eventId}")
	public String withdrawParticipationFromEvent(@PathVariable(name="memberId") int memberId, @PathVariable(name="eventId") int eventId)
	{
		if(!organization.findMemberWithIdExists(memberId))
		{
			return "The member with given Id does not exist in the organization";
		}
		else if(!organization.findEventWithIdExists(eventId))
		{
			return "The mentioned event does not exist in the organization";
		}
		else if(!organization.isMemberParticipatingInEvent(memberId,eventId))
		{
			return "The member never enrolled to participate in the event in the past!";
		}
		else
		{
			Event currentEvent = organization.decrementParticipants(eventId);
			eventService.updateEvent(currentEvent);
			MemberActivity memberActivity = organization.deleteFromParticipatedEventsOfMember(memberId,eventId);
			memberActivityService.updateMemberActivity(memberActivity);
			return "Member with id: " + memberId + " cannot particpate the event with id: " + eventId + " now ";
		}
	}
	
	
	
	public  String deleteEvent(int eventId) {
		organization.deleteEventFromOrganization(organization.getEventWithId(eventId));
		return eventService.deleteEventById(eventId);
	}
}
