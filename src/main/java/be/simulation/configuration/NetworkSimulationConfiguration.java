package be.simulation.configuration;

import be.simulation.core.configuration.AbstractConfiguration;

/**
 * Configuration de la simulation.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-Francois
 */
public class NetworkSimulationConfiguration extends AbstractConfiguration {
	/**
	 * Nom de la simulation.
	 */
	private String	nomDeSimulation;



	public NetworkSimulationConfiguration() {
	}



	public NetworkSimulationConfiguration(final String nom) {
		this.nomDeSimulation = nom;
	}



	/**
	 * Récupérer le nom de la simulation.
	 * 
	 * @return nom de la simulation
	 */
	public String getNom() {
		return nomDeSimulation;
	}



	/**
	 * Définir le nom de la simulation.
	 * 
	 * @param nom
	 *        le nom de la simulation
	 */
	public void setNom(String nom) {
		this.nomDeSimulation = nom;
	}
}
