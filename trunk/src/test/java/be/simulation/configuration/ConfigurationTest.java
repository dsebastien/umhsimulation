package be.simulation.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import be.simulation.configuration.exceptions.ExceptionOptionsInvalides;

/**
 * Tests de la classe de configuration et des options.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class ConfigurationTest {
	/**
	 * Préfixe placé devant les options.
	 */
	private static final String					PREFIXE			= "-";
	/**
	 * Séparateur entre l'option et la valeur.
	 */
	private static final String					SEPARATEUR		= "=";
	/**
	 * Classe testée.
	 */
	private Configuration						configuration;
	private final ConfigurationSimulationReseau	cfgSimReseau	=
																		new ConfigurationSimulationReseau();
	private final ConfigurationAgents			cfgAgents		=
																		new ConfigurationAgents();
	private final ConfigurationHotes			cfgHotes		=
																		new ConfigurationHotes();



	/**
	 * Exécuté avant chaque test.
	 */
	@Before
	public void setup() {
		configuration = new Configuration(cfgSimReseau, cfgAgents, cfgHotes);
	}



	/**
	 * Test getter.
	 */
	@Test
	public void testGetConfigurationSimulationReseau() {
		assertEquals(cfgSimReseau, configuration
				.getConfigurationSimulationReseau());
	}



	/**
	 * Test getter.
	 */
	@Test
	public void testGetConfigurationAgents() {
		assertEquals(cfgAgents, configuration.getConfigurationAgents());
	}



	/**
	 * Test getter.
	 */
	@Test
	public void testGetConfigurationHotes() {
		assertEquals(cfgHotes, configuration.getConfigurationHotes());
	}



	/**
	 * Si les arguments ne sont pas fournis, ça doit échouer.
	 * 
	 * @throws ExceptionOptionsInvalides
	 *         doit se produire
	 */
	@Test(expected = ExceptionOptionsInvalides.class)
	public void testOptionsNonFournies() throws ExceptionOptionsInvalides {
		configuration.parse((String[]) null);
	}



	/**
	 * Afficher l'aide.
	 * 
	 * @throws ExceptionOptionsInvalides
	 *         ne doit pas avoir lieu
	 */
	@Test
	public void testAide() throws ExceptionOptionsInvalides {
		for (String option : Configuration.OPTIONS_AIDE) {
			configuration.parse(new String[] {
				PREFIXE + option
			});
		}
	}



	/**
	 * Parser une option incorrecte.
	 * 
	 * @throws ExceptionOptionsInvalides
	 *         doit avoir lieu
	 */
	@Test(expected = ExceptionOptionsInvalides.class)
	public void testOptionInvalide() throws ExceptionOptionsInvalides {
		final String[] args = {
			PREFIXE + "oopsjenexistepas"
		};
		configuration.parse(args);
	}



	/**
	 * Spécifier un nombre valide d'hotes par agent.
	 * 
	 * @throws ExceptionOptionsInvalides
	 *         ne doit pas avoir lieu
	 */
	@Test
	public void testAgentsNombreHotes() throws ExceptionOptionsInvalides {
		final String valeur = "555";
		final String[] args =
				{
					PREFIXE + Configuration.OPTION_AGENTS_NOMBRE_HOTES
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Long.valueOf(valeur).longValue() == configuration
				.getConfigurationAgents().getNombreHotes());
	}



	/**
	 * Mal spécifier la valeur d'une option.
	 * 
	 * @throws ExceptionOptionsInvalides
	 *         doit avoir lieu
	 */
	@Test(expected = ExceptionOptionsInvalides.class)
	public void testAgentsNombreHotesInvalide()
			throws ExceptionOptionsInvalides {
		final String[] args =
				{
					PREFIXE + Configuration.OPTION_AGENTS_NOMBRE_HOTES
							+ SEPARATEUR + "BOOM"
				};
		configuration.parse(args);
	}



	/**
	 * Spécifier des valeurs incorrectes pour le nombre d'hotes par agent.
	 */
	@Test
	public void testAgentsNombreHotesValeursIncorrectes() {
		// valeur négative
		String[] args =
				new String[] {
					PREFIXE + Configuration.OPTION_AGENTS_NOMBRE_HOTES
							+ SEPARATEUR + "-10"
				};
		try {
			configuration.parse(args);
			fail();
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur non spécifiée
		args = new String[] {
			PREFIXE + Configuration.OPTION_AGENTS_NOMBRE_HOTES
		};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur non spécifiée
		args = new String[] {
			PREFIXE + Configuration.OPTION_AGENTS_NOMBRE_HOTES + SEPARATEUR
		};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur nulle
		args =
				new String[] {
					PREFIXE + Configuration.OPTION_AGENTS_NOMBRE_HOTES
							+ SEPARATEUR + "0"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
	}



	/**
	 * Spécifier un taux valide de pertes de brutales.
	 * 
	 * @throws ExceptionOptionsInvalides
	 *         ne doit pas avoir lieu
	 */
	@Test
	public void testAgentsTauxPerteBrutale() throws ExceptionOptionsInvalides {
		// une perte brutale non nulle
		String valeur = "0.5";
		String[] args =
				{
					PREFIXE + Configuration.OPTION_AGENTS_TAUX_PERTE_BRUTALE
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Float.valueOf(valeur).floatValue() == configuration
				.getConfigurationAgents().getTauxPerteBrutale());
		// aucune perte brutale
		valeur = "0";
		args =
				new String[] {
					PREFIXE + Configuration.OPTION_AGENTS_TAUX_PERTE_BRUTALE
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Float.valueOf(valeur).floatValue() == configuration
				.getConfigurationAgents().getTauxPerteBrutale());
	}



	/**
	 * Spécifier un taux invalide de perte brutale.
	 */
	@Test
	public void testAgentsTauxPerteBrutaleValeursIncorrectes() {
		// valeur négative
		String[] args =
				new String[] {
					PREFIXE + Configuration.OPTION_AGENTS_TAUX_PERTE_BRUTALE
							+ SEPARATEUR + "-100"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur trop haute (frontière)
		args =
				new String[] {
					PREFIXE + Configuration.OPTION_AGENTS_TAUX_PERTE_BRUTALE
							+ SEPARATEUR + "1.01"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur trop haute
		args =
				new String[] {
					PREFIXE + Configuration.OPTION_AGENTS_TAUX_PERTE_BRUTALE
							+ SEPARATEUR + "50"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
	}



	/**
	 * Spécifier la durée de la simulation et vérifier que c'est bien pris en
	 * compte.
	 * 
	 * @throws ExceptionOptionsInvalides
	 *         ne doit pas avoir lieu
	 */
	@Test
	public void testSimulationDuree() throws ExceptionOptionsInvalides {
		String valeur = "50";
		String[] args =
				{
					PREFIXE + Configuration.OPTION_SIMULATION_DUREE
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Long.valueOf(valeur).longValue() == configuration
				.getConfigurationSimulationReseau().getDuree());
		valeur = "1";
		args =
				new String[] {
					PREFIXE + Configuration.OPTION_SIMULATION_DUREE
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Long.valueOf(valeur).longValue() == configuration
				.getConfigurationSimulationReseau().getDuree());
	}



	/**
	 * Spécifier des valeurs incorrectes pour la durée de la simulation.
	 */
	@Test
	public void testSimulationDureeValeursIncorrectes() {
		// valeur négative
		String[] args =
				{
					PREFIXE + Configuration.OPTION_SIMULATION_DUREE
							+ SEPARATEUR + "-100"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur nulle
		args = new String[] {
			PREFIXE + Configuration.OPTION_SIMULATION_DUREE + SEPARATEUR + "0"
		};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur non spécifiée
		args = new String[] {
			PREFIXE + Configuration.OPTION_SIMULATION_DUREE + SEPARATEUR
		};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// argument non spécifié
		args = new String[] {
			PREFIXE + Configuration.OPTION_SIMULATION_DUREE
		};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
	}
	
	
	/**
	 * Spécifier la durée de la période d'initialisation de la simulation et vérifier que c'est bien pris en
	 * compte.
	 * 
	 * @throws ExceptionOptionsInvalides
	 *         ne doit pas avoir lieu
	 */
	@Test
	public void testSimulationDureeInitialisation() throws ExceptionOptionsInvalides {
		String valeur = "50";
		String[] args =
				{
					PREFIXE + Configuration.OPTION_SIMULATION_DUREE_INITIALISATION
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Long.valueOf(valeur).longValue() == configuration
				.getConfigurationSimulationReseau().getDureeInitialisation());
		valeur = "1";
		args =
				new String[] {
					PREFIXE + Configuration.OPTION_SIMULATION_DUREE_INITIALISATION
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Long.valueOf(valeur).longValue() == configuration
				.getConfigurationSimulationReseau().getDureeInitialisation());
		valeur = "0";
		args =
				new String[] {
					PREFIXE + Configuration.OPTION_SIMULATION_DUREE_INITIALISATION
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Long.valueOf(valeur).longValue() == configuration
				.getConfigurationSimulationReseau().getDureeInitialisation());
	}


	/**
	 * Spécifier des valeurs incorrectes pour la durée de la période d'initialisation de la simulation.
	 */
	@Test
	public void testSimulationDureeInitialisationValeursIncorrectes() {
		// valeur négative
		String[] args =
				{
					PREFIXE + Configuration.OPTION_SIMULATION_DUREE_INITIALISATION
							+ SEPARATEUR + "-100"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur non spécifiée
		args = new String[] {
			PREFIXE + Configuration.OPTION_SIMULATION_DUREE_INITIALISATION + SEPARATEUR
		};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// argument non spécifié
		args = new String[] {
			PREFIXE + Configuration.OPTION_SIMULATION_DUREE_INITIALISATION
		};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
	}



	/**
	 * Spécifier un taux valide de messages à destination d'hôtes d'autres
	 * agents.
	 * 
	 * @throws ExceptionOptionsInvalides
	 *         ne doit pas avoir lieu
	 */
	@Test
	public void testHotesTauxMessagesVersAutreAgent()
			throws ExceptionOptionsInvalides {
		String valeur = "0.75";
		String[] args =
				{
					PREFIXE
							+ Configuration.OPTION_HOTES_TAUX_MESSAGES_VERS_AUTRE_AGENT
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Float.valueOf(valeur).floatValue() == configuration
				.getConfigurationHotes().getTauxMessagesVersAutreAgent());
		valeur = "1.0";
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_HOTES_TAUX_MESSAGES_VERS_AUTRE_AGENT
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Float.valueOf(valeur).floatValue() == configuration
				.getConfigurationHotes().getTauxMessagesVersAutreAgent());
		valeur = "0.0";
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_HOTES_TAUX_MESSAGES_VERS_AUTRE_AGENT
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Float.valueOf(valeur).floatValue() == configuration
				.getConfigurationHotes().getTauxMessagesVersAutreAgent());
	}



	/**
	 * Spécifier un taux invalide de messages à destination d'hôtes d'autres
	 * agents.
	 */
	@Test
	public void testHotesTauxMessagesVersAutresAgentValeursIncorrectes() {
		// valeur négative
		String[] args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_HOTES_TAUX_MESSAGES_VERS_AUTRE_AGENT
							+ SEPARATEUR + "-100"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur trop grande
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_HOTES_TAUX_MESSAGES_VERS_AUTRE_AGENT
							+ SEPARATEUR + "50"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur trop basse
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_HOTES_TAUX_MESSAGES_VERS_AUTRE_AGENT
							+ SEPARATEUR + "-0.1"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur trop haute
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_HOTES_TAUX_MESSAGES_VERS_AUTRE_AGENT
							+ SEPARATEUR + "1.01"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
	}



	/**
	 * Spécifier des valeurs correctes pour le timeout de réémission.
	 * 
	 * @throws ExceptionOptionsInvalides
	 *         ne doit pas se produire
	 */
	@Test
	public void testHotesTimeoutReemission()
			throws ExceptionOptionsInvalides {
		final String valeur = "85";
		final String[] args =
				{
					PREFIXE
							+ Configuration.OPTION_HOTES_TIMEOUT_REEMISSION_MESSAGES
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Integer.valueOf(valeur).intValue() == configuration
				.getConfigurationHotes()
				.getTimeoutReemissionMessages());
	}



	/**
	 * Spécifier des valeurs incorrectes pour le timeout de réémission.
	 */
	@Test
	public void testHotesTimeoutReemissionValeursIncorrectes() {
		// valeur presque ok (frontière)
		String[] args =
				{
					PREFIXE
							+ Configuration.OPTION_HOTES_TIMEOUT_REEMISSION_MESSAGES
							+ SEPARATEUR + "79"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur trop basse
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_HOTES_TIMEOUT_REEMISSION_MESSAGES
							+ SEPARATEUR + "50"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
	}



	/**
	 * Spécifier des valeurs correctes pour la taille des buffers des agents.
	 * 
	 * @throws ExceptionOptionsInvalides
	 *         ne doit pas se produire
	 */
	@Test
	public void testAgentsTailleBuffer()
			throws ExceptionOptionsInvalides {
		String valeur = "0";
		String[] args =
				{
					PREFIXE
							+ Configuration.OPTION_AGENTS_TAILLE_BUFFER
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Long.MAX_VALUE == configuration
				.getConfigurationAgents().getTailleBuffer());
		valeur = "500";
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_AGENTS_TAILLE_BUFFER
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Long.valueOf(valeur).intValue() == configuration
				.getConfigurationAgents().getTailleBuffer());
		valeur = "1";
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_AGENTS_TAILLE_BUFFER
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Long.valueOf(valeur).intValue() == configuration
				.getConfigurationAgents().getTailleBuffer());
	}



	/**
	 * Spécifier des valeurs incorrectes pour la taille des buffers des agents.
	 */
	@Test
	public void testSimulationTailleBuffersAgentsValeursIncorrectes() {
		// valeur presque ok (frontière)
		String[] args =
				{
					PREFIXE
							+ Configuration.OPTION_AGENTS_TAILLE_BUFFER
							+ SEPARATEUR + "-1"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur trop basse
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_AGENTS_TAILLE_BUFFER
							+ SEPARATEUR + "-100"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
	}



	/**
	 * Spécifier des valeurs correctes pour le temps de traitement d'un message
	 * par un agent.
	 * 
	 * @throws ExceptionOptionsInvalides
	 *         ne doit pas se produire
	 */
	@Test
	public void testSimulationAgentTempsTraitementMessage()
			throws ExceptionOptionsInvalides {
		String valeur = "0"; // instantané
		String[] args =
				{
					PREFIXE
							+ Configuration.OPTION_AGENTS_TEMPS_TRAITEMENT_MESSAGE
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Float.valueOf(valeur).floatValue() == configuration
				.getConfigurationAgents().getTempsTraitementMessage());
		valeur = "0.5";
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_AGENTS_TEMPS_TRAITEMENT_MESSAGE
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Float.valueOf(valeur).floatValue() == configuration
				.getConfigurationAgents().getTempsTraitementMessage());
		valeur = "1";
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_AGENTS_TEMPS_TRAITEMENT_MESSAGE
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Float.valueOf(valeur).floatValue() == configuration
				.getConfigurationAgents().getTempsTraitementMessage());
	}



	/**
	 * Spécifier des valeurs incorrectes pour le temps de traitement d'un
	 * message par un agent.
	 */
	@Test
	public void testSimulationAgentTempsTraitementMessageValeursIncorrectes() {
		// valeur presque ok (frontière)
		String[] args =
				{
					PREFIXE
							+ Configuration.OPTION_AGENTS_TEMPS_TRAITEMENT_MESSAGE
							+ SEPARATEUR + "-0.1"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur trop basse
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_AGENTS_TEMPS_TRAITEMENT_MESSAGE
							+ SEPARATEUR + "-1"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur trop haute
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_AGENTS_TEMPS_TRAITEMENT_MESSAGE
							+ SEPARATEUR + "1.1"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
	}



	/**
	 * Spécifier des valeurs correctes pour le temps de traitement d'un message
	 * par un hote.
	 * 
	 * @throws ExceptionOptionsInvalides
	 *         ne doit pas se produire
	 */
	@Test
	public void testSimulationHoteTempsTraitementMessage()
			throws ExceptionOptionsInvalides {
		String valeur = "0"; // instantané
		String[] args =
				{
					PREFIXE
							+ Configuration.OPTION_HOTES_TEMPS_TRAITEMENT_MESSAGE
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Float.valueOf(valeur).floatValue() == configuration
				.getConfigurationHotes().getTempsTraitementMessage());
		valeur = "0.5";
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_HOTES_TEMPS_TRAITEMENT_MESSAGE
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Float.valueOf(valeur).floatValue() == configuration
				.getConfigurationHotes().getTempsTraitementMessage());
		valeur = "1.0";
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_HOTES_TEMPS_TRAITEMENT_MESSAGE
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Float.valueOf(valeur).floatValue() == configuration
				.getConfigurationHotes().getTempsTraitementMessage());
	}



	/**
	 * Spécifier des valeurs incorrectes pour le temps de traitement d'un
	 * message par un hote.
	 */
	@Test
	public void testSimulationHoteTempsTraitementMessageValeursIncorrectes() {
		// valeur presque ok (frontière)
		String[] args =
				{
					PREFIXE
							+ Configuration.OPTION_HOTES_TEMPS_TRAITEMENT_MESSAGE
							+ SEPARATEUR + "-0.1"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur trop basse
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_HOTES_TEMPS_TRAITEMENT_MESSAGE
							+ SEPARATEUR + "-1"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur trop haute
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_HOTES_TEMPS_TRAITEMENT_MESSAGE
							+ SEPARATEUR + "1.1"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
	}



	/**
	 * Spécifier des valeurs correctes pour le delai entre entités (hote ->
	 * agent / agent -> hôte).
	 * 
	 * @throws ExceptionOptionsInvalides
	 *         ne doit pas se produire
	 */
	@Test
	public void testSimulationDelaiEntreEntites()
			throws ExceptionOptionsInvalides {
		String valeur = "0";
		String[] args =
				{
					PREFIXE
							+ Configuration.OPTION_SIMULATION_DELAI_ENTRE_ENTITES
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Integer.valueOf(valeur).intValue() == configuration
				.getConfigurationSimulationReseau().getDelaiEntreEntites());
		valeur = "5";
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_SIMULATION_DELAI_ENTRE_ENTITES
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Integer.valueOf(valeur).intValue() == configuration
				.getConfigurationSimulationReseau().getDelaiEntreEntites());
		valeur = "1";
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_SIMULATION_DELAI_ENTRE_ENTITES
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Integer.valueOf(valeur).intValue() == configuration
				.getConfigurationSimulationReseau().getDelaiEntreEntites());
	}



	/**
	 * Spécifier des valeurs incorrectes pour le delai entre entités (hote ->
	 * agent / agent -> hôte).
	 */
	@Test
	public void testSimulationDelaiEntreEntitesValeursIncorrectes() {
		// valeur presque ok (frontière)
		String[] args =
				{
					PREFIXE
							+ Configuration.OPTION_SIMULATION_DELAI_ENTRE_ENTITES
							+ SEPARATEUR + "-1"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur trop basse
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_SIMULATION_DELAI_ENTRE_ENTITES
							+ SEPARATEUR + "-100"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Spécifier des valeurs correctes pour le temps maximal inter-envois des
	 * hôtes.
	 * 
	 * @throws ExceptionOptionsInvalides
	 *         ne doit pas se produire
	 */
	@Test
	public void testSimulationHoteTempsMaxInterEnvois()
			throws ExceptionOptionsInvalides {
		String valeur = "200";
		String[] args =
				{
					PREFIXE + Configuration.OPTION_HOTES_TEMPS_MAX_INTER_ENVOIS
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Integer.valueOf(valeur).intValue() == configuration
				.getConfigurationHotes().getTempsMaxInterEnvois());
		valeur = "1";
		args =
				new String[] {
					PREFIXE + Configuration.OPTION_HOTES_TEMPS_MAX_INTER_ENVOIS
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertTrue(Integer.valueOf(valeur).intValue() == configuration
				.getConfigurationHotes().getTempsMaxInterEnvois());
	}



	/**
	 * Spécifier des valeurs incorrectes pour le temps maximal inter-envois des
	 * hôtes.
	 */
	@Test
	public void testSimulationHoteTempsMaxInterEnvoisValeursIncorrectes() {
		// valeur presque ok (frontière)
		String[] args =
				{
					PREFIXE + Configuration.OPTION_HOTES_TEMPS_MAX_INTER_ENVOIS
							+ SEPARATEUR + "0"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
		// valeur trop basse
		args =
				new String[] {
					PREFIXE
							+ Configuration.OPTION_HOTES_TEMPS_TRAITEMENT_MESSAGE
							+ SEPARATEUR + "-1"
				};
		try {
			configuration.parse(args);
			fail("Une exception aurait du avoir lieu!");
		} catch (ExceptionOptionsInvalides e) {
		}
	}
}
