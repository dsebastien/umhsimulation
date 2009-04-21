package be.simulation.core.entites;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import be.simulation.configuration.Configuration;

/**
 * Entité quelconque d'une simulation. Chaque type d'entité (pouvant à priori
 * être configurée), dispose via cette classe d'un mécanisme de configuration
 * simple à utiliser.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public abstract class AbstractEntiteSimulation implements InitializingBean,
		ApplicationContextAware {
	/**
	 * Application context de Spring.
	 */
	private ApplicationContext	applicationContext;


	protected final Logger	LOGGER	=
													Logger
															.getLogger(getClass().getName());
	
	/**
	 * Configuration de la simulation.
	 */
	protected Configuration	configuration;

	/**
	 * Récupérer la configuration de la simulation.
	 * 
	 * @return la configuration de la simulation
	 */
	public Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * Remplace la configuration de la simulation.
	 * 
	 * @param config
	 *        la nouvelle configuration
	 * @throws IllegalArgumentException
	 *         si la configuration donnée est nulle
	 */
	@Autowired
	public void setConfiguration(Configuration config) {
		if (config == null) {
			throw new IllegalArgumentException(
					"La configuration ne peut pas être nulle");
		}
		configuration = config;
	}

	/**
	 * Initialise ou réinitialise l'entité à son état initial.
	 */
	public abstract void reset();


	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract String toString();

	/**
	 * Cette méthode est appelée après que toutes les propriétés aient étés
	 * initialisées. Permet de mettre l'entité dans l'état souhaité.
	 */
	public abstract void afterPropertiesSet() throws Exception;


	public void setApplicationContext(
			final ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	/**
	 * Récupérer l'application context de Spring.
	 * 
	 * @return l'application context de Spring
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
