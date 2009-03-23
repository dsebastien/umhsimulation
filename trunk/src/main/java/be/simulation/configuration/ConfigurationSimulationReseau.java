package be.simulation.configuration;

import be.simulation.core.configuration.AbstractConfiguration;

/**
 * Configuration de la simulation de réseau (projet).
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-Francois
 */
public class ConfigurationSimulationReseau extends AbstractConfiguration {
	/**
	 * Durée de simulation.
	 */
	private long	duree;
	/**
	 * Nom de la simulation.
	 */
	private String	nom;



	/**
	 * Constructeur par défaut.
	 */
	public ConfigurationSimulationReseau() {
	}



	/**
	 * Récupérer la durée de la simulation
	 * 
	 * @return la durée de la simulation
	 */
	public long getDuree() {
		return duree;
	}



	/**
	 * Récupérer le nom de la simulation.
	 * 
	 * @return nom de la simulation
	 */
	public String getNom() {
		return nom;
	}



	/**
	 * Déterminer la durée de la simulation.
	 * 
	 * @param duree
	 *        durée de la simulation
	 */
	public void setDuree(final long duree) {
		this.duree = duree;
	}



	/**
	 * Définir le nom de la simulation.
	 * 
	 * @param nom
	 *        le nom de la simulation
	 */
	public void setNom(final String nom) {
		this.nom = nom;
	}
}
