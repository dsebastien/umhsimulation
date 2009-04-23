package be.simulation.entites;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;

import be.simulation.SimulationReseau;
import be.simulation.core.entites.AbstractEntiteSimulation;
import be.simulation.messages.util.MessageEnAttente;

/**
 * Classe utilisée par toutes les entités de la simulation réseau.
 * 
 * @author Dubois Sebastien
 */
public abstract class AbstractEntiteSimulationReseau extends
		AbstractEntiteSimulation {
	/**
	 * Buffer de l'entité.
	 */
	private final Queue<MessageEnAttente> buffer = new LinkedList<MessageEnAttente>();

	/**
	 * La simulation dont l'entité fait partie.
	 */
	private SimulationReseau simulation;

	/**
	 * Récupérer le buffer.
	 * 
	 * @return le buffer
	 */
	public Queue<MessageEnAttente> getBuffer() {
		return buffer;
	}

	/**
	 * Récupérer la simulation dont l'entité fait partie.
	 * 
	 * @return la simulation dont l'entité fait partie
	 */
	protected SimulationReseau getSimulation() {
		return simulation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		buffer.clear();
	}

	/**
	 * Définir la simulation dont l'entité fait partie.
	 * 
	 * @param simulation
	 *            la simulation dont l'entité fait partie.
	 */
	@Autowired
	protected void setSimulation(final SimulationReseau simulation) {
		if (simulation == null) {
			throw new IllegalArgumentException(
					"La simulation ne peut pas être null!");
		}
		this.simulation = simulation;
	}
}
