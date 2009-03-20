package be.simulation;

import be.simulation.configuration.NetworkSimulationConfiguration;
import be.simulation.core.AbstractSimulation;

/**
 * Network simulation
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-Francois
 */
public class NetworkSimulation extends
		AbstractSimulation<NetworkSimulationConfiguration> {
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
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void demarrer() {
		LOGGER.info("Démarrage de la simulation " + getConfiguration().getNom());
	}
}
