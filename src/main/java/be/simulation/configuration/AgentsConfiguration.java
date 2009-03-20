package be.simulation.configuration;

import be.simulation.core.configuration.AbstractConfiguration;

/**
 * Configuration des {@link Agent}.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-Francois
 */
public class AgentsConfiguration extends AbstractConfiguration {
	/**
	 * Nombre d'hôtes par agent.
	 */
	private int	nombreHotes;



	/**
	 * Récupérer le nombre d'hôtes par agent.
	 * 
	 * @return le nombre d'hôtes par agent
	 */
	public int getNombreHotes() {
		return nombreHotes;
	}



	/**
	 * Modifier le nombre d'hôtes par agent (effectif seulement après une
	 * réinitialisation de la simulation).
	 * 
	 * @param nombreHotes
	 *        le nombre d'hôtes par agent à utiliser
	 */
	public void setNombreHotes(int nombreHotes) {
		this.nombreHotes = nombreHotes;
	}
}
