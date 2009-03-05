package be.simulation;

import java.util.ArrayList;
import java.util.List;


public class Simulation {
	private static final int NUMBER_OF_SERVERS = 100;
	
	private long clock = 0L;
	
	private List<Serveur> servers = new ArrayList<Serveur>();
	
	private EventList futureEventList;
	
	
	
	
	public static void main(String[] args) {
		new Simulation();
	}
	
	
	public Simulation(){
		for(int i = 0; i< NUMBER_OF_SERVERS; i++){
			servers.add(new Serveur());
		}
		
		// generer events (initialization)
		//futureEventList.scheduleEvent(evt);
		
		// init simulation.
		
	}
	
	
}
