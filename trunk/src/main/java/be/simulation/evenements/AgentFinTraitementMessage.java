package be.simulation.evenements;

import be.simulation.core.evenements.Evenement;
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
	 * Le message qu'on termine de traiter.
	 */
	private final Message	message;

	/**
	 * Evènement de fin de traitement.
	 * 
	 * @param message
	 *        le message qu'on termine de traiter
	 **/
	public AgentFinTraitementMessage(final long tempsPrevu,
			final Message message) {
		super(tempsPrevu);
		this.message = message;
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
		return "Agent fini de traiter message au temps " + getTempsPrevu();
	}
}
