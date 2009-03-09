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
    private Integer	numberOfServers;


    public void setNumberOfServers(Integer numberOfServers) {
    	if (numberOfServers != null) {
			this.numberOfServers = numberOfServers;
		}
	}



	/**
	 * How many servers can the system use.
	 * 
	 * @return the number of servers
	 */
	public int getNumberOfServers() {
        if(numberOfServers == null){
            numberOfServers =
					Integer.parseInt(getConfiguration().getProperty("servers",
							"1"));
        }
        return numberOfServers;
    }



}
