package be.simulation.core.events;

import java.util.LinkedHashMap;
import java.util.Map;
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
    /**
     * The Hash Table containing the events. The key is the time at which the
     * associated are planned. The events that should occur at the same time are
     * stored together in a {@link Queue} (FIFO).
     */
    private Map<Long, Queue<Event>> events = new LinkedHashMap<Long, Queue<Event>>();


    public void scheduleEvent(Event evt) {
    	LOG.info(evt);
        //FIXME implement
        // ajout trié de l'evenement
        // voir le temps auquel il doit se produire
        // et l'insérer au bon endroit
        // evt.getTime()
    }

    /**
     * Reset the event list.
     */
    public void reset(){
        events = new LinkedHashMap<Long, Queue<Event>>();
    }

    //TODO method to retrieve the imminent events

}
