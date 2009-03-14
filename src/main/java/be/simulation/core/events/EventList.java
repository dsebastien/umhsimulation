package be.simulation.core.events;

import java.util.LinkedList;
import java.util.Queue;
import org.apache.log4j.Logger;

/**
 * An event list manages a list of events.
 * It can:<br />
 * <ul>
 *      <li>schedule events (put them at an appropriate position in the list depending on its planified time</li>
 *      <li>give a list of the next planned events</li>
 * </ul>
 * @author Dubois Sebastien
 *
 */
public class EventList {
	private static final Logger		LOG		=
													Logger
															.getLogger(EventList.class
																	.getName());

	private final Queue<Event>	eventsQueue	= new LinkedList<Event>();

    /**
	 * Schedule an event (put the event in the event list at the correct
	 * position).
	 * 
	 * @param evt
	 *        the event
	 */
    public void scheduleEvent(Event evt) {
    	LOG.info("Scheduling event: " + evt.toString());
    	eventsQueue.add(evt);
    }
    
    /**
	 * Get the imminent event if it exists, null otherwise.
	 * 
	 * @return the imminent event or null it there isn't any
	 */
	public Event getImminentEvent() {
		return eventsQueue.poll();
	}

    /**
     * Reset the event list.
     */
    public void reset(){
        eventsQueue.clear();
    }

    //TODO method to retrieve the imminent events

}
