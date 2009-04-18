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
	 * Message simple
	 */
	public MessageSimple(final Hote source, final Hote destination) {
		super(source, destination);
	}
}
