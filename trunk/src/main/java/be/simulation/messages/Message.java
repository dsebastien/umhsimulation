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
	 * Message.
	 */
	public Message(final Hote source, final Hote destination) {
		super(source, destination);
	}
}
