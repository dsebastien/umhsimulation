package be.simulation.entites;


/**
 * Hote du système, relié à un et un seul agent (liens statiques).
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class Hote extends AbstractEntiteSimulationReseau {
	
    /**
     * Agent auquel cet hote est relié (pour pouvoir communiquer avec lui).
     */
	private Agent	agent;

	/**
	 * Numero identifiant de l'hote.
	 */
	private int		numeroHote;



	/**
	 * Définir le numéro de cet hote
	 * 
	 * @param numeroHote
	 *        le numéro de cet hôte
	 * @throw IllegalArgumentException si le numéro d'hote fourni est <= 0
	 */
	public void setNumeroHote(int numeroHote) {
		if (numeroHote <= 0) {
			throw new IllegalArgumentException(
					"Le numero d'hote fourni est invalide (il doit etre > 0)");
		}
		this.numeroHote = numeroHote;
	}



	/**
	 * Créer un nouvel hote.
	 */
	public Hote() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Faire ici toute initialisation qui requiert l'utilisation de la
		// configuration
	}

	/**
	 * Récupérer le numero de l'hote.
	 * 
	 * @return le numéro de l'hote
	 */
	public int getNumeroHote() {
		return numeroHote;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Hote " + getNumeroHote();
	}

	@Override
	public void reset() {
		// TODO ici tout remettre à zéro (compteurs, ...)
	}



	/**
	 * Récupérer l'agent auquel cet hôte est connecté.
	 * 
	 * @return l'agent auquel cet hôte est connecté
	 */
	public Agent getAgent() {
		return agent;
	}



	/**
	 * Définir l'agent auquel cet hôte est connecté.
	 * 
	 * @param agent
	 *        l'agent auquel cet hôte est connecté
	 */
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
}
