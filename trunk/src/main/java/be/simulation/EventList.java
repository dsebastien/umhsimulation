package be.simulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;


public class EventList {
	//TODO which is the best data structure?
	private Map<Long, List<Event>> events = new LinkedHashMap<Long, List<Event>>(); //TreeMap<Long,Event>();
	
	public void scheduleEvent(Event evt){
		
		

		// ajout trié de l'evenement
		// voir le temps auquel il doit se produire
		// et l'insérer au bon endroit
		//evt.getTime()
	}
	
	
}
