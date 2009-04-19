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
	 * Nom de la simulation.
	 */
	private String	nom;
	/**
	 * Timeout après lequel les messages doivent être réexpédiés si aucun accusé
	 * de réception n'est reçu (> 80).
	 */
	private int		timeoutReemissionMessages;



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
	 * Récupérer le nom de la simulation.
	 * 
	 * @return nom de la simulation
	 */
	public String getNom() {
		return nom;
	}



	/**
	 * Récupérer le timeout après lequel les messages doivent être réexpédiés si
	 * aucun accusé de réception n'est reçu.
	 * 
	 * @return le timeout
	 */
	public int getTimeoutReemissionMessages() {
		return timeoutReemissionMessages;
	}






	public void setDelaiEntreEntites(int delaiEntreEntites) {
		this.delaiEntreEntites = delaiEntreEntites;
	}



	/**
	 * Déterminer la durée de la simulation (> 0).
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





	/**
	 * Définir le timeout pour la réémission des messages (> 80).
	 * 
	 * @param timeoutReemissionMessages
	 *        le timeout.
	 */
	public void setTimeoutReemissionMessages(final int timeoutReemissionMessages) {
		this.timeoutReemissionMessages = timeoutReemissionMessages;
	}
}
