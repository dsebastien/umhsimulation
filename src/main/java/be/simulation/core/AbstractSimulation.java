package be.simulation.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
	private EventList						futureEventList;
	private long							clock					= 0L;
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
		
		initialize();
	}
	
	
	
	public T getConfig() {
		return config;
	}
	
	

	public long getClock() {
		return clock;
	}



	public void setClock(long clock) {
		this.clock = clock;
	}



	public EventList getFutureEventList() {
		return futureEventList;
	}



	public void setFutureEventList(EventList futureEventList) {
		this.futureEventList = futureEventList;
	}



	/**
	 * Initializes (or reinitializes) the system (initial system state). Should
	 * put the system in a valid state before beginning the simulation.
	 */
	public abstract void initialize();



	/**
	 * Start the simulation.
	 */
	public abstract void start();



	/**
	 * Display the results of the simulation.
	 */
	public abstract void displayResults();
}
