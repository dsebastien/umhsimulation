package be.simulation;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import be.simulation.configuration.Configuration;
import be.simulation.configuration.exceptions.ExceptionOptionsInvalides;

/**
 * Point d'entrée du programme. Cette classe est uniquement présente pour
 * configurer la simulation suivant les paramètres par défaut ou ceux fournis au
 * programme. La simulation est ensuite exécutée et les résultats affichés.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class Main {
	/**
	 * Logger.
	 */
	private static final Logger	LOGGER	= Logger.getLogger(Main.class);



	/**
	 * Point d'entrée du programme.
	 * 
	 * @param args
	 *        arguments donnés au lancement
	 */
	public static void main(String[] args) {
		// Chargement de la configuration par défaut
		// (définie dans l'application context de spring)
		ApplicationContext springApplicationContext =
				new ClassPathXmlApplicationContext("applicationContext.xml");
		Configuration configuration =
				(Configuration) springApplicationContext
						.getBean("configuration");
		// Modification de la configuration en fonction des paramètres donnés
		// par l'utilisateur
		try {
			boolean aideAffichee = configuration.parse(args);
			if (aideAffichee) {
				// on quitte car l'utilisateur voulait juste de l'aide
				System.exit(0);
			}
		} catch (ExceptionOptionsInvalides e) {
			LOGGER.fatal(e.getMessage());
			System.exit(0);
		}
		// Recupération de la simulation configurée par spring
		SimulationReseau simulation =
				(SimulationReseau) springApplicationContext
						.getBean("simulationReseau");
		// Lancement de la simulation
		simulation.demarrer();
		simulation.afficherResultats();
	}
}
