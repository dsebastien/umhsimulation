package be.simulation.tp1;

import org.apache.log4j.Logger;
import be.simulation.core.AbstractSimulation;
import be.simulation.tp1.configuration.NewsDealerSimulationConfiguration;
import be.simulation.tp1.model.NewsDealer;


public class NewsDealerSimulation extends
		AbstractSimulation<NewsDealerSimulationConfiguration> {
	private final Logger LOG = Logger.getLogger(NewsDealerSimulation.class
			.getName());

	private NewsDealer	newsDealer;
	private long			currentSimulationDay;
	
	@Override
	public void displayResults() {
		if (newsDealer == null) {
			LOG.info("No results to display");
		} else {
			// TODO implement
			// System.out.println();
			// System.out.println("AVG Profit : " + dealer.getAVGProfit());
			// System.out.println("AVG Asked : " + dealer.getAVGAskedNews());
			//   
		}
	}



	@Override
	public void reset() {
		clock = 0L;
		newsDealer = new NewsDealer(getConfig().getNewsPapersPerDay());
		currentSimulationDay = 1;
	}



	@Override
	public void start() {
		for (; currentSimulationDay <= getConfig().getDaysToSimulate(); currentSimulationDay++) {
			LOG.info("Day " + currentSimulationDay);
			// TODO do this:
			// generate type of day
			// depending on the type, generate the number of bought newspapers
			// for that day
			// call the news dealer "run one day" and give him the bought news
			// papers number
		}
	}
	
     
	

}
