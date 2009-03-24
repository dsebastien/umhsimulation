package be.simulation;

import be.simulation.configuration.ConfigurationSimulationReseau;
import be.simulation.core.AbstractSimulation;

/**
 * Simulation d'un réseau.
 * 
 * @author Dubois Sebastien
 * @author Regnier Fréderic
 * @author Mernier Jean-François
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
		LOGGER.info("Démarrage de " + getConfiguration().getNom());
		// TODO implementer
	}
}
