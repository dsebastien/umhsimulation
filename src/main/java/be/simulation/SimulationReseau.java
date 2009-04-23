package be.simulation;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import be.simulation.core.AbstractSimulation;
import be.simulation.core.evenements.Evenement;
import be.simulation.entites.Agent;
import be.simulation.entites.Hote;
import be.simulation.evenements.AgentFinTraitementMessage;
import be.simulation.evenements.AgentRecoitMessage;
import be.simulation.evenements.FinDeSimulation;
import be.simulation.evenements.HoteEnvoieMessageOriginal;
import be.simulation.evenements.HoteTimeoutReceptionAccuse;
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
	private Agent	agent1;
	@Autowired
	private Agent	agent2;
	@Autowired
	private Agent	agent3;
	@Autowired
	private Agent	agent4;
	@Autowired
	private Agent	agent5;
	@Autowired
	private Agent	agent6;
	@Autowired
	private Agent	agent7;


	/**
	 * PRNG utilisé pour choisir un agent au hasard (utilisé pour les choix des
	 * agents de destination pour les nouveaux messages des hôtes).
	 */
	private final Random	generateurChoixAgent	= new Random();

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
				// envoi d'un message original par un hôte
				HoteEnvoieMessageOriginal evt =
						(HoteEnvoieMessageOriginal) evenementImminent;
				evt.getHote().envoyerMessageOriginal();
			} else if (evenementImminent instanceof AgentRecoitMessage){
				// réception d'un message par un agent
				AgentRecoitMessage evt = (AgentRecoitMessage) evenementImminent;
				evt.getAgent().recoitMessage(evt.getMessage());
			} else if (evenementImminent instanceof AgentFinTraitementMessage) {
				// fin de traitement d'un message par un agent
				AgentFinTraitementMessage evt =
						(AgentFinTraitementMessage) evenementImminent;
				evt.getAgent().finitTraiterMessage(evt.getMessage());
			} else if (evenementImminent instanceof HoteTimeoutReceptionAccuse){
				// timeout réception d'un accusé
				HoteTimeoutReceptionAccuse evt = (HoteTimeoutReceptionAccuse) evenementImminent;
				//FIXME implémenter
				//evt.getHote().t
			} else if (evenementImminent instanceof FinDeSimulation) {
				// on quitte la boucle principale (simulation terminée)
				break;
			}
		}
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
			h.genererEvenementHoteEnvoieMessageOriginal();
		}
	}


	/**
	 * Récupérer un agent aléatoire pouvant être n'importe lequel sauf celui
	 * fourni en argument.
	 * 
	 * @param exception
	 *        le seul agent ne pouvant pas être retourné
	 * @return un agent aléatoire autre que celui donné en argument
	 */
	public Agent getAgentAleatoire(final Agent exception){
		if(exception == null){
			throw new IllegalArgumentException("L'agent exclus (l'exception) ne peut pas être null!");
		}
		
		Agent retVal = null;
		do{
			switch(generateurChoixAgent.nextInt(7) + 1){
				case 1: retVal = agent1; break;
				case 2: retVal = agent2; break;
				case 3: retVal = agent3; break;
				case 4: retVal = agent4; break;
				case 5: retVal = agent5; break;
				case 6: retVal = agent6; break;
				case 7: retVal = agent7; break;
				default:
					LOGGER
							.error("Un problème a eu lieu pendant la sélection aléatoire d'un agent.");
			}
		}while(retVal == null || exception.equals(retVal));
		
		return retVal;
	}



	/**
	 * Remise à zéro des tables de routage des agents.
	 */
	private void reinitialiserTablesRoutageAgents() {
		// pour aller de l'agent A à l'agent B, on passe par le voisin X avec un
		// coût de Y: agentA...addRoute(agentB,agentX,Y);
		
		// routes de l'agent 1
		agent1.getRouteur().reset();
		agent1.getRouteur().ajouterRoute(agent2, agent2, 10);
		agent1.getRouteur().ajouterRoute(agent3, agent3, 20);
		agent1.getRouteur().ajouterRoute(agent4, agent2, 10);
		agent1.getRouteur().ajouterRoute(agent5, agent2, 10);
		agent1.getRouteur().ajouterRoute(agent6, agent2, 10);
		agent1.getRouteur().ajouterRoute(agent7, agent2, 10);
		
		// routes de l'agent 2
		agent2.getRouteur().reset();
		agent2.getRouteur().ajouterRoute(agent1, agent1, 10);
		agent2.getRouteur().ajouterRoute(agent3, agent4, 10);
		agent2.getRouteur().ajouterRoute(agent4, agent4, 10);
		agent2.getRouteur().ajouterRoute(agent5, agent4, 10);
		agent2.getRouteur().ajouterRoute(agent6, agent7, 30);
		agent2.getRouteur().ajouterRoute(agent7, agent7, 30);
		
		// routes de l'agent 3
		agent3.getRouteur().reset();
		agent3.getRouteur().ajouterRoute(agent1, agent1, 20);
		agent3.getRouteur().ajouterRoute(agent2, agent4, 10);
		agent3.getRouteur().ajouterRoute(agent4, agent4, 10);
		agent3.getRouteur().ajouterRoute(agent5, agent4, 10);
		agent3.getRouteur().ajouterRoute(agent6, agent4, 10);
		agent3.getRouteur().ajouterRoute(agent7, agent4, 10);
		
		// routes de l'agent 4
		agent4.getRouteur().reset();
		agent4.getRouteur().ajouterRoute(agent1, agent2, 10);
		agent4.getRouteur().ajouterRoute(agent2, agent2, 10);
		agent4.getRouteur().ajouterRoute(agent3, agent3, 10);
		agent4.getRouteur().ajouterRoute(agent5, agent5, 20);
		agent4.getRouteur().ajouterRoute(agent6, agent2, 10);
		agent4.getRouteur().ajouterRoute(agent7, agent5, 20);
		
		// routes de l'agent 5
		agent5.getRouteur().reset();
		agent5.getRouteur().ajouterRoute(agent1, agent4, 20);
		agent5.getRouteur().ajouterRoute(agent2, agent4, 20);
		agent5.getRouteur().ajouterRoute(agent3, agent4, 20);
		agent5.getRouteur().ajouterRoute(agent4, agent4, 20);
		agent5.getRouteur().ajouterRoute(agent6, agent7, 10);
		agent5.getRouteur().ajouterRoute(agent7, agent7, 10);
		
		// routes de l'agent 6
		agent6.getRouteur().reset();
		agent6.getRouteur().ajouterRoute(agent1, agent7, 20);
		agent6.getRouteur().ajouterRoute(agent2, agent7, 20);
		agent6.getRouteur().ajouterRoute(agent3, agent7, 20);
		agent6.getRouteur().ajouterRoute(agent4, agent7, 20);
		agent6.getRouteur().ajouterRoute(agent5, agent7, 20);
		agent6.getRouteur().ajouterRoute(agent7, agent7, 20);
		
		// routes de l'agent 7
		agent7.getRouteur().reset();
		agent7.getRouteur().ajouterRoute(agent1, agent2, 30);
		agent7.getRouteur().ajouterRoute(agent2, agent2, 30);
		agent7.getRouteur().ajouterRoute(agent3, agent5, 10);
		agent7.getRouteur().ajouterRoute(agent4, agent5, 10);
		agent7.getRouteur().ajouterRoute(agent5, agent5, 10);
		agent7.getRouteur().ajouterRoute(agent6, agent6, 20);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		resetBasicSimulation(); // temps, FEL
		
		agent1.setNumero(10000);
		agent2.setNumero(20000);
		agent3.setNumero(30000);
		agent4.setNumero(40000);
		agent5.setNumero(50000);
		agent6.setNumero(60000);
		agent7.setNumero(70000);

		// remise à zéro des agents
		agent1.reset();
		agent2.reset();
		agent3.reset();
		agent4.reset();
		agent5.reset();
		agent6.reset();
		agent7.reset();
		
		// TODO v2.0: à modifier
		reinitialiserTablesRoutageAgents();
		
		// TODO v2.0: ajouter les premiers évènements de type AgentEnvoieInfosRoutage 
		// et faire en sorte que ça dure x temps de simulation
		
		// Génération des premiers évènement d'envoi des hôtes
		genererPremiersEvenementsEnvoiDesHotes(agent1);
		genererPremiersEvenementsEnvoiDesHotes(agent2);
		genererPremiersEvenementsEnvoiDesHotes(agent3);
		genererPremiersEvenementsEnvoiDesHotes(agent4);
		genererPremiersEvenementsEnvoiDesHotes(agent5);
		genererPremiersEvenementsEnvoiDesHotes(agent6);
		genererPremiersEvenementsEnvoiDesHotes(agent7);
		
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
