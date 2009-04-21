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


	/**
	 * Configuration de la simulation.
	 */
	private Configuration		configuration;
	
	protected final Logger	LOGGER	=
													Logger
															.getLogger(getClass().getName());

	/**
	 * Cette méthode est appelée après que toutes les propriétés aient étés
	 * initialisées. Permet de mettre l'entité dans l'état souhaité.
	 */
	public abstract void afterPropertiesSet() throws Exception;



	/**
	 * Récupérer l'application context de Spring.
	 * 
	 * @return l'application context de Spring
	 */
	protected ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * Récupérer la configuration de la simulation.
	 * 
	 * @return la configuration de la simulation
	 */
	protected Configuration getConfiguration() {
		return configuration;
	}


	/**
	 * Initialise ou réinitialise l'entité à son état initial.
	 */
	public abstract void reset();



	/**
	 * Placer l'application context (appelé automatiquement par Spring)
	 */
	public void setApplicationContext(
			final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
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
	protected void setConfiguration(final Configuration config) {
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
