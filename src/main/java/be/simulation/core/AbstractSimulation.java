package be.simulation.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.apache.log4j.Logger;
import be.simulation.core.configuration.AbstractConfiguration;
import be.simulation.core.events.EventList;

/**
 * Generic simulation class (needed by any simulation).
 * 
 * @param <T>
 *        The configuration type
 * @author Dubois Sebastien
 */
public abstract class AbstractSimulation<T extends AbstractConfiguration> {
	private final Logger LOG = Logger.getLogger(AbstractSimulation.class
			.getName());
	
	private EventList		futureEventList;
	protected long clock = 0L;



	private T			config;



	/**
	 * Initialize the configuration and then initialize the simulation.
	 * 
	 * @param cfg
	 *        the configuration
	 */
	@SuppressWarnings("unchecked")
	public AbstractSimulation() {
		// a little fun with generics in order to load the configuration in a
		// generic manner
	    Type type = this.getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) type;
		try {
			config =
				((Class<T>) pt.getActualTypeArguments()[0]).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initializeSimulation();
	}
	/**
	 * Display the results of the simulation.
	 */
	public abstract void displayResults();



	/**
	 * Get the simulation configuration.
	 * 
	 * @return the configuration
	 */
	public T getConfig() {
		return config;
	}
	
	
	
	protected EventList getFutureEventList() {
		return futureEventList;
	}



	/**
	 * Initializes the simulation (first start).
	 */
	private void initializeSimulation() {
		LOG.info("Initializing the simulation...");
		reset();
		LOG.info("Simulation initialized");
	}



	/**
	 * Initializes (or reinitializes) the system (initial system state). Should
	 * put the system in a valid state before beginning the simulation.
	 */
	public abstract void reset();
	
	/**
	 * Execute the simulation.
	 */
	public abstract void start();
}
