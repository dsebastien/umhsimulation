package be.simulation.evenements;

import be.simulation.core.evenements.Evenement;
import be.simulation.messages.Message;

/**
 * Evenement déclenché quand un hôte reçoit un message.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class HoteRecoitMessage extends Evenement {
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
	public HoteRecoitMessage(final long tempsPrevu, final Message message) {
		super(tempsPrevu);
		if (message == null) {
			throw new IllegalArgumentException(
					"Le message ne peut pas être null!");
		}
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
		return "Hôte reçoit message au temps " + getTempsPrevu();
	}
}
