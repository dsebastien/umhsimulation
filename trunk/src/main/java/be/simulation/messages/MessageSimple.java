package be.simulation.messages;

import be.simulation.entites.Hote;

/**
 * Un message simple.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class MessageSimple extends Message {
	/**
	 * le numéro identifiant de ce message.
	 */
	private final int	numeroMessage;
	/**
	 * temps de simulation au moment de l'émission de ce message.
	 */
	private final long	tempsEmission;



	/**
	 * Message simple
	 */
	public MessageSimple(final Hote source, final Hote destination,
			final int numeroMessage, final long tempsEmission) {
		super(source, destination);
		if (tempsEmission < 0) {
			throw new IllegalArgumentException(
					"Le temps d'émission ne peut pas etre < 0");
		}
		this.numeroMessage = numeroMessage;
		this.tempsEmission = tempsEmission;
	}



	/**
	 * Récupérer le numéro de ce message.
	 * 
	 * @return le numéro de ce message.
	 */
	public int getNumeroMessage() {
		return numeroMessage;
	}



	/**
	 * Récupérer le temps de simulation auquel ce message a été émis.
	 * 
	 * @return le temps de simulation auquel ce message a été émis.
	 */
	public long getTempsEmission() {
		return tempsEmission;
	}
}
