package be.simulation.evenements;

import be.simulation.core.evenements.Evenement;
import be.simulation.entites.Hote;
import be.simulation.messages.Message;

/**
 * Evenement déclenché quand un hôte termine de traiter un message.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class HoteFinTraitementMessage extends Evenement {
	/**
	 * L'hôte qui termine de traiter un message.
	 */
	private final Hote		hote;
	/**
	 * Le message qu'on termine de traiter.
	 */
	private final Message	message;



	/**
	 * Evènement de fin de traitement.
	 * 
	 * @param message
	 *        le message qu'on termine de traiter
	 * @param hote
	 *        l'hôte qui termine de traiter ce message
	 * @param tempsPrevu
	 *        le temps de simulation auquel cet évènement doit se produire
	 **/
	public HoteFinTraitementMessage(final Message message, final Hote hote,
			final long tempsPrevu) {
		super(tempsPrevu);
		if (message == null) {
			throw new IllegalArgumentException(
					"Le message ne peut pas être null!");
		}
		if (hote == null) {
			throw new IllegalArgumentException("L'hôte ne peut pas être null!");
		}
		this.message = message;
		this.hote = hote;
	}



	/**
	 * Récupérer l'hôte qui termine de traiter ce message.
	 * 
	 * @return l'hôte qui termine de traiter ce message.
	 */
	public Hote getHote() {
		return hote;
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
		return "Hote " + hote.getNumero()
				+ " termine de traiter message au temps " + getTempsPrevu();
	}
}
