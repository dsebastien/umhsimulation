package be.simulation.tp2;



import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import be.simulation.core.AbstractSimulation;
import be.simulation.tp2.configuration.ServiceSimulationConfiguration;
import be.simulation.tp2.model.Server;

/**
 * Simulation of services with customers (multiple servers).
 * 
 * @author Dubois Sebastien
 */
public class ServiceSimulation extends
AbstractSimulation<ServiceSimulationConfiguration> {
    private static final Logger LOG = Logger.getLogger(AbstractSimulation.class.getName());

    /**
     * The servers.
     */
    private List<Server> servers;


    @Override
    public void reset() {
        servers = new ArrayList<Server>();

        // create the servers
        LOG.info("Creating " + getConfig().getNumberOfServers() + " servers");
        for (long i = 0; i < getConfig().getNumberOfServers(); i++) {
            servers.add(new Server());
        }

        // very important if we reset the simulation.
        getFutureEventList().reset();

        // schedule the first event
		// FIXME implements this (use RandomUtils
		Long firstEventTime = getConfig().getMeanInterArrivalTime();


        //Event firstEvent = new Event();

        // futureEventList.scheduleEvent(evt);
    }



    @Override
    public void start(){
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
