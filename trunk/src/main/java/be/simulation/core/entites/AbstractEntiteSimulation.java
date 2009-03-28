package be.simulation.core.entites;

import org.springframework.beans.factory.InitializingBean;
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
public abstract class AbstractEntiteSimulation<T extends AbstractConfiguration>
		implements InitializingBean {
	/**
	 * Configuration de l'entité.
	 */
	private T	configuration;



	/**
	 * Cette méthode est appelée après que toutes les propriétés aient étés
	 * initialisées. Permet de mettre l'entité dans l'état souhaité.
	 */
	public abstract void afterPropertiesSet() throws Exception;



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
	public void setConfiguration(T config) {
		if (config == null) {
			throw new IllegalArgumentException(
					"La configuration ne peut pas être nulle");
		}
		configuration = config;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract String toString();
}
