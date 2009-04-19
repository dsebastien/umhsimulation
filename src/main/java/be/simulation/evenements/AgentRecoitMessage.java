package be.simulation.evenements;

import be.simulation.core.evenements.Evenement;
import be.simulation.messages.Message;

/**
 * Evenement déclenché quand un agent reçoit un message.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class AgentRecoitMessage extends Evenement {
	/**
	 * Le message reçu.
	 */
	private final Message	message;



	/**
	 * Evènement de réception.
	 * 
	 * @param message
	 *        le message reçu
	 **/
	public AgentRecoitMessage(final Double tempsPrevu,
			final Message message) {
		super(tempsPrevu);
		this.message = message;
	}



	/**
	 * Récupérer le message reçu.
	 * 
	 * @return le message reçu
	 */
	public Message getMessage() {
		return message;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Agent reçoit message au temps " + getTempsPrevu();
	}
}