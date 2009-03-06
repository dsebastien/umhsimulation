package be.simulation.tp1.configuration;

import be.simulation.core.configuration.AbstractConfiguration;

/**
 * News stand simulation configuration.
 * 
 * @author Dubois Sebastien
 */
public class NewsStandSimulationConfiguration extends AbstractConfiguration {
    private static final String CONFIGURATION_FILENAME = "simulation.properties";
    
	/**
	 * How many news papers are available each day.
	 */
	private Long				newsPapersPerDay;


	/**
	 * Loads the simulation configuration file.
	 */
    public NewsStandSimulationConfiguration(){
    	super(CONFIGURATION_FILENAME);
    }



	/**
	 * How many news papers are available each day.
	 * 
	 * @return the number of news papers available each day
	 */
	public Long getNewsPapersPerDay() {
		if (newsPapersPerDay == null) {
			newsPapersPerDay =
					Long.parseLong(getConfiguration().getProperty(
							"newspapers_per_day", "50"));
		}
		return newsPapersPerDay;
	}
    

}
