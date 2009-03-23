package be.simulation.configuration;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import be.simulation.configuration.exceptions.OptionsIncorrectes;

/**
 * Tests de la classe de configuration et des options.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-Francois
 */
public class ConfigurationTest {
	/**
	 * Préfixe placé devant les options.
	 */
	private static final String	PREFIXE		= "-";
	/**
	 * Séparateur entre l'option et la valeur.
	 */
	private static final String	SEPARATEUR	= "=";
	/**
	 * Configuration testée.
	 */
	private Configuration		configuration;



	/**
	 * Exécuté avant chaque test.
	 */
	@Before
	public void setup() {
		final ConfigurationSimulationReseau cfgSimReseau =
				new ConfigurationSimulationReseau();
		final ConfigurationAgents cfgAgents = new ConfigurationAgents();
		final ConfigurationHotes cfgHotes = new ConfigurationHotes();
		configuration = new Configuration(cfgSimReseau, cfgAgents, cfgHotes);
	}



	/**
	 * Afficher l'aide.
	 * 
	 * @throws OptionsIncorrectes
	 *         ne doit pas avoir lieu
	 */
	@Test
	public void testAide() throws OptionsIncorrectes {
		final String[] args = {
			PREFIXE + Configuration.OPTION_AIDE
		};
		configuration.parse(args);
	}



	/**
	 * Parser une option incorrecte.
	 * 
	 * @throws OptionsIncorrectes
	 *         doit avoir lieu
	 */
	@Test(expected = OptionsIncorrectes.class)
	public void testOptionInvalide() throws OptionsIncorrectes {
		final String[] args = {
			PREFIXE + "oopsjenexistepas"
		};
		configuration.parse(args);
	}



	/**
	 * Spécifier un nombre valide d'hotes par agent.
	 * 
	 * @throws OptionsIncorrectes
	 *         ne doit pas avoir lieu
	 */
	@Test
	public void testAgentsNombreHotes() throws OptionsIncorrectes {
		final long valeur = 555;
		final String[] args =
				{
					PREFIXE + Configuration.OPTION_AGENTS_NOMBRE_HOTES
							+ SEPARATEUR + valeur
				};
		configuration.parse(args);
		assertEquals(valeur, configuration.getConfigurationAgents()
				.getNombreHotes());
	}



	/**
	 * Spécifier une valeur invalide pour le nombre d'hotes par agent.
	 * 
	 * @throws OptionsIncorrectes
	 *         doit avoir lieu
	 */
	@Test(expected = OptionsIncorrectes.class)
	public void testAgentsNombreHotesInvalide() throws OptionsIncorrectes {
		final String[] args =
				{
					PREFIXE + Configuration.OPTION_AGENTS_NOMBRE_HOTES
							+ SEPARATEUR + "BOOM"
				};
		configuration.parse(args);
	}
}
