package be.simulation.core.events;

/**
 * An event (notice), which can be stored in an {@link EventList}.
 *
 * @author Dubois Sebastien
 */
public class Event implements Comparable<Event>{
	// FIXME ask whether the time can be a long or if it better be a double (so
	// that no two events are likely to occur at the exact same time
    /**
     * When this event should occur.
     */
    private long time;

    /**
     * The type of the event.
     */
    private EventType	type;

    public Event(Long eventTime, EventType eventType) {
        this.time = eventTime;
        this.type = eventType;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long eventTime) {
		this.time = eventTime;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType eventType) {
        this.type = eventType;
    }



    /**
     * Compare an event with another. The result only depends on the time at
     * which the events are planned to occur.
     */
    public int compareTo(Event other) {
        return Long.valueOf(getTime()).compareTo(Long.valueOf(other.getTime()));
    }


    /**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Time: " + time + " - Type: " + type.toString();
	}
}
