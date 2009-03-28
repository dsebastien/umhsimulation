package be.simulation.entites;

import be.simulation.configuration.ConfigurationHotes;
import be.simulation.core.entites.AbstractEntiteSimulation;

/**
 * Hote du système, relié à un et un seul agent (liens statiques).
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class Hote extends AbstractEntiteSimulation<ConfigurationHotes> {
    /**
     * Agent auquel cet hote est relié (pour pouvoir communiquer avec lui).
     */
	private final Agent	agent;

	/**
	 * Numero identifiant de l'hote.
	 */
	private final int	numeroHote;


	/**
	 * Créer un nouvel hote.
	 * 
	 * @param agent
	 *        l'agent auquel l'hote est relie.
	 * @param numeroHote
	 *        le numéro identifiant de cet hote
	 * @throw IllegalArgumentException si le numéro d'hote fourni est <= 0
	 */
	public Hote(final Agent agent,
			final int numeroHote) {
		if(numeroHote <= 0){
			throw new IllegalArgumentException("Le numero d'hote fourni est invalide (il doit etre > 0)");
		}
		
		this.agent = agent;
		this.numeroHote = numeroHote;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// Faire ici toute initialisation qui requiert l'utilisation de la
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
}
