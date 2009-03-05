package be.simulation;


public class Event {
	private long time;
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
}
