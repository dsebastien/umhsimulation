package be.simulation.tp1.configuration;

import be.simulation.core.configuration.AbstractConfiguration;

/**
 * News stand simulation configuration.
 * 
 * @author Dubois Sebastien
 */
public class NewsDealerSimulationConfiguration extends AbstractConfiguration {
	private Long	newsPapersPerDay;
	private Long	daysToSimulate;



	/**
	 * How many days should be simulated.
	 * 
	 * @return the number of days to simulate
	 */
	public Long getDaysToSimulate() {
		if (daysToSimulate == null) {
			daysToSimulate =
					Long.parseLong(getConfiguration().getProperty(
							"days_to_simulate", "20"));
		}
		return daysToSimulate;
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



	public void setDaysToSimulate(Long daysToSimulate) {
		if (daysToSimulate != null) {
			this.daysToSimulate = daysToSimulate;
		}
	}



	public void setNewsPapersPerDay(Long newsPapersPerDay) {
		if (newsPapersPerDay != null) {
			this.newsPapersPerDay = newsPapersPerDay;
		}
	}
}
