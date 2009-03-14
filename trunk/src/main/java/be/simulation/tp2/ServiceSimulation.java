package be.simulation.tp2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import be.simulation.core.AbstractSimulation;
import be.simulation.core.events.Event;
import be.simulation.core.events.EventType;
import be.simulation.core.utilities.RandomUtils;
import be.simulation.tp2.configuration.ServiceSimulationConfiguration;
import be.simulation.tp2.model.Server;

/**
 * Simulation of services with customers (multiple servers).
 * 
 * @author Dubois Sebastien
 */
public class ServiceSimulation extends
		AbstractSimulation<ServiceSimulationConfiguration> {
	private static final Logger	LOG	=
											Logger
													.getLogger(AbstractSimulation.class
															.getName());
	/**
	 * The servers.
	 */
	private List<Server>		servers;
	/**
	 * The pseudo-random number generator.
	 */
	private Random				prng;
	/**
	 * How many customers were already served.
	 */
	private int					servedCustomers;



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		resetBasicSimulation();
		
		servedCustomers = 0;
		servers = new ArrayList<Server>();
		prng = new Random();
		// create the servers
		LOG.info("Creating " + getConfig().getNumberOfServers() + " servers");
		for (long i = 0; i < getConfig().getNumberOfServers(); i++) {
			servers.add(new Server());
		}
		
		// schedule the first event
		Double firstEventTime =
				RandomUtils.exponential(prng, getConfig()
						.getMeanInterArrivalTime());
		Event firstEvent = new Event(firstEventTime, EventType.ARRIVAL);
		getFutureEventList().scheduleEvent(firstEvent);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() {
		LOG.info("Beginning the simulation");
		// continue the simulation until we served the configured number of
		// customers
		while (servedCustomers < getConfig().getCustomersToServe()) {
			// query the FEL for the imminent event
			Event imminent = getFutureEventList().getImminentEvent();
			if (imminent == null) {
				LOG
						.warn("No imminent event was found, stopping the simulation");
				break;
			}
			// we have the event, process it!
			setClock(imminent.getTime());
			
		}
		// process the event: setClock(evt.getTime());
		// to planify the finish event: event.getTime() + service time
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void displayResults() {
		// FIXME implement
		LOG.info("DISPLAY RESULTS NOT IMPLEMENTED YET");
	}
}
