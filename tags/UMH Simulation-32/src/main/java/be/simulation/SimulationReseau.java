package be.simulation;

import be.simulation.configuration.ConfigurationSimulationReseau;
import be.simulation.core.AbstractSimulation;

/**
 * Network simulation
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-Francois
 */
public class SimulationReseau extends
		AbstractSimulation<ConfigurationSimulationReseau> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afficherResultats() {
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		resetBasicSimulation(); // temps, FEL
		
		// TODO implementer:
		// recréer les agents, ...
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void demarrer() {
		LOGGER.info("Démarrage de la simulation " + getConfiguration().getNom());
		
		// TODO implementer
	}
}
