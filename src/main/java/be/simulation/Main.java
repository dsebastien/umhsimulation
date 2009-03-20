package be.simulation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Point d'entrée du programme. Cette classe est uniquement présente pour
 * configurer la simulation suivant les paramètres par défaut ou fournis au
 * programme. La simulation est ensuite exécutée et les résultats affichés.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-Francois
 */
public class Main {
	public static void main(String[] args) {
		// FIXME prendre en compte les arguments
		// (voir CliParser, demander à Seb)
		
		// Chargement de la configuration de spring
		ApplicationContext springApplicationContext =
				new ClassPathXmlApplicationContext("applicationContext.xml");
		
		// Recupération de la simulation configurée par spring
		NetworkSimulation simulation =
				(NetworkSimulation) springApplicationContext
						.getBean("networkSimulation");
		
		// Lancement de la simulation
		simulation.demarrer();
	}
}
