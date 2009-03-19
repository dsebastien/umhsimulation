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
 *            The configuration type
 * @author Dubois Sebastien
 */
public abstract class AbstractSimulation<T extends AbstractConfiguration> {
    private final Logger LOG = Logger.getLogger(AbstractSimulation.class.getName());

    /**
	 * The FEL.
	 */
    private final EventList futureEventList;
    
    /**
	 * The simulation clock.
	 */
    private Double			clock;



	/**
	 * Get the simulation clock.
	 * 
	 * @return the clock
	 */
	protected Double getClock() {
		return clock;
	}
	
	/**
	 * Set the clock to the specified time (> 0).
	 * 
	 * @param time
	 *        the time to set the clock to
	 */
	protected void setClock(Double time) {
		if (time == null || time < 0) {
			throw new IllegalArgumentException(
					"The time to set the clock to has to be > 0");
		}
		clock = time;
	}
	
	/**
	 * Reset the basic simulation information (FEL, clock, ...).
	 */
	protected void resetBasicSimulation() {
		resetClock();
		futureEventList.reset();
	}



	/**
	 * Reset the clock (0).
	 */
	protected void resetClock() {
		clock = Double.valueOf(0);
	}
	
	/**
	 * Add the given time to the clock.
	 * 
	 * @param time
	 *        the time to add
	 * @throws IllegalArgumentException
	 *         if the given time is null or negative (not allowed!)
	 */
	protected void addToClock(Double time) {
		if (time == null) {
			throw new IllegalArgumentException(
					"The time to add to the clock cannot be null!");
		} else {
			if (time < 0) {
				throw new IllegalArgumentException(
						"The time to add to the clock cannot be negative!");
			}
		}
		clock = Double.valueOf(clock + time);
	}
	
	
	/**
	 * The simulation configuration.
	 */
	private T	config;

    /**
     * Initialize the configuration and then initialize the simulation.
     * 
     */
    @SuppressWarnings("unchecked")
    public AbstractSimulation() {
        // load the configuration in a generic manner
        Type type = this.getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) type;
        try {
            config = ((Class<T>) pt.getActualTypeArguments()[0]).newInstance();
        } catch (InstantiationException e) {
            LOG.fatal(e);
            throw new Error(e);
        } catch (IllegalAccessException e) {
            LOG.fatal(e);
            throw new Error(e);
        }
                
        // instanciate the future event list
        futureEventList = new EventList();
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
     * Initialize (or reinitialize) the system (initial system state). Puts the
     * system in a valid state.
     */
    public abstract void reset();

    /**
     * Execute the simulation.
     */
    public abstract void start();
}
