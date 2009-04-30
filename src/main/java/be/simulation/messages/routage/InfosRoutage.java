package be.simulation.messages.routage;

import java.util.List;

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
	 * Les routes données dans le message.
	 */
	private final List<Route> routes;

	/**
	 * Informations de routage.
	 * 
	 * @param source
	 *            l'agent à la source de l'envoi des informations de routage
	 * @param destination
	 *            l'agent destinataire des informations de routage
	 * @param routes les routes
	 */
	public InfosRoutage(final Agent source, final Agent destination,
			final List<Route> routes) {
		super(source, destination);
		if (routes == null) {
			throw new IllegalArgumentException(
					"Les routes ne peuvent pas être null!");
		}
		this.routes = routes;
	}
	// TODO v2.0: à définir

	/**
	 * Récupérer les routes.
	 * 
	 * @return les routes.
	 */
	public List<Route> getRoutes() {
		return routes;
	}
}
