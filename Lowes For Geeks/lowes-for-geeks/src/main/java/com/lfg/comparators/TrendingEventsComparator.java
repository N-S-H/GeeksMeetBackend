package com.lfg.comparators;

import java.util.Comparator;

import com.lfg.entities.Event;

public class TrendingEventsComparator implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {
		if(o1.getNumberOfLikes()!=o2.getNumberOfLikes())
			return o1.getNumberOfLikes() - o2.getNumberOfLikes();
		else if(o1.getNumberOfWatchers()!=o2.getNumberOfWatchers())
			return o2.getNumberOfWatchers()-o2.getNumberOfWatchers();
		else return o2.getNumberOfParticipants()-o2.getNumberOfParticipants();
	}

}
