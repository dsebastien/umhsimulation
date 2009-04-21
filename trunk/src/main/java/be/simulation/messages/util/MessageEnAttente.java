package be.simulation.messages.util;

import be.simulation.messages.Message;

/**
 * Quand un message arrive chez un agent ou un hôte il est placé dans un buffer
 * et encapsulé dans cette structure qui permet de savoir à quel moment il est
 * arrivé dans le buffer. L'intérêt est de pouvoir ensuite calculer pour chaque
 * message le temps passé dans les buffers.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public final class MessageEnAttente {
	/**
	 * Le message en attente.
	 */
	private final Message	message;
	/**
	 * Le temps de simulation où le message a été mis en attente.
	 */
	private final long		tempsArrivee;



	/**
	 * Création d'un message en attente.
	 * 
	 * @param tempsArrivee
	 *        le temps de simulation auquel ce message a été mis en attente.
	 */
	public MessageEnAttente(final Message message, final long tempsArrivee) {
		if (message == null) {
			throw new IllegalArgumentException(
					"Le message qu'on met en attente ne peut pas être null!");
		}
		if (tempsArrivee < 0) {
			throw new IllegalArgumentException(
					"Le temps d'arrivée du message ne peut pas etre < 0");
		}
		this.message = message;
		this.tempsArrivee = tempsArrivee;
	}



	/**
	 * Récupérer le message en attente.
	 * 
	 * @return le message en attente.
	 */
	public Message getMessage() {
		return message;
	}



	/**
	 * Récupérer le temps de simulation auquel ce message a été mis en attente.
	 * 
	 * @return le temps de simulation auquel ce message a été mis en attente.
	 */
	public long getTempsArrivee() {
		return tempsArrivee;
	}
}
