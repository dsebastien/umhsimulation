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
	 * @param source l'agent à la source de l'envoi des informations de routage
	 * @param destination l'agent destinataire des informations de routage
	 */
	public InfosRoutage(final Agent source, final Agent destination) {
		super(source, destination);
	}
	// FIXME v2.0: à définir
}
