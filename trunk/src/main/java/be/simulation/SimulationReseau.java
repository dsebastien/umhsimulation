package be.simulation;

import org.springframework.beans.factory.annotation.Autowired;
import be.simulation.core.AbstractSimulation;
import be.simulation.core.evenements.Evenement;
import be.simulation.entites.Agent;
import be.simulation.entites.Hote;
import be.simulation.evenements.FinDeSimulation;
import be.simulation.evenements.HoteEnvoieMessageOriginal;
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



	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
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
			LOGGER.trace(evenementImminent.toString());
			// On avance le temps de simulation au temps de l'évènement imminent
			this.setHorloge(evenementImminent.getTempsPrevu());
			
			// Traitement de l'évènement imminent récupéré
			if (evenementImminent instanceof HoteEnvoieMessageOriginal) {
				// FIXME ok?
				HoteEnvoieMessageOriginal evt =
						(HoteEnvoieMessageOriginal) evenementImminent;
				evt.getHote().envoyerMessageOriginal();
			} else if (evenementImminent instanceof FinDeSimulation) {
				// on quitte la boucle principale (simulation terminée)
				break;
			}
		}
		
		// TODO implementer
	}



	/**
	 * Méthode utilisée pour générer et placer sur la FEL les premiers
	 * évènements d'envoi des hôtes d'un agent donné.
	 * 
	 * @param agent
	 *        l'agent
	 */
	private void genererPremiersEvenementsEnvoiDesHotes(Agent agent) {
		for (Hote h : agent.getHotes()) {
			long tempsEnvoi = h.genererTempsProchainEnvoi();
			getFutureEventList().planifierEvenement(
					new HoteEnvoieMessageOriginal(h, tempsEnvoi));
		}
	}



	public Agent getAgent1000() {
		return agent1000;
	}



	public Agent getAgent2000() {
		return agent2000;
	}



	public Agent getAgent3000() {
		return agent3000;
	}



	public Agent getAgent4000() {
		return agent4000;
	}



	public Agent getAgent5000() {
		return agent5000;
	}



	public Agent getAgent6000() {
		return agent6000;
	}



	public Agent getAgent7000() {
		return agent7000;
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
	public void reset() {
		resetBasicSimulation(); // temps, FEL
		
		agent1000.setNumero(1000);
		agent2000.setNumero(2000);
		agent3000.setNumero(3000);
		agent4000.setNumero(4000);
		agent5000.setNumero(5000);
		agent6000.setNumero(6000);
		agent7000.setNumero(7000);

		// remise à zéro des agents
		agent1000.reset();
		agent2000.reset();
		agent3000.reset();
		agent4000.reset();
		agent5000.reset();
		agent6000.reset();
		agent7000.reset();
		
		// TODO v2.0: à modifier
		reinitialiserTablesRoutageAgents();
		
		// TODO v2.0: ajouter les évènements de type agent envoie infos
		// routage et faire en sorte que ça dure x temps de simulation
		
		// Génération des premiers évènement d'envoi des hôtes
		genererPremiersEvenementsEnvoiDesHotes(agent1000);
		genererPremiersEvenementsEnvoiDesHotes(agent2000);
		genererPremiersEvenementsEnvoiDesHotes(agent3000);
		genererPremiersEvenementsEnvoiDesHotes(agent4000);
		genererPremiersEvenementsEnvoiDesHotes(agent5000);
		genererPremiersEvenementsEnvoiDesHotes(agent6000);
		genererPremiersEvenementsEnvoiDesHotes(agent7000);
		
		// On ajoute directement l'évènement de fin de simulation à la FEL
		FinDeSimulation finDeSimulation =
				new FinDeSimulation(getConfiguration()
						.getConfigurationSimulationReseau().getDuree());
		getFutureEventList().planifierEvenement(finDeSimulation);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getConfiguration().getConfigurationSimulationReseau().getNom();
	}
}
