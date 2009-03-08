package be.simulation.tp1.configuration;

import be.simulation.core.configuration.AbstractConfiguration;

/**
 * News stand simulation configuration.
 * 
 * @author Dubois Sebastien
 */
public class NewsDealerSimulationConfiguration extends AbstractConfiguration {
	private Integer	newsPapersPerDay;
	private Integer	daysToSimulate;



	/**
	 * How many days should be simulated.
	 * 
	 * @return the number of days to simulate
	 */
	public Integer getDaysToSimulate() {
		if (daysToSimulate == null) {
			daysToSimulate =
					Integer.parseInt(getConfiguration().getProperty(
							"days_to_simulate", "20"));
			
		}
		return daysToSimulate;
	}



	/**
	 * How many news papers are available each day.
	 * 
	 * @return the number of news papers available each day
	 */
	public Integer getNewsPapersPerDay() {
		if (newsPapersPerDay == null) {
			newsPapersPerDay =
					Integer.parseInt(getConfiguration().getProperty(
							"newspapers_per_day", "50"));
		}
		return newsPapersPerDay;
	}



	public void setDaysToSimulate(Integer daysToSimulate) {
		if (daysToSimulate != null) {
			this.daysToSimulate = daysToSimulate;
		}
	}



	public void setNewsPapersPerDay(Integer newsPapersPerDay) {
		if (newsPapersPerDay != null) {
			this.newsPapersPerDay = newsPapersPerDay;
		}
	}
}
