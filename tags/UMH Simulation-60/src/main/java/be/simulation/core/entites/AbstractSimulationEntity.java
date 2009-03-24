package be.simulation.core.entites;

import org.springframework.beans.factory.annotation.Autowired;
import be.simulation.core.configuration.AbstractConfiguration;

/**
 * Entité quelconque d'une simulation. Chaque type d'entité (pouvant à priori
 * être configurée), dispose via cette classe d'un mécanisme de configuration
 * simple à utiliser.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 * @param <T>
 *        Le type de configuration utilisé par cette entité
 */
public abstract class AbstractSimulationEntity<T extends AbstractConfiguration> {
	/**
	 * Configuration de l'entité.
	 */
	private T	configuration;



	/**
	 * Récupérer la configuration de l'entité.
	 * 
	 * @return la configuration de l'entité
	 */
	public T getConfiguration() {
		return configuration;
	}



	/**
	 * Remplace la configuration de l'entité.
	 * 
	 * @param config
	 *        la nouvelle configuration
	 * @throws IllegalArgumentException
	 *         si la configuration donnée est nulle
	 */
	@Autowired
	// Spring appèle cette méthode automatiquement
	public void setConfiguration(T config) {
		if (config == null) {
			throw new IllegalArgumentException(
					"La configuration ne peut pas être nulle");
		}
		this.configuration = config;
	}
}
