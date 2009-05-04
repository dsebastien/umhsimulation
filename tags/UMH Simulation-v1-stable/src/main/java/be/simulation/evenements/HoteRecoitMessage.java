package be.simulation.evenements;

import be.simulation.core.evenements.Evenement;
import be.simulation.entites.Hote;
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
	 * L'hôte qui reçoit ce message.
	 */
	private final Hote		hote;
	/**
	 * Le message reçu.
	 */
	private final Message	message;



	/**
	 * Evènement de réception.
	 * 
	 * @param message
	 *        le message reçu
	 * @param hote l'hôte qui reçoit ce message
	 * @param tempsPrevu le temps de simulation auquel cet évènement doit se produire
	 **/
	public HoteRecoitMessage(final Message message, final Hote hote,
			final long tempsPrevu) {
		super(tempsPrevu);
		if (message == null) {
			throw new IllegalArgumentException(
					"Le message ne peut pas être null!");
		}
		if (hote == null) {
			throw new IllegalArgumentException("L'hôte ne peut pas être null!");
		}
		if (hote.getNumero() == 0) {
			throw new IllegalArgumentException("L'hôte doit avoir un numéro!");
		}
		if (hote.getAgent() == null) {
			throw new IllegalArgumentException(
					"L'hôte doit être connecté à un agent!");
		}
		this.message = message;
		this.hote = hote;
	}



	/**
	 * Récupérer l'hôte qui reçoit ce message.
	 * 
	 * @return l'hôte qui reçoit ce message
	 */
	public Hote getHote() {
		return hote;
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
		return "Hôte "+hote.getNumero()+" reçoit message au temps " + getTempsPrevu();
	}
}
