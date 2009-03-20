package be.simulation.core.configuration;

import org.apache.log4j.Logger;

/**
 * Configuration générique. Si nécessaire on peut implémenter ici les interfaces
 * communes à toutes les configurations.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-Francois
 */
public abstract class AbstractConfiguration {
	protected final Logger	LOGGER	= Logger.getLogger(getClass());
}
