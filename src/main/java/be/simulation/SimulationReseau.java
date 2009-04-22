package be.simulation;

import org.springframework.beans.factory.annotation.Autowired;
import be.simulation.core.AbstractSimulation;
import be.simulation.core.evenements.Evenement;
import be.simulation.entites.Agent;
import be.simulation.evenements.FinDeSimulation;
import be.simulation.routage.Route;

/**
 * Simulation d'un réseau.
 * 
 * @author Dubois Sebastien
 * @author Regnier Fréderic
 * @author Mernier Jean-François
 */
public class SimulationReseau extends
		AbstractSimulation {

	// Les agents de la simulation
	// (fixes, basés sur la figure 2 de l'énoncé)
	@Autowired
	private Agent	agent1000;
	@Autowired
	private Agent	agent2000;
	@Autowired
	private Agent	agent3000;
	@Autowired
	private Agent	agent4000;
	@Autowired
	private Agent	agent5000;
	@Autowired
	private Agent	agent6000;
	@Autowired
	private Agent	agent7000;



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afficherResultats() {
		// TODO ici on doit calculer les résultats finaux et les afficher)
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		resetBasicSimulation(); // temps, FEL

		// remise à zéro des agents
		agent1000.reset();
		agent2000.reset();
		agent3000.reset();
		agent4000.reset();
		agent5000.reset();
		agent6000.reset();
		agent7000.reset();
		
		// TODO définir les tables de routage de chaque agent (fixées pour la
		// première version!)
		// TODO plus tard, ajouter les évènements de type agent envoie infos
		// routage et faire en sorte que ça dure x temps de simulation
		// TODO ajouter les premiers évènements à la FEL (envoi des premiers
		// messages des hôtes)
		
		
		// On ajoute directement l'évènement de fin de simulation à la FEL
		FinDeSimulation finDeSimulation =
				new FinDeSimulation(getConfiguration()
						.getConfigurationSimulationReseau().getDuree());
		getFutureEventList().planifierEvenement(finDeSimulation);
	}



	/**
	 * Remise à zéro des tables de routage des agents.
	 */
	public void reinitialiserTablesRoutageAgents() {
		agent1000.getRouteur().reset();
		agent1000.getRouteur().addRoute(
				new Route(agent2000, agent2000, 10));
		// FIXME continuer
		
		
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void demarrer() {
		LOGGER.info("Démarrage de la simulation ("
				+ getConfiguration().getConfigurationSimulationReseau()
						.getNom() + ")");
		// à chaque tour de boucle on récupère
		// l'évènement imminent dans la FEL et on le traite
		while (true) {
			// TODO récupérer d'abord les évènements qu'on veut traiter en
			// priorité (infos routage, accusés de réception, ...)
			Evenement evenementImminent =
					getFutureEventList().getEvenementImminent();
			this.setHorloge(evenementImminent.getTempsPrevu());
			if (evenementImminent instanceof FinDeSimulation) {
				LOGGER.info(evenementImminent.toString());
				break;
			}
		}
		
		// TODO implementer
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getConfiguration().getConfigurationSimulationReseau().getNom();
	}



	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		agent1000.setNumero(1000);
		agent2000.setNumero(2000);
		agent3000.setNumero(3000);
		agent4000.setNumero(4000);
		agent5000.setNumero(5000);
		agent6000.setNumero(6000);
		agent7000.setNumero(7000);
		
		// on initialise les tables de routage des agents
		reinitialiserTablesRoutageAgents();
	}
}
