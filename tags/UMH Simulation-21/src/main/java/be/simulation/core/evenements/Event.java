package be.simulation.core.evenements;

// FIXME réfléchir à la gestion des evenements. Envoyer mail avec questions à
// l'assistant

/**
 * An event (notice), which can be stored in an {@link EventList}.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-Francois
 */
public class Event {
	// FIXME ask the teacher/assistant what his advice is concerning time
	// tracking: Long or Double?
	// With long, we have simple values but it's more likely that multiple
	// events will be scheduled to occur at the same time
	/**
	 * When this event should occur.
	 */
	private Double		time;
	/**
	 * The type of the event.
	 */
	private EventType	type;



	public Event(Double eventTime, EventType eventType) {
		this.time = eventTime;
		this.type = eventType;
	}



	public Double getTime() {
		return time;
	}



	public void setTime(Double eventTime) {
		this.time = eventTime;
	}



	public EventType getType() {
		return type;
	}



	public void setType(EventType eventType) {
		this.type = eventType;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Time: " + time + " - Type: " + type.toString();
	}
}
