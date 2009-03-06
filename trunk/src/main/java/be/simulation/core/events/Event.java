package be.simulation.core.events;

/**
 * An event (notice), which can be stored in an {@link EventList}.
 * 
 * @author Dubois Sebastien
 */
public class Event implements Comparable<Event>{
	/**
	 * When this event should occur.
	 */
    private long time;
    
    /**
	 * The type of the event.
	 */
    private EventType eventType;

    public Event(long time, EventType eventType){
        this.time = time;
        this.eventType = eventType;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }



	/**
	 * Compare an event with another. The result only depends on the time at
	 * which the events are planned to occur.
	 */
    public int compareTo(Event other) {
        return Long.valueOf(getTime()).compareTo(Long.valueOf(other.getTime()));
    }



}
