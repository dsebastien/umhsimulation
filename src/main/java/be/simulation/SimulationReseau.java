package be.simulation;

import be.simulation.core.AbstractSimulation;
import be.simulation.entites.Agent;

/**
 * Simulation d'un réseau.
 * 
 * @author Dubois Sebastien
 * @author Regnier Fréderic
 * @author Mernier Jean-François
 */
public class SimulationReseau extends
		AbstractSimulation {

	// Les agents de la simulation (fixes, basés sur la figure 2 de l'énoncé)
	private Agent	agent1000;
	private Agent	agent2000;
	private Agent	agent3000;
	private Agent	agent4000;
	private Agent	agent5000;
	private Agent	agent6000;
	private Agent	agent7000;



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afficherResultats() {
		// TODO ici on doit calculer les résultats finaux et les afficher)
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		resetBasicSimulation(); // temps, FEL
		// recréation des agents
		// TODO dans un premier temps on utilise la même taille de buffers pour
		// tous. ensuite on pourra fixer des valeurs différentes pour chacun
		agent1000 =
				new Agent(1000, getConfiguration()
						.getConfigurationSimulationReseau()
						.getTailleBuffersAgents());
		agent2000 =
				new Agent(2000, getConfiguration()
						.getConfigurationSimulationReseau()
						.getTailleBuffersAgents());
		agent3000 =
				new Agent(3000, getConfiguration()
						.getConfigurationSimulationReseau()
						.getTailleBuffersAgents());
		agent4000 =
				new Agent(4000, getConfiguration()
						.getConfigurationSimulationReseau()
						.getTailleBuffersAgents());
		agent5000 =
				new Agent(5000, getConfiguration()
						.getConfigurationSimulationReseau()
						.getTailleBuffersAgents());
		agent6000 =
				new Agent(6000, getConfiguration()
						.getConfigurationSimulationReseau()
						.getTailleBuffersAgents());
		agent7000 =
				new Agent(7000, getConfiguration()
						.getConfigurationSimulationReseau()
						.getTailleBuffersAgents());
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void demarrer() {
		LOGGER.info("Démarrage de la simulation ("
				+ getConfiguration().getConfigurationSimulationReseau()
						.getNom() + ")");
		// TODO implementer
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getConfiguration().getConfigurationSimulationReseau().getNom();
	}

}
