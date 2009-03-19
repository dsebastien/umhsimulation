package be.simulation.core.configuration;

import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Generic configuration.
 * 
 * @author Dubois Sebastien
 */
public abstract class AbstractConfiguration {
	private static final String	DEFAULT_CONFIGURATION_FILENAME	=
																		"config.properties";
	
	private static final Logger	LOG				=
														Logger
																.getLogger(AbstractConfiguration.class);
	
	/**
	 * The managed configuration.
	 */
	private final Properties	configuration					=
																		new Properties();
	

	/**
	 * Loads the specified configuration file (should be in the appropriate
	 * package).
	 */
	public AbstractConfiguration(String filename) {
		LOG.info("Loading the configuration...");
		try {
			configuration.load(this.getClass().getResourceAsStream(filename));
		} catch (IOException e) {
			LOG.fatal(e);
			throw new Error(e);
		}
		LOG.info("Configuration loaded");
	}



	/**
	 * Loads the default configuration file.
	 */
	public AbstractConfiguration(){
		this(DEFAULT_CONFIGURATION_FILENAME);
	}



	/**
	 * Get the configuration.
	 * 
	 * @return the configuration
	 */
	protected Properties getConfiguration() {
		return configuration;
	}
}
