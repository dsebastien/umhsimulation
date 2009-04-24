package be.simulation.evenements;

import be.simulation.core.evenements.Evenement;
import be.simulation.entites.Hote;
import be.simulation.messages.Message;

/**
 * Evenement déclenché quand un hôte n'a pas reçu un accusé de réception à temps.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class HoteTimeoutReceptionAccuse extends Evenement {
	/**
	 * L'hôte qui n'a pas reçu d'accusé de réception pour ce message.
	 */
	private final Hote		hote;
	/**
	 * Le message correspondant à ce timeout.
	 */
	private final Message	message;



	/**
	 * Evènement de timeout.
	 * 
	 * @param message
	 *        le message correspondant à ce timeout.
	 * @param hote l'hôte qui n'a pas reçu d'accusé de réception pour ce message
	 * @param tempsPrevu le temps de simulation auquel cet évènement doit se produire
	 **/
	public HoteTimeoutReceptionAccuse(final Message message, final Hote hote,
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
	 * Récupérer l'hôte qui n'a pas reçu d'accusé de réception pour ce message.
	 * 
	 * @return l'hôte qui n'a pas reçu d'accusé de réception pour ce message.
	 */
	public Hote getHote() {
		return hote;
	}



	/**
	 * Récupérer le message correspondant à ce timeout.
	 * 
	 * @return le message correspondant à ce timeout
	 */
	public Message getMessage() {
		return message;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Hôte "+hote.getNumero()+" n'a pas reçu d'accusé de réception à temps pour un message au temps " + getTempsPrevu();
	}
}
