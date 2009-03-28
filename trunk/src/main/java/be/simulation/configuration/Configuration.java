package be.simulation.configuration;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import be.simulation.configuration.exceptions.ExceptionOptionsInvalides;

/**
 * Configuration globale. Dispose d'une copie de la configuration de chaque
 * entité du système et de la simulation elle même. Permet de récupérer les
 * options données en argument au programme. Le parsing des options se base sur
 * la librairie jOPT (http://jopt-simple.sourceforge.net).
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class Configuration {
	/**
	 * Logger.
	 */
	private static final Logger					LOGGER											=
																										Logger
																												.getLogger(Configuration.class);
	/**
	 * Message utilisé quand les options sont mal spécifiées.
	 */
	private static final String					MSG_ERREUR_OPTIONS_INCORRECTES					=
																										"Une erreur s'est produite pendant la vérification des options fournies. Vérifiez la syntaxe (-opt=valeur). Utilisez -aide pour plus d'informations sur les commandes disponibles.";
	/**
	 * OPTION - Nombre d'hotes par agent.
	 */
	public static final String					OPTION_AGENTS_NOMBRE_HOTES						=
																										"agentsNombreHotes";
	/**
	 * OPTION - Taux de perte brutale des agents.
	 */
	public static final String					OPTION_AGENTS_TAUX_PERTE_BRUTALE				=
																										"agentsTauxPerteBrutale";
	/**
	 * OPTION - Temps de traitement d'un message par un agent.
	 */
	public static final String					OPTION_AGENTS_TEMPS_TRAITEMENT_MESSAGE			=
																										"agentsTempsTraitementMessage";
	
	/**
	 * OPTION - Delai entre un agent et un hôte (et inversément).
	 */
	public static final String					OPTION_SIMULATION_DELAI_ENTRE_ENTITES			=
																										"delaiEntreEntites";
	/**
	 * OPTION - Taux de messages à destination d'un autre agent.
	 */
	public static final String					OPTION_HOTES_TAUX_MESSAGES_VERS_AUTRE_AGENT		=
																										"hotesTauxMessagesVersAutreAgent";
	/**
	 * OPTION - Temps de traitement d'un message par un hote.
	 */
	public static final String					OPTION_HOTES_TEMPS_TRAITEMENT_MESSAGE			=
																										"hotesTempsTraitementMessage";
	/**
	 * OPTION - Durée de simulation.
	 */
	public static final String					OPTION_SIMULATION_DUREE							=
																										"duree";
	/**
	 * OPTION - Taille des buffers des agents.
	 */
	public static final String					OPTION_SIMULATION_TAILLE_BUFFERS_AGENTS			=
																										"tailleBuffersAgents";
	/**
	 * OPTION - Timeout pour la réémission des messages.
	 */
	public static final String					OPTION_SIMULATION_TIMEOUT_REEMISSION_MESSAGES	=
																										"timeoutReemissionMessages";
	/**
	 * OPTION - Aide.
	 */
	public static final List<String>			OPTIONS_AIDE									=
																										Arrays
																												.asList(
																														"aide",
																														"a",
																														"help",
																														"h");
	/**
	 * Configuration des agents.
	 */
	private final ConfigurationAgents			configurationAgents;
	/**
	 * Configuration des hôtes.
	 */
	private final ConfigurationHotes			configurationHotes;
	/**
	 * Configuration de la simulation.
	 */
	private final ConfigurationSimulationReseau	configurationSimulationReseau;
	// options (interne)
	private final OptionSpec<Long>				optionAgentsNombreHotes;
	private final OptionSpec<Float>				optionAgentsTauxPerteBrutale;
	private final OptionSpec<Float>				optionAgentsTempsTraitementMessage;
	private final OptionSpec<Void>				optionAide;
	private final OptionSpec<Float>				optionHotesTauxMessagesVersAutreAgent;
	private final OptionSpec<Float>				optionHotesTempsTraitementMessage;
	/**
	 * Parser pour récupérer les options données via la ligne de commande.
	 */
	private final OptionParser					optionParser;
	private final OptionSpec<Long>				optionSimulationDuree;
	private final OptionSpec<Integer>			optionSimulationTailleBuffersAgents;
	private final OptionSpec<Integer>			optionSimulationTimeoutReemissionMessages;
	private final OptionSpec<Integer>			optionSimulationDelaiEntreEntites;



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
		optionAide = optionParser.acceptsAll(OPTIONS_AIDE, "Aide");
		optionAgentsNombreHotes =
				optionParser.accepts(OPTION_AGENTS_NOMBRE_HOTES,
						"Nombre d'hotes par agent").withRequiredArg().ofType(
						Long.class);
		optionAgentsTauxPerteBrutale =
				optionParser
						.accepts(OPTION_AGENTS_TAUX_PERTE_BRUTALE,
								"Taux de perte brutale des agents (e.g., 0.05) (0 <= valeur < 1)")
						.withRequiredArg().ofType(Float.class);
		optionSimulationDuree =
				optionParser.accepts(OPTION_SIMULATION_DUREE,
						"Durée de la simulation (> 0)").withRequiredArg()
						.ofType(Long.class);
		optionHotesTauxMessagesVersAutreAgent =
				optionParser
						.accepts(
								OPTION_HOTES_TAUX_MESSAGES_VERS_AUTRE_AGENT,
								"Taux de messages d'un hôte qui seront à destination d'un hôte relié à un autre agent (e.g., 0.75) (0 <= valeur <= 1)")
						.withRequiredArg().ofType(Float.class);
		optionSimulationTimeoutReemissionMessages =
				optionParser
						.accepts(
								OPTION_SIMULATION_TIMEOUT_REEMISSION_MESSAGES,
								"Timeout après lequel les messages doivent etre réexpédiés si aucun accusé de réception n'est reçu (> 80)")
						.withRequiredArg().ofType(Integer.class);
		optionSimulationTailleBuffersAgents =
				optionParser.accepts(OPTION_SIMULATION_TAILLE_BUFFERS_AGENTS,
						"Taille des buffers des agents (>= 0)")
						.withRequiredArg().ofType(Integer.class);
		optionAgentsTempsTraitementMessage =
				optionParser
						.accepts(
								OPTION_AGENTS_TEMPS_TRAITEMENT_MESSAGE,
								"Temps de traitement d'un message par un agent (>=0). 0 = traitement instantané")
						.withRequiredArg().ofType(Float.class);
		optionHotesTempsTraitementMessage =
				optionParser
						.accepts(OPTION_HOTES_TEMPS_TRAITEMENT_MESSAGE,
								"Temps de traitement d'un message par un hote (>=0). 0 = traitement instantané")
						.withRequiredArg().ofType(Float.class);
		optionSimulationDelaiEntreEntites =
				optionParser
						.accepts(
								OPTION_SIMULATION_DELAI_ENTRE_ENTITES,
								"Délai nécessaire pour qu'un message d'un hôte arrive à l'agent (et inversément) (>=0)")
						.withRequiredArg().ofType(Integer.class);
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
	 * Mettre à jour la configuration des agents en fonction des options
	 * données.
	 * 
	 * @param options
	 *        les options
	 * @throws ExceptionOptionsInvalides
	 *         si la valeur d'une option est incorrecte
	 */
	private void majConfigurationAgents(final OptionSet options)
			throws ExceptionOptionsInvalides {
		// nombre d'hôtes par agent
		if (options.has(optionAgentsNombreHotes)) {
			final Long valeur = options.valueOf(optionAgentsNombreHotes);
			if (valeur <= 0) {
				throw new ExceptionOptionsInvalides(
						"Le nombres d'hotes par agent ne peut pas etre <= 0!");
			}
			configurationAgents.setNombreHotes(valeur);
		}
		// taux de perte brutale
		if (options.has(optionAgentsTauxPerteBrutale)) {
			final float tauxPerteBrutale =
					options.valueOf(optionAgentsTauxPerteBrutale);
			if (tauxPerteBrutale < 0 || tauxPerteBrutale >= 1) {
				throw new ExceptionOptionsInvalides(
						"Le taux de perte brutale d'un agent doit etre tel que: 0 <= taux < 1");
			}
			configurationAgents.setTauxPerteBrutale(tauxPerteBrutale);
		}
		if (options.has(optionAgentsTempsTraitementMessage)) {
			final float tempsTraitementMessage =
					options.valueOf(optionAgentsTempsTraitementMessage);
			if (tempsTraitementMessage < 0) {
				throw new ExceptionOptionsInvalides(
						"Le temps de traitement d'un message pour un agent doit être >=0");
			}
			configurationAgents
					.setTempsTraitementMessage(tempsTraitementMessage);
		}
	}



	/**
	 * Mettre à jour la configuration des hôtes en fonction des options données.
	 * 
	 * @param options
	 *        les options
	 * @throws ExceptionOptionsInvalides
	 */
	private void majConfigurationHotes(final OptionSet options)
			throws ExceptionOptionsInvalides {
		if (options.has(optionHotesTauxMessagesVersAutreAgent)) {
			final float tauxMessagesVersAutreAgent =
					options.valueOf(optionHotesTauxMessagesVersAutreAgent);
			if (tauxMessagesVersAutreAgent < 0
					|| tauxMessagesVersAutreAgent > 1) {
				throw new ExceptionOptionsInvalides(
						"Le taux de messages vers un hôte d'un autre agent doit être tel que 0 <= taux <= 1");
			}
			configurationHotes
					.setTauxMessagesVersAutreAgent(tauxMessagesVersAutreAgent);
		}
		if (options.has(optionHotesTempsTraitementMessage)) {
			final float tempsTraitementMessage =
					options.valueOf(optionHotesTempsTraitementMessage);
			if (tempsTraitementMessage < 0) {
				throw new ExceptionOptionsInvalides(
						"Le temps de traitement d'un message pour un hote doit être >=0");
			}
			configurationHotes
					.setTempsTraitementMessage(tempsTraitementMessage);
		}
	}



	/**
	 * Mettre à jour la configuration de la simulation en fonction des options
	 * données.
	 * 
	 * @param options
	 *        les options
	 * @throws ExceptionOptionsInvalides
	 */
	private void majConfigurationSimulation(final OptionSet options)
			throws ExceptionOptionsInvalides {
		if (options.has(optionSimulationDuree)) {
			long duree = options.valueOf(optionSimulationDuree);
			if (duree <= 0) {
				throw new ExceptionOptionsInvalides(
						"La durée de simulation ne peut pas être négative ou nulle!");
			}
			configurationSimulationReseau.setDuree(options
					.valueOf(optionSimulationDuree));
		}
		if (options.has(optionSimulationTimeoutReemissionMessages)) {
			final int timeoutReemission =
					options.valueOf(optionSimulationTimeoutReemissionMessages);
			int timeoutMinimal = 80;
			if (timeoutReemission <= timeoutMinimal) {
				throw new ExceptionOptionsInvalides(
						"Le timeout avant réémission des messages doit être >"
								+ timeoutMinimal);
			}
			configurationSimulationReseau
					.setTimeoutReemissionMessages(timeoutReemission);
		}
		if (options.has(optionSimulationTailleBuffersAgents)) {
			final int tailleBuffersAgents =
					options.valueOf(optionSimulationTailleBuffersAgents);
			if (tailleBuffersAgents < 0) {
				throw new ExceptionOptionsInvalides(
						"La taille des buffers des agents ne peut pas être < 0");
			}
			configurationSimulationReseau
					.setTailleBuffersAgents(tailleBuffersAgents);
		}
		
		if (options.has(optionSimulationDelaiEntreEntites)) {
			final int delaiEntreEntites =
					options.valueOf(optionSimulationDelaiEntreEntites);
			if (delaiEntreEntites < 0) {
				throw new ExceptionOptionsInvalides(
						"Le délai entre entités doit être >= 0");
			}
			configurationSimulationReseau
					.setDelaiEntreEntites(delaiEntreEntites);
		}
	}



	/**
	 * Effectue le parsing des arguments fourni (venant à priori de la ligne de
	 * commande) et met à jour. Si l'aide est demandée, le programme l'affiche
	 * et se termine.
	 * 
	 * @param args
	 *        les arguments
	 * @throws ExceptionOptionsInvalides
	 *         si des problèmes sont détectés pendant le parsing des options
	 *         (incorrectes, non fournies, ...)
	 * @return vrai si l'aide a été affichée (auquel cas on peut quitter
	 *         l'application car l'utilisateur voulait juste obtenir l'aide
	 */
	public boolean parse(final String... args) throws ExceptionOptionsInvalides {
		if (args == null) {
			throw new ExceptionOptionsInvalides(
					"Les arguments à parser ne peuvent pas être null");
		}
		OptionSet options = null;
		try {
			options = optionParser.parse(args);
			// affichage de l'aide si demandé
			if (options.has(optionAide)) {
				try {
					// on veut passer par log4j
					// donc on récupère le message
					StringWriter sw = new StringWriter();
					optionParser.printHelpOn(sw);
					LOGGER.info(sw.toString());
				} catch (IOException e) {
					LOGGER.warn("Un problème a empeché l'affichage de l'aide",
							e);
				}
				return true;
			}
		} catch (Exception e) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(e);
			}
			throw new ExceptionOptionsInvalides(MSG_ERREUR_OPTIONS_INCORRECTES);
		}
		// mise à jour de chaque configuration
		try {
			majConfigurationSimulation(options);
			majConfigurationAgents(options);
			majConfigurationHotes(options);
		} catch (OptionException e) {
			throw new ExceptionOptionsInvalides(MSG_ERREUR_OPTIONS_INCORRECTES);
		}
		return false; // on a pas affiché l'aide
	}
}
