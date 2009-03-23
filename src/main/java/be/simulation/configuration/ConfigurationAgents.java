package be.simulation.configuration;

import be.simulation.core.configuration.AbstractConfiguration;

/**
 * Configuration des {@link Agent}.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-Francois
 */
public class ConfigurationAgents extends AbstractConfiguration {
	/**
	 * Nombre d'hôtes par agent.
	 */
	private long	nombreHotes;



	/**
	 * Récupérer le nombre d'hôtes par agent.
	 * 
	 * @return le nombre d'hôtes par agent
	 */
	public long getNombreHotes() {
		return nombreHotes;
	}



	/**
	 * Modifier le nombre d'hôtes par agent (effectif seulement après une
	 * réinitialisation de la simulation).
	 * 
	 * @param nombreHotes
	 *        le nombre d'hôtes par agent à utiliser
	 */
	public void setNombreHotes(final long nombreHotes) {
		this.nombreHotes = nombreHotes;
	}
}
