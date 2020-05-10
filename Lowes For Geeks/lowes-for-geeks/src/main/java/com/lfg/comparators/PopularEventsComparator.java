package com.lfg.comparators;

import java.util.Comparator;

import com.lfg.entities.Event;

public class PopularEventsComparator implements Comparator<Event>{

	@Override
	public int compare(Event o1, Event o2) {
		if(o1.getNumberOfLikes()!=o2.getNumberOfLikes())
		return o1.getNumberOfLikes() - o2.getNumberOfLikes();
		else return o1.getNumberOfViews() - o2.getNumberOfViews();
	}
   
}
