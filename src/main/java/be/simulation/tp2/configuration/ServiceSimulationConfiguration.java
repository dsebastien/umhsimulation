package be.simulation.tp2.configuration;

import be.simulation.core.configuration.AbstractConfiguration;

/**
 * Simple class to give access to the configuration
 * @author Dubois Sebastien
 *
 */
public class ServiceSimulationConfiguration extends AbstractConfiguration {
    /**
	 * How many servers are available in the system.
	 */
    private Long numberOfServers;



	/**
	 * Loads the simulation configuration file.
	 */
    public ServiceSimulationConfiguration(){
    }

    public long getNumberOfServers(){
        if(numberOfServers == null){
            numberOfServers =
					Long.parseLong(getConfiguration().getProperty("servers",
							"1"));
        }
        return numberOfServers;
    }



}
