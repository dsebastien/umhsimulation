package be.simulation.entites;

import java.util.LinkedList;
import java.util.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import be.simulation.SimulationReseau;
import be.simulation.core.entites.AbstractEntiteSimulation;
import be.simulation.messages.utilitaires.MessageEnAttente;

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
	private final Queue<MessageEnAttente>	buffer	=
															new LinkedList<MessageEnAttente>();
	/**
	 * Numero identifiant de l'entité.
	 */
	private long							numero;
	/**
	 * La simulation dont l'entité fait partie.
	 */
	private SimulationReseau				simulation;



	/**
	 * Récupérer le buffer.
	 * 
	 * @return le buffer
	 */
	public Queue<MessageEnAttente> getBuffer() {
		return buffer;
	}



	/**
	 * Récupérer le numero de l'entité.
	 * 
	 * @return le numéro de l'entité
	 */
	public long getNumero() {
		return numero;
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
	 * Définir le numéro de cette entité
	 * 
	 * @param numeroHote
	 *        le numéro de cette entité
	 * @throw IllegalArgumentException si le numéro d'entité fourni est <= 0
	 */
	public void setNumero(final long numeroHote) {
		if (numeroHote <= 0) {
			throw new IllegalArgumentException(
					"Le numero d'entité fourni est invalide (il doit etre > 0)");
		}
		this.numero = numeroHote;
	}



	/**
	 * Définir la simulation dont l'entité fait partie.
	 * 
	 * @param simulation
	 *        la simulation dont l'entité fait partie.
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
