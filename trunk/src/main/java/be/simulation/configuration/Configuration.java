package be.simulation.configuration;

import java.io.IOException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import be.simulation.configuration.exceptions.OptionsIncorrectes;

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
	/**
	 * Logger.
	 */
	private static final Logger					LOGGER				=
																			Logger
																					.getLogger(Configuration.class);
	/**
	 * OPTION - Nombre d'hotes par agent.
	 */
	public static final String					OPTION_AGENTS_NOMBRE_HOTES	=
																			"agentsNombreHotes";
	/**
	 * OPTION - Aide.
	 */
	public static final String					OPTION_AIDE				= "aide";
	/**
	 * OPTION - Durée de simulation.
	 */
	public static final String					OPTION_SIMULATION_DUREE	= "duree";
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
		// TODO séparer en plusieurs méthodes pour la clarté
		optionParser.accepts(OPTION_AIDE).withOptionalArg().describedAs("Aide");
		optionParser.accepts(OPTION_AGENTS_NOMBRE_HOTES).withRequiredArg().ofType(
				Long.class).describedAs("Nombre d'hotes par agent");
		optionParser.accepts(OPTION_SIMULATION_DUREE).withRequiredArg().ofType(
				Long.class).describedAs("Durée de la simulation");
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
	 * Effectue le parsing des arguments fourni (venant à priori de la ligne de
	 * commande) et met à jour. Si l'aide est demandée, le programme l'affiche
	 * et se termine.
	 * 
	 * @param args
	 *        les arguments
	 * @throws OptionsIncorrectes
	 *         si des problèmes sont détectés pendant le parsing des options
	 *         (incorrectes, ...)
	 * @return vrai si l'aide a été affichée (auquel cas on peut quitter
	 *         l'application car l'utilisateur voulait juste obtenir l'aide
	 */
	public boolean parse(final String... args) throws OptionsIncorrectes {
		if (args == null) {
			throw new OptionsIncorrectes(
					"Les arguments à parser ne peuvent pas être null");
		}
		OptionSet options = null;
		try {
			options = optionParser.parse(args);
			// affichage de l'aide si demandé
			if (options.has(OPTION_AIDE)) {
				try {
					optionParser.printHelpOn(System.out);
				} catch (IOException e) {
					LOGGER.warn("Un problème a empeché l'affichage de l'aide",
							e);
				}
				return true;
			}
			// mise à jour de chaque configuration
			majConfigurationSimulation(options);
			majConfigurationAgents(options);
			majConfigurationHotes(options);
		} catch (Exception e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e);
			}
			throw new OptionsIncorrectes(
					"Une erreur s'est produite pendant la vérification des options fournies. Vérifiez la syntaxe (-opt=valeur). Utilisez -aide pour plus d'informations sur les commandes disponibles.");
		}
		return false;
	}



	/**
	 * Mettre à jour la configuration de la simulation en fonction des options
	 * données.
	 * 
	 * @param options
	 *        les options
	 */
	private void majConfigurationSimulation(final OptionSet options) {
		if (options.has(OPTION_SIMULATION_DUREE)) {
			configurationSimulationReseau.setDuree((Long) options
					.valueOf(OPTION_SIMULATION_DUREE));
		}
	}



	/**
	 * Mettre à jour la configuration des agents en fonction des options
	 * données.
	 * 
	 * @param options
	 *        les options
	 */
	private void majConfigurationAgents(final OptionSet options) {
		if (options.has(OPTION_AGENTS_NOMBRE_HOTES)) {
			configurationAgents.setNombreHotes((Long) options
					.valueOf(OPTION_AGENTS_NOMBRE_HOTES));
		}
	}



	/**
	 * Mettre à jour la configuration des hôtes en fonction des options données.
	 * 
	 * @param options
	 *        les options
	 */
	private void majConfigurationHotes(final OptionSet options) {
	}
}
