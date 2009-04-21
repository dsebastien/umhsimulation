package be.simulation.messages;

import be.simulation.core.messages.AbstractMessage;
import be.simulation.entites.Hote;

/**
 * Un message entre hôtes.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public abstract class Message extends AbstractMessage<Hote> {
	/**
	 * le temps total passé par ce message dans des buffers.
	 */
	private long	tempsPasseDansBuffers;

	/**
	 * Message.
	 */
	public Message(final Hote source, final Hote destination) {
		super(source, destination);
	}
	
	
	/**
	 * Augmenter le temps passé par ce message dans des buffers
	 * 
	 * @param tempsSupplementaire
	 *        le temps à ajouter au total
	 */
	public void augmenterTempsPasseDansBuffers(long tempsSupplementaire) {
		if (tempsSupplementaire < 0) {
			throw new IllegalArgumentException(
					"Le temps a ajouter ne peut pas etre < 0!");
		}
		this.tempsPasseDansBuffers += tempsSupplementaire;
	}



	/**
	 * Récupérer le temps total passé par ce message dans des buffers.
	 * 
	 * @return le temps total passé par ce message dans des buffers.
	 */
	public long getTempsPasseDansBuffers() {
		return tempsPasseDansBuffers;
	}
}
