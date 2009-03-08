package be.simulation.tp2;



import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import be.simulation.core.AbstractSimulation;
import be.simulation.tp2.configuration.ServiceSimulationConfiguration;
import be.simulation.tp2.model.Server;

/**
 * Simulation of services with customers (multiple servers)
 * @author Dubois Sebastien
 *
 */
public class ServiceSimulation extends
		AbstractSimulation<ServiceSimulationConfiguration> {
    private static final Logger LOG = Logger.getLogger(AbstractSimulation.class.getName());

    /**
	 * The servers.
	 */
    private List<Server> servers;

    //private Random random;

    @Override
    public void reset() {
        servers = new ArrayList<Server>();

        // create the servers
		LOG.info("Creating " + getConfig().getNumberOfServers() + " servers");
		for (long i = 0; i < getConfig().getNumberOfServers(); i++) {
            servers.add(new Server());
        }
    }



    @Override
    public void start(){
        LOG.info("Beginning the simulation");
        // schedule the first events

        // futureEventList.scheduleEvent(evt);

        // priority queue for ? and getMin
        // to process an event: setClock(evt.getTime());
        // to planify the finish event: event.getTime() + service time
    }

    @Override
    public void displayResults() {
        // FIXME implement

    }
    
    public static void main(String[] args) {
		LOG.info("Loading the simulation");
		ServiceSimulation s = new ServiceSimulation();
	}
}
