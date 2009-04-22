package be.simulation.routage;

import java.util.Set;
import java.util.TreeSet;
import be.simulation.entites.Agent;
import be.simulation.entites.Hote;

/**
 * Routeur interne à un agent, lui permet de déterminer où envoyer un message
 * donné.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class Routeur {
	/**
	 * Les différentes routes disponibles.
	 */
	private final Set<Route>	routes	= new TreeSet<Route>();
	/**
	 * Constructeur par défaut.
	 */
	public Routeur() {
	}
	
	public Agent trouverMeilleureRoute(final Hote destinataire) {
		// FIXME implementer
		throw new UnsupportedOperationException("Pas encore implémenté!");
	}
	
	/**
	 * Remise à zéro de ce routeur (effacement de la table de routage).
	 */
	public void reset() {
		this.routes.clear();
	}



	/**
	 * Ajouter une route à la table de routage.
	 * 
	 * @param route
	 *        la route à ajouter
	 */
	public void addRoute(final Route route) {
		if (route == null) {
			throw new IllegalArgumentException(
					"La route ne peut pas être null!");
		}
		this.routes.add(route);
	}
}
