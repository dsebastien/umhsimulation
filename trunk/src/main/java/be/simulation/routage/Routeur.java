package be.simulation.routage;

import java.util.ArrayList;
import java.util.List;
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
	private final List<Route>	routes	= new ArrayList<Route>();



	/**
	 * Constructeur par défaut.
	 */
	public Routeur() {
	}
	
	public Agent trouverMeilleureRoute(final Hote destinataire) {
		// FIXME implementer
		throw new UnsupportedOperationException("Pas encore implémenté!");
	}
}
