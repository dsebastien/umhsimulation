package be.simulation.tp2.configuration;

import be.simulation.core.configuration.AbstractConfiguration;

/**
 * Service simulation configuration holder.
 * 
 * @author Dubois Sebastien
 */
public class ServiceSimulationConfiguration extends AbstractConfiguration {
    /**
	 * How many servers are available in the system.
	 */
    private Long numberOfServers;


    public void setNumberOfServers(Long numberOfServers) {
    	if (numberOfServers != null) {
			this.numberOfServers = numberOfServers;
		}
	}



	/**
	 * How many servers can the system use.
	 * 
	 * @return the number of servers
	 */
	public long getNumberOfServers() {
        if(numberOfServers == null){
            numberOfServers =
					Long.parseLong(getConfiguration().getProperty("servers",
							"1"));
        }
        return numberOfServers;
    }



}
