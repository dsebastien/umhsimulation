package be.simulation.messages.routage;

import be.simulation.core.messages.AbstractMessage;
import be.simulation.entites.Agent;

/**
 * Informations de routage échangées par les agents.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class InfosRoutage extends AbstractMessage<Agent> {
	/**
	 * Informations de routage.
	 */
	public InfosRoutage(final Agent source, final Agent destination) {
		super(source, destination);
	}
	// FIXME à définir
}
