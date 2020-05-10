package com.lfg.entities;


import java.time.LocalDateTime;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

enum EventType {
	Organization,
	Team,
	Private;
}

enum RecurringFrequency {
	Not_Recurring,
	Day,
	Month,
	Year;
}

@Document(collection = "events")
public class Event {
	
	
	@Id
	private int eventId;
	//0 for Organization, 1 for Team, 2 for Private events
	private EventType eventType;
	private String name;
	private String description;
	private String location;
	private boolean recurring;
	
	//ISO -LOCAL_DATE_TIME FORMAT : YYYY-MM-DDTHH:MM:SS
	private String startDate; 
	private String endDate;
	
	
	/*----No need to input the following fields, the details will be updated automatically----*/
	private Member createdBy = new Member();
	private int numberOfLikes = 0;
	private int numberOfWatchers = 0;
	private int numberOfViews = 0;
	private int numberOfParticipants = 0;
	private RecurringFrequency recurringFrequency = RecurringFrequency.Not_Recurring;
	private int TeamIdForTeamEvent = 0;
	private String createdDate;
	private boolean expired=false;
	
	
	
	
	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public int getTeamIdForTeamEvent() {
		return TeamIdForTeamEvent;
	}

	public void setTeamIdForTeamEvent(int teamIdForTeamEvent) {
		TeamIdForTeamEvent = teamIdForTeamEvent;
	}
	
	public void checkRecurrence()
	{
		if(this.isRecurring())
		{
		LocalDateTime eventStartDate = this.getParsedStartDate();
	    LocalDateTime eventEndDate = this.getParsedEndDate();
		
	    if((eventStartDate.getDayOfYear() == eventEndDate.getDayOfYear()) && (eventStartDate.getYear()==eventEndDate.getYear()))
	    {
	    	this.setRecurringFrequency(RecurringFrequency.Day);
	    }
	    else if((eventStartDate.getMonthValue() == eventEndDate.getMonthValue()) && (eventStartDate.getYear() == eventEndDate.getYear()))
	    {
	    	this.setRecurringFrequency(RecurringFrequency.Month);
	    }
	    else if(eventStartDate.getYear() == eventEndDate.getYear())
	    {
	    	this.setRecurringFrequency(RecurringFrequency.Year);
	    }
	    else 
	    {
	    	this.setRecurring(false);
	    	this.setRecurringFrequency(RecurringFrequency.Not_Recurring);
	    }
	    
		}
	}
	
	public LocalDateTime getParsedStartDate()
	{
		LocalDateTime eventStartDate = LocalDateTime.parse(this.startDate);
		return eventStartDate;
	}
	
	public LocalDateTime getParsedEndDate()
	{
		 LocalDateTime eventEndDate = LocalDateTime.parse(this.endDate);
		 return eventEndDate;
	}
	
	public LocalDateTime getParsedCreatedDate()
	{
		 LocalDateTime eventCreatedDate = LocalDateTime.parse(this.createdDate);
		 return eventCreatedDate;
	}
	

	public int getEventId() {
		return eventId;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Member getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Member createdBy) {
		this.createdBy = createdBy;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public int getNumberOfLikes() {
		return numberOfLikes;
	}
	public void setNumberOfLikes(int numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}
	public int getNumberOfWatchers() {
		return numberOfWatchers;
	}
	public void setNumberOfWatchers(int numberOfWatchers) {
		this.numberOfWatchers = numberOfWatchers;
	}
	public int getNumberOfViews() {
		return numberOfViews;
	}
	public void setNumberOfViews(int numberOfViews) {
		this.numberOfViews = numberOfViews;
	}
	public int getNumberOfParticipants() {
		return numberOfParticipants;
	}
	public void setNumberOfParticipants(int numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}
	public boolean isRecurring() {
		return recurring;
	}
	public void setRecurring(boolean recurring) {
		this.recurring = recurring;
	}
	public RecurringFrequency getRecurringFrequency() {
		return recurringFrequency;
	}
	public void setRecurringFrequency(RecurringFrequency recurringFrequency) {
		this.recurringFrequency = recurringFrequency;
	}


	public boolean isOrganizationEvent() {
		return this.eventType == EventType.Organization;
	}


	public boolean isTeamEvent() {
		return this.eventType == EventType.Team;
	}


	public boolean isPrivateEvent() {
		return this.eventType == EventType.Private;
	}
	
}
