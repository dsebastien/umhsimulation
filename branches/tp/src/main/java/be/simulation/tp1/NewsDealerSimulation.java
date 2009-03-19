package be.simulation.tp1;

import org.apache.log4j.Logger;
import be.simulation.core.AbstractSimulation;
import be.simulation.tp1.configuration.NewsDealerSimulationConfiguration;
import be.simulation.tp1.model.NewsDealer;

/**
 * News Dealer simulation (TP1).
 * 
 * @author Dubois Sebastien
 * 
 */
public class NewsDealerSimulation extends AbstractSimulation<NewsDealerSimulationConfiguration> {
    private final Logger LOG = Logger.getLogger(NewsDealerSimulation.class.getName());

    private NewsDealer newsDealer;

    private long currentSimulationDay;

    /**
	 * {@inheritDoc}
	 */
    @Override
    public void displayResults() {
        if (newsDealer == null) {
            LOG.info("No results to display");
        } else {
            LOG.info("AVG Profit : " + newsDealer.getAVGProfit());
            LOG.info("AVG Asked : " + newsDealer.getAVGNewspaperDemandPerDay());
        }
    }

    /**
	 * {@inheritDoc}
	 */
    @Override
    public void reset() {
        newsDealer = new NewsDealer(getConfig().getNewsPapersPerDay());
        currentSimulationDay = 1;
    }

    /**
	 * {@inheritDoc}
	 */
    @Override
    public void start() {
        LOG.info("Simulation in progress...");
        for (; currentSimulationDay <= getConfig().getDaysToSimulate(); currentSimulationDay++) {
            newsDealer.runOneDay();
        }
        LOG.info("Simulation completed");
    }

}
