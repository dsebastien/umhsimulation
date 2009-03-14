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



	@Override
	public void reset() {
		servers = new ArrayList<Server>();
		prng = new Random();
		// create the servers
		LOG.info("Creating " + getConfig().getNumberOfServers() + " servers");
		for (long i = 0; i < getConfig().getNumberOfServers(); i++) {
			servers.add(new Server());
		}
		// very important if we reset the simulation.
		getFutureEventList().reset();
		// schedule the first event
		// FIXME check with teacher/assistant if we can keep track of the time
		// with a long value (which means that the likelihood of having multiple
		// events at the same time is higher) even though the FEL will give us
		// these events in a FIFO order.
		Long firstEventTime =
				Math.round(RandomUtils.exponential(prng, getConfig()
						.getMeanInterArrivalTime()));
		Event firstEvent = new Event(firstEventTime, EventType.ARRIVAL);
		getFutureEventList().scheduleEvent(firstEvent);
	}



	@Override
	public void start() {
		LOG.info("Beginning the simulation");
		// query the FEL for the imminent events
		// priority queue for ? and getMin
		// to process an event: setClock(evt.getTime());
		// to planify the finish event: event.getTime() + service time
	}



	@Override
	public void displayResults() {
		// FIXME implement
		LOG.info("NOT IMPLEMENTED YET");
	}
}
