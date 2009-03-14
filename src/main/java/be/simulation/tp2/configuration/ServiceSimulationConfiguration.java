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

    /**
	 * Mean time between each customer's arrival.
	 */
    private Double	meanInterArrivalTime;
    
    /**
	 * How many customers should be served during the simulation.
	 */
    private Integer	customersToServe;
    
    

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


	/**
	 * Mean inter arrival time.
	 *
	 * @return the mean inter arrival time
	 */
	public double getMeanInterArrivalTime() {
		if (meanInterArrivalTime == null) {
			meanInterArrivalTime =
					Double.parseDouble(getConfiguration().getProperty(
							"mean_interarrival_time", "1"));
		}
		return meanInterArrivalTime;
	}
	
	/**
	 * Customers to serve.
	 * 
	 * @return the number of customers to serve
	 */
	public double getCustomersToServe() {
		if (customersToServe == null) {
			customersToServe =
					Integer.parseInt(getConfiguration().getProperty(
							"customers_to_serve", "10"));
		}
		return customersToServe;
	}
	
}
