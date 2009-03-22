package be.simulation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import be.simulation.configuration.Configuration;

/**
 * Point d'entrée du programme. Cette classe est uniquement présente pour
 * configurer la simulation suivant les paramètres par défaut ou ceux fournis au
 * programme. La simulation est ensuite exécutée et les résultats affichés.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-Francois
 */
public class Main {
	/**
	 * Point d'entrée du programme.
	 * 
	 * @param args
	 *        arguments donnés au lancement
	 */
	public static void main(String[] args) {
		// Chargement de la configuration de spring
		ApplicationContext springApplicationContext =
				new ClassPathXmlApplicationContext("applicationContext.xml");
		// Modification de la configuration en fonction des paramètres donnés
		// par l'utilisateur
		Configuration configuration =
				(Configuration) springApplicationContext
						.getBean("configuration");
		configuration.parse(args);
		// Recupération de la simulation configurée par spring
		SimulationReseau simulation =
				(SimulationReseau) springApplicationContext
						.getBean("simulationReseau");
		// Lancement de la simulation
		simulation.demarrer();
	}
}
