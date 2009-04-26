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
	 * temps de simulation au moment de l'émission de ce message.
	 */
	// FIXME Déplacer dans la super classe! Utile pour les accusés aussi!
	// et ajouter la mise à jour de l'info (ajout au constructeur + checks)
	private final long	tempsEmission;
	/**
	 * le temps total passé par ce message dans des buffers.
	 */
	private long		tempsPasseDansBuffers;
	/**
	 * le temps où le message a été reçu par son destinataire final.
	 */
	private long		tempsReception;



	/**
	 * Message.
	 */
	public Message(final Hote source, final Hote destination,
			final long tempsEmission) {
		super(source, destination);
		if (tempsEmission < 0) {
			throw new IllegalArgumentException(
					"Le temps d'émission ne peut pas etre < 0");
		}
		this.tempsEmission = tempsEmission;
	}



	/**
	 * Augmenter le temps passé par ce message dans des buffers
	 * 
	 * @param tempsSupplementaire
	 *        le temps à ajouter au total
	 */
	public void augmenterTempsPasseDansBuffers(final long tempsSupplementaire) {
		if (tempsSupplementaire < 0) {
			throw new IllegalArgumentException(
					"Le temps a ajouter ne peut pas etre < 0!");
		}
		this.tempsPasseDansBuffers += tempsSupplementaire;
	}



	/**
	 * Récupérer le temps de simulation auquel ce message a été émis.
	 * 
	 * @return le temps de simulation auquel ce message a été émis.
	 */
	public long getTempsEmission() {
		return tempsEmission;
	}



	/**
	 * Récupérer le temps total passé par ce message dans des buffers.
	 * 
	 * @return le temps total passé par ce message dans des buffers.
	 */
	public long getTempsPasseDansBuffers() {
		return tempsPasseDansBuffers;
	}



	/**
	 * Récupérer le temps de réception de ce message.
	 * 
	 * @return le temps de réception de ce message
	 */
	public long getTempsReception() {
		return tempsReception;
	}



	/**
	 * Définir le temps de réception de ce message.
	 * 
	 * @param tempsReception
	 *        le temps de réception de ce message
	 */
	public void setTempsReception(final long tempsReception) {
		this.tempsReception = tempsReception;
	}



	/**
	 * {@inheritDoc}
	 */
	public abstract String toString();
}
