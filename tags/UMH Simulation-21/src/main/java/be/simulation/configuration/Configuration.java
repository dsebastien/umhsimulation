package be.simulation.configuration;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Configuration globale. Dispose d'une copie de la configuration de chaque
 * entité du système et de la simulation elle même. Permet de récupérer les
 * options données en argument au programme. Le parsing des options se base sur
 * la librairie jOPT (http://jopt-simple.sourceforge.net).
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-Francois
 */
public class Configuration {
	private static final String					AGENTS_NOMBRE_HOTES	=
																			"agentsNombreHotes";
	/**
	 * Configuration des agents.
	 */
	private final ConfigurationAgents			configurationAgents;
	/**
	 * Configuration des hotes.
	 */
	private final ConfigurationHotes			configurationHotes;
	/**
	 * Configuration de la simulation.
	 */
	private final ConfigurationSimulationReseau	configurationSimulationReseau;
	/**
	 * Parser pour récupérer les options données via la ligne de commande.
	 */
	private final OptionParser					optionParser;



	/**
	 * Initialisation de la configuration par défaut.
	 * 
	 * @param configurationAgents
	 *        configuration par défaut des agents
	 * @param configurationHotes
	 *        configuration par défaut des hotes
	 */
	@Autowired
	public Configuration(
			ConfigurationSimulationReseau configurationSimulationReseau,
			ConfigurationAgents configurationAgents,
			ConfigurationHotes configurationHotes) {
		this.configurationAgents = configurationAgents;
		this.configurationHotes = configurationHotes;
		this.configurationSimulationReseau = configurationSimulationReseau;
		// initialisation du parser
		optionParser = new OptionParser();
		optionParser.accepts(AGENTS_NOMBRE_HOTES).withRequiredArg().ofType(
				Integer.class).describedAs("Nombre d'hotes par agent");
	}



	/**
	 * Récupérer la configuration des agents.
	 * 
	 * @return configuration des agents
	 */
	public ConfigurationAgents getConfigurationAgents() {
		return configurationAgents;
	}



	/**
	 * Récupérer la configuration des hotes.
	 * 
	 * @return configuration des hotes
	 */
	public ConfigurationHotes getConfigurationHotes() {
		return configurationHotes;
	}



	/**
	 * Récupérer la configuration de la simulation de réseau.
	 * 
	 * @return configuration de la simulation de réseau
	 */
	public ConfigurationSimulationReseau getConfigurationSimulationReseau() {
		return configurationSimulationReseau;
	}



	/**
	 * Parser les arguments fournis (venant à priori de la ligne de commande).
	 * 
	 * @param args
	 *        les arguments
	 */
	public void parse(String... args) {
		OptionSet options = optionParser.parse(args);
		if (options.has(AGENTS_NOMBRE_HOTES)) {
			configurationAgents.setNombreHotes((Integer) options
					.valueOf(AGENTS_NOMBRE_HOTES));
		}
		// FIXME continuer
	}
}
