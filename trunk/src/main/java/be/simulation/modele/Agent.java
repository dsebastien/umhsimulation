package be.simulation.modele;

/**
 * Agent du système (= serveur).
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-Francois
 */
public class Agent {
	/**
	 * Le numéro identifiant de cet agent.
	 */
	private final int	numeroAgent;
	
	/**
	 * Créer un nouvel agent.
	 * 
	 * @param numeroAgent
	 *        le numéro identifiant de cet agent
	 */
	public Agent(final int numeroAgent) {
		this.numeroAgent = numeroAgent;
	}
}
