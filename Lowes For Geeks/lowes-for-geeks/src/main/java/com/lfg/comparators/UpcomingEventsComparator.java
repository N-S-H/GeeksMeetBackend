package com.lfg.comparators;

import java.util.Comparator;

import com.lfg.entities.Event;

public class UpcomingEventsComparator implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {
		return o1.getNumberOfViews()-o2.getNumberOfViews();
	}
   
}
