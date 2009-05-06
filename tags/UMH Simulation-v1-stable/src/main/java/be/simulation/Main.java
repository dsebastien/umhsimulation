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
		ApplicationContext springApplicationContext =
				new ClassPathXmlApplicationContext("applicationContext.xml");
		Configuration configuration =
				(Configuration) springApplicationContext
						.getBean("configuration");
		// Modification de la configuration en fonction des paramètres donnés
		// par l'utilisateur
		try {
			boolean aideAffichee = configuration.parse(args);
			// on quitte car l'utilisateur voulait juste voir l'aide
			if (aideAffichee) {
				System.exit(0);
			}
		} catch (ExceptionOptionsInvalides e) {
			LOGGER.fatal(e.getMessage());
			System.exit(0);
		}
		// Recupération de la simulation pré-configurée par spring
		SimulationReseau simulation =
				(SimulationReseau) springApplicationContext
						.getBean("simulationReseau");
		
		LOGGER.info("Initialisation de la simulation...");
		// important (initialisation des agents, 
		// hôtes, tables de routage ...
		simulation.reset();
		LOGGER.info("Simulation initialisee");
		
		// Affichage de la configuration
		configuration.afficher();
		
		// Lancement de la simulation
		simulation.demarrer();

		LOGGER.info("Fin de la simulation");
		
		//FIXME ajouter à l'uml les endroits où on met à jour les infos globales
	}
}