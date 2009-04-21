package be.simulation.evenements;

import be.simulation.core.evenements.Evenement;
import be.simulation.entites.Hote;

/**
 * Evenement qui déclénche l'envoi d'un nouveau message par un hôte.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class HoteEnvoieMessageOriginal extends Evenement {
	private final Hote	hote;



	/**
	 * Instanciation de l'évènement HoteEnvoieMessageOriginal.
	 * 
	 * @param hote
	 *        l'hôte qui doit envoyer un message
	 * @param tempsPrevu
	 *        temps auquel cet évènement doit se produire
	 */
	public HoteEnvoieMessageOriginal(final Hote hote, final long tempsPrevu) {
		super(tempsPrevu);
		if (hote == null) {
			throw new IllegalArgumentException("L'hôte ne peut pas être null!");
		}
		if (hote.getNumeroHote() == 0) {
			throw new IllegalArgumentException("L'hôte doit avoir un numéro!");
		}
		if (hote.getAgent() == null) {
			throw new IllegalArgumentException(
					"L'hôte doit être connecté à un agent!");
		}
		this.hote = hote;
	}



	/**
	 * Récupérer l'hôte qui doit envoyer un nouveau message.
	 * 
	 * @return l'hôte qui doit envoyer un nouveau message.
	 */
	public Hote getHote() {
		return hote;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Fin de simulation au temps " + getTempsPrevu();
	}
}
