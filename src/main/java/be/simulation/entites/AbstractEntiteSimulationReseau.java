package be.simulation.entites;

import org.springframework.beans.factory.annotation.Autowired;
import be.simulation.SimulationReseau;
import be.simulation.core.entites.AbstractEntiteSimulation;

/**
 * Classe utilisée par toutes les entités de la simulation réseau.
 * 
 * @author Dubois Sebastien
 */
public abstract class AbstractEntiteSimulationReseau extends
		AbstractEntiteSimulation {
	/**
	 * La simulation dont l'entité fait partie.
	 */
	private SimulationReseau	simulation;

	/**
	 * Récupérer la simulation dont l'entité fait partie.
	 * 
	 * @return la simulation dont l'entité fait partie
	 */
	public SimulationReseau getSimulation() {
		return simulation;
	}



	/**
	 * Définir la simulation dont l'entité fait partie.
	 * 
	 * @param simulation
	 *        la simulation dont l'entité fait partie.
	 */
	@Autowired
	public void setSimulation(SimulationReseau simulation) {
		this.simulation = simulation;
	}
}
