package be.simulation.routage;

import be.simulation.entites.Agent;

/**
 * Route disponible.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class Route {
	/**
	 * Destination de cette route.
	 */
	private final Agent	agent;
	/**
	 * Coût de cette route.
	 */
	private final int	cout;



	/**
	 * Création d'une route.
	 * 
	 * @param agent
	 *        agent de destination de cette route
	 * @param cout
	 *        cout de cette route
	 */
	public Route(final Agent agent, final int cout) {
		if (agent == null) {
			throw new IllegalArgumentException(
					"L'agent de destination ne peut pas etre null!");
		}
		this.agent = agent;
		this.cout = cout;
	}



	/**
	 * Récupérer l'agent de destination de cette route
	 * 
	 * @return l'agent de destination de cette route
	 */
	public Agent getAgent() {
		return agent;
	}



	/**
	 * Récupéret le coût de cette route.
	 * 
	 * @return le coût de cette route.
	 */
	public int getCout() {
		return cout;
	}
}
