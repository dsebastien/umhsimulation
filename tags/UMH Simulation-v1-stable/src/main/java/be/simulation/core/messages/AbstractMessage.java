package be.simulation.core.messages;

import be.simulation.core.entites.AbstractEntiteSimulation;

/**
 * Message abstrait. Représente une communication entre deux entités.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 * @param <T>
 *        Type des entités qui communiquent
 */
public abstract class AbstractMessage<T extends AbstractEntiteSimulation> {
	/**
	 * Entité qui doit reçevoir le message.
	 */
	private final T	destination;
	/**
	 * Entité à l'origine du message.
	 */
	private final T	source;



	/**
	 * Création d'un message. Tout message a une source et une destination.
	 * 
	 * @param source
	 *        la source du message
	 * @param destination
	 *        la destination du message
	 * @throws IllegalArgumentException
	 *         si la source ou la destination est null
	 */
	public AbstractMessage(final T source, final T destination) {
		if (source == null) {
			throw new IllegalArgumentException(
					"La source ne peut pas être null");
		}
		if (destination == null) {
			throw new IllegalArgumentException(
					"La destination ne peut pas être null");
		}
		this.source = source;
		this.destination = destination;
	}



	/**
	 * Récupérer le destinataire du message.
	 * 
	 * @return le destinataire du message
	 */
	public T getDestination() {
		return destination;
	}



	/**
	 * Récupérer la source du message.
	 * 
	 * @return la source du message
	 */
	public T getSource() {
		return source;
	}
}
