package be.simulation.messages.routage;

import java.util.List;
import java.util.Map;

import be.simulation.core.messages.AbstractMessage;
import be.simulation.entites.Agent;
import be.simulation.routage.Route;

/**
 * Informations de routage échangées par les agents.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class InfosRoutage extends AbstractMessage<Agent> {
	/**
	 * Un distance vector.
	 */
	private final Map<Agent,List<Route>> distanceVector;

	/**
	 * Informations de routage.
	 * 
	 * @param source
	 *            l'agent à la source de l'envoi des informations de routage
	 * @param destination
	 *            l'agent destinataire des informations de routage
	 * @param distanceVector le distance vector
	 */
	public InfosRoutage(final Agent source, final Agent destination,
			final Map<Agent,List<Route>> distanceVector) {
		super(source, destination);
		if (distanceVector == null) {
			throw new IllegalArgumentException(
					"Le distance vector ne peut pas être null!");
		}
		this.distanceVector = distanceVector;
	}
	/**
	 * Récupérer le distance vector.
	 * 
	 * @return le distance vector.
	 */
	public Map<Agent,List<Route>> getDistanceVector() {
		return distanceVector;
	}
}
