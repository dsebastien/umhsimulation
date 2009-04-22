package be.simulation.evenements;

import be.simulation.core.evenements.Evenement;
import be.simulation.entites.Agent;
import be.simulation.messages.Message;

/**
 * Evenement déclenché quand un agent termine de traiter un message.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class AgentFinTraitementMessage extends Evenement {
	/**
	 * L'agent qui termine de traiter ce message.
	 */
	private final Agent agent;
	/**
	 * Récupérer l'agent qui termine de traiter ce message.
	 * @return l'agent qui termine de traiter ce message.
	 */
	public Agent getAgent() {
		return agent;
	}


	/**
	 * Le message qu'on termine de traiter.
	 */
	private final Message	message;

	/**
	 * Evènement de fin de traitement.
	 * 
	 * @param message
	 *        le message qu'on termine de traiter
	 * @param agent l'agent qui termine de traiter ce message
	 * @param tempsPrevu le temps de simulation auquel cet évènement doit se produire
	 **/
	public AgentFinTraitementMessage(final Message message, final Agent agent, final long tempsPrevu) {
		super(tempsPrevu);
		if (message == null) {
			throw new IllegalArgumentException(
					"Le message ne peut pas être null!");
		}
		if (agent == null) {
			throw new IllegalArgumentException("L'agent ne peut pas être null!");
		}
		this.message = message;
		this.agent = agent;
	}



	/**
	 * Récupérer le message qu'on termine de traiter.
	 * 
	 * @return le message qu'on termine de traiter
	 */
	public Message getMessage() {
		return message;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Agent "+agent.getNumero()+" termine de traiter message au temps " + getTempsPrevu();
	}
}
