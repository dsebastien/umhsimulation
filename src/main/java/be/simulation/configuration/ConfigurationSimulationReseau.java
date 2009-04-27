package be.simulation.configuration;

import be.simulation.core.configuration.AbstractConfiguration;

/**
 * Configuration de la simulation de réseau (projet).
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class ConfigurationSimulationReseau extends AbstractConfiguration {
	/**
	 * Temps nécessaire pour qu'un message partant d'un hôte arrive à son agent
	 * (et inversément) (>= 0).
	 */
	private int		delaiEntreEntites;
	/**
	 * Durée de simulation (> 0).
	 */
	private long	duree;
	/**
	 * Durée de la période d'initialisation du système (>= 0).
	 */
	private long	dureeInitialisation;
	/**
	 * Nom de la simulation.
	 */
	private String	nom;
	/**
	 * Quand afficher les statistiques? (exemple: 0.1 = tous les 10% de la
	 * simulation).
	 */
	private float	periodiciteAffichageStatistiques;



	/**
	 * Constructeur par défaut.
	 */
	public ConfigurationSimulationReseau() {
	}



	public int getDelaiEntreEntites() {
		return delaiEntreEntites;
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
	 * Récupérer la durée de la période d'initialisation de la simulation (>=0)
	 * 
	 * @return la durée de la période d'initialisation de la simulation
	 */
	public long getDureeInitialisation() {
		return dureeInitialisation;
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
	 * Récupérer la périodicité pour l'affichage des stats.
	 * 
	 * @return la périodicité pour l'affichage des stats.
	 */
	public float getPeriodiciteAffichageStatistiques() {
		return periodiciteAffichageStatistiques;
	}



	/**
	 * Définir le délai entre entités (agent <-> hôte).
	 * 
	 * @param delaiEntreEntites
	 *        le délai entre entités
	 */
	public void setDelaiEntreEntites(final int delaiEntreEntites) {
		if (delaiEntreEntites < 0) {
			throw new IllegalArgumentException(
					"Le délai entre entités doit être >= 0");
		}
		this.delaiEntreEntites = delaiEntreEntites;
	}



	/**
	 * Déterminer la durée de la simulation (> 0).
	 * 
	 * @param duree
	 *        durée de la simulation
	 */
	public void setDuree(final long duree) {
		if (duree <= 0) {
			throw new IllegalArgumentException(
					"La durée de la simulation doit etre > 0");
		}
		this.duree = duree;
	}



	/**
	 * Déterminer la durée de la période d'initialisation de la simulation (>=
	 * 0).
	 * 
	 * @param dureeInit
	 *        durée de la période d'initialisation de la simulation
	 */
	public void setDureeInitialisation(final long dureeInit) {
		if (dureeInit < 0) {
			throw new IllegalArgumentException(
					"La durée de la période d'initialisation de la simulation doit etre >= 0");
		}
		this.dureeInitialisation = dureeInit;
	}



	/**
	 * Définir le nom de la simulation.
	 * 
	 * @param nom
	 *        le nom de la simulation
	 */
	public void setNom(final String nom) {
		if (nom == null || "".equals(nom.trim())) {
			throw new IllegalArgumentException(
					"Le nom de la simulation ne peut pas etre null ou vide");
		}
		this.nom = nom;
	}



	/**
	 * Définir la périodicité pour l'affichage des stats.
	 * 
	 * @param periodiciteAffichageStatistiques
	 *        la périodicité pour l'affichage des stats.
	 */
	public void setPeriodiciteAffichageStatistiques(
			float periodiciteAffichageStatistiques) {
		if (periodiciteAffichageStatistiques <= 0
				|| periodiciteAffichageStatistiques > 1) {
			throw new IllegalArgumentException(
					"La périodicité d'affichage des stats doit être telle que: 0 < periodicite <= 1");
		}
		this.periodiciteAffichageStatistiques =
				periodiciteAffichageStatistiques;
	}
}
