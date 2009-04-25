package be.simulation;

import java.util.ArrayList;
import java.util.List;
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
import be.simulation.evenements.HoteFinTraitementMessage;
import be.simulation.evenements.HoteRecoitMessage;
import be.simulation.evenements.HoteTimeoutReceptionAccuse;
import be.simulation.utilitaires.Utilitaires;

/**
 * Simulation d'un réseau.
 * 
 * @author Dubois Sebastien
 * @author Regnier Fréderic
 * @author Mernier Jean-François
 */
public class SimulationReseau extends AbstractSimulation {
	// Les agents de la simulation
	// (fixes, basés sur la figure 2 de l'énoncé)
	@Autowired
	private Agent			agent1;
	@Autowired
	private Agent			agent2;
	@Autowired
	private Agent			agent3;
	@Autowired
	private Agent			agent4;
	@Autowired
	private Agent			agent5;
	@Autowired
	private Agent			agent6;
	@Autowired
	private Agent			agent7;
	
	/**
	 * Liste des agents (utile pour les statistiques).
	 */
	private List<Agent> agents = new ArrayList<Agent>();
	
	/**
	 * PRNG utilisé pour choisir un agent au hasard (utilisé pour les choix des
	 * agents de destination pour les nouveaux messages des hôtes).
	 */
	private final Random	generateurChoixAgent	= new Random();



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void calculerEtAfficherResultats() {
		// TODO sortir aussi les résultats dans une forme plus clean 
		// pour pouvoir s'en servir pour créer les graphiques
		
		LOGGER.info("------------------------------------------------------------------");
		LOGGER.info("Résultats de la simulation:                                       ");
		LOGGER.info("------------------------------------------------------------------");
		
		// messages perdus brutalement par les agents
		calculerEtAfficherMessagesPerdusBrutalement();
		// messages perdus pour cause de buffers pleins (agents)
		calculerEtAfficherMessagesPerdusBufferPlein();
		// taux d'utilisation des buffers
		calculerEtAfficherTauxUtilisationBufferAgents();
		

		
		
		
		// TODO continuer d'implémenter le calcul des stats
//		for(Agent agent: agents){
//		for(Hote h: agent.getHotes()){
//			//h.getAccusesReceptionRecus()
//			//h.getMessagesEnvoyes()
//			//h.getMessagesReexpedies()
//			//h.getTempsTotalVoyageMessages()
//			//h.getTimeoutsTropCourts()
//		}
//		}	
	}
	
	
	/**
	 * Calcule et affiche les stats concernant le taux d'utilisation du buffer des agents.
	 */
	private void calculerEtAfficherTauxUtilisationBufferAgents(){
		double sommeSommeTauxUtilisationBuffers = 0.0;
		for(Agent agent: agents){
			double moyenne = (double) agent.getSommeNiveauxOccupationBuffer() / (double) getConfiguration().getConfigurationSimulationReseau().getDuree() / (double) getConfiguration().getConfigurationAgents().getTailleBuffer();
			sommeSommeTauxUtilisationBuffers += moyenne;
			LOGGER.info("Le buffer de l'agent "+agent.getNumero()+" a été utilisé en moyenne à "+Utilitaires.pourcentage(moyenne));
		}
		// globalement
		// on doit diviser par le nombre d'agents puisque là on a le total pour tous les agents
		double moyenneGlobale = sommeSommeTauxUtilisationBuffers / (double) agents.size();
		LOGGER.info("Les buffers des agents ont été utilisés en moyenne à "+Utilitaires.pourcentage(moyenneGlobale));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Calcule et affiche les stats concernant les messages perdus à cause de buffers pleins (agents).
	 */
	private void calculerEtAfficherMessagesPerdusBufferPlein(){
		int totalPerdusBuffersPleins = 0;
		int totalRecus = 0;
		
		for(Agent agent: agents){
			// par agent
			int perdusBufferPlein = agent.getMessagesPerdusBufferPlein();
			int recus = agent.getMessagesRecus();
			
			// pour calculer l'info globale
			totalPerdusBuffersPleins +=perdusBufferPlein;
			totalRecus += recus;
				
			LOGGER.info("L'agent "+agent.getNumero()+" a perdu "+
					perdusBufferPlein+" messages sur "+recus+" recus car son buffer était plein ("+Utilitaires.pourcentage(perdusBufferPlein, recus)+")");
		}
		// globalement
		LOGGER.info("Au total les agents ont perdu "+totalPerdusBuffersPleins+" messages sur "+totalRecus+" recus à cause de buffers pleins ("+Utilitaires.pourcentage(totalPerdusBuffersPleins, totalRecus)+")");
	}
	
	
	
	
	/**
	 * Calcule et affiche les stats concernant les messages perdus brutalement par les agents.
	 */
	private void calculerEtAfficherMessagesPerdusBrutalement(){
		int totalPerdusBrutalement = 0;
		int totalRecus = 0;
		
		for(Agent agent: agents){
			// par agent
			int perdusBrutalement = agent.getMessagesPerdusBrutalement();
			int recus = agent.getMessagesRecus();
			
			// pour calculer l'info globale
			totalPerdusBrutalement +=perdusBrutalement;
			totalRecus += recus;
				
			LOGGER.info("L'agent "+agent.getNumero()+" a perdu brutalement "+
					perdusBrutalement+" messages sur "+recus+" recus ("+Utilitaires.pourcentage(perdusBrutalement, recus)+")");
		}
		// globalement
		LOGGER.info("Au total les agents ont perdu brutalement "+totalPerdusBrutalement+" messages sur "+totalRecus+" ("+Utilitaires.pourcentage(totalPerdusBrutalement, totalRecus)+")");
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
		while (!getFutureEventList().estVide()) {
			// TODO V2.0: récupérer d'abord les évènements de type 
			// AgentEnvoieInfosRoutage et AgentRecoitInfosRoutage
			
			// on essaie en priorité de traiter la réception des messages par les hôtes
			// car ça peut éviter l'envoi le déclenchement inutile de timeouts
			Evenement evenementImminent =
				getFutureEventList().getEvenementImminent(HoteRecoitMessage.class);
			
			if(evenementImminent == null){ 
				// s'il n'y a pas de réception de message à traiter
				// on récupère simplement l'évènement imminent
				evenementImminent = getFutureEventList().getEvenementImminent();
			}
			
			
			LOGGER.trace(evenementImminent.toString());
			// On avance le temps de simulation au temps de l'évènement imminent
			this.setHorloge(evenementImminent.getTempsPrevu());
			
			
			// Traitement de l'évènement imminent récupéré
			// chaque évènement contient toutes les informations nécessaires pour le traiter
			if (evenementImminent instanceof HoteEnvoieMessageOriginal) {
				// envoi d'un message original par un hôte
				HoteEnvoieMessageOriginal evt =
						(HoteEnvoieMessageOriginal) evenementImminent;
				evt.getHote().envoiMessageOriginal();
			} else if (evenementImminent instanceof AgentRecoitMessage) {
				// réception d'un message par un agent
				AgentRecoitMessage evt = (AgentRecoitMessage) evenementImminent;
				evt.getAgent().recoitMessage(evt.getMessage());
			} else if (evenementImminent instanceof AgentFinTraitementMessage) {
				// fin de traitement d'un message par un agent
				AgentFinTraitementMessage evt =
						(AgentFinTraitementMessage) evenementImminent;
				evt.getAgent().finitTraiterMessage(evt.getMessage());
			} else if (evenementImminent instanceof HoteTimeoutReceptionAccuse) {
				// timeout réception d'un accusé
				HoteTimeoutReceptionAccuse evt =
						(HoteTimeoutReceptionAccuse) evenementImminent;
				evt.getHote().timeoutReceptionAccuse(evt.getMessage());
			} else if (evenementImminent instanceof HoteRecoitMessage) {
				// réception d'un message par un hôte
				HoteRecoitMessage evt = (HoteRecoitMessage) evenementImminent;
				evt.getHote().recoitMessage(evt.getMessage());
			} else if (evenementImminent instanceof HoteFinTraitementMessage) {
				// fin de traitement d'un message par un hote
				HoteFinTraitementMessage evt = (HoteFinTraitementMessage) evenementImminent;
				evt.getHote().finitTraiterMessage(evt.getMessage());
			} else if (evenementImminent instanceof FinDeSimulation) {
				// on met à jour la statistique du taux d'utilisation du buffer de chaque agent
				// une derniere fois pour que le résultat final soit correct
				for(Agent a: agents){
					a.mettreAJourStatTauxUtilisationBuffer();
				}
				
				// on vide la FEL, ce qui provoque la sortie de cette boucle
				getFutureEventList().reset();
				LOGGER.info("Fin de la simulation");
			}
		}
		// on calcule et on affiche les résultats de la simulation
		calculerEtAfficherResultats();
	}



	/**
	 * Méthode utilisée pour générer et placer sur la FEL les premiers
	 * évènements d'envoi des hôtes des agents
	 * 
	 */
	private void genererPremiersEvenementsEnvoiDesHotes() {
		// avant d'ajouter quoi que ce soit sur la FEL on ajoute
		// à l'horloge la durée d'initialisation de la simulation
		// pour la première version ce temps est de 0
		// pour la seconde version utilisant le distance vector
		// on le fixera par exemple à 1000
		// pour dire que les 1000 premiers temps serviront à l'initialisation
		ajouterTempsHorloge(getConfiguration().getConfigurationSimulationReseau().getDureeInitialisation());
		for(Agent agent: agents){
			for (Hote h : agent.getHotes()) {
				HoteEnvoieMessageOriginal evt = h.genererEvenementHoteEnvoieMessageOriginal();
				getFutureEventList().planifierEvenement(evt);
			}
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
	public Agent getAgentAleatoire(final Agent exception) {
		if (exception == null) {
			throw new IllegalArgumentException(
					"L'agent exclus (l'exception) ne peut pas être null!");
		}
		Agent retVal = null;
		do {
			switch (generateurChoixAgent.nextInt(7) + 1) {
				case 1:
					retVal = agent1;
					break;
				case 2:
					retVal = agent2;
					break;
				case 3:
					retVal = agent3;
					break;
				case 4:
					retVal = agent4;
					break;
				case 5:
					retVal = agent5;
					break;
				case 6:
					retVal = agent6;
					break;
				case 7:
					retVal = agent7;
					break;
				default:
					LOGGER
							.error("Un problème a eu lieu pendant la sélection aléatoire d'un agent.");
			}
		} while (retVal == null || exception.equals(retVal));
		
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
		agent1.setNumero(1000);
		agent2.setNumero(2000);
		agent3.setNumero(3000);
		agent4.setNumero(4000);
		agent5.setNumero(5000);
		agent6.setNumero(6000);
		agent7.setNumero(7000);
		// remise à zéro des agents
		agent1.reset();
		agent2.reset();
		agent3.reset();
		agent4.reset();
		agent5.reset();
		agent6.reset();
		agent7.reset();
		// TODO v2.0: peut être à modifier
		reinitialiserTablesRoutageAgents();
		
		
		// on ajoute les agents à la liste 
		// (pour simplifier le calcul de certaines stats
		agents.clear();
		agents.add(agent1);
		agents.add(agent2);
		agents.add(agent3);
		agents.add(agent4);
		agents.add(agent5);
		agents.add(agent6);
		agents.add(agent7);
		
		// TODO v2.0: ajouter les premiers évènements de type
		// AgentEnvoieInfosRoutage
		// ces envois commencent au temps 0 jusqu'au temps dureeInitialisation au max
		
		
		// Génération des premiers évènement d'envoi des hôtes
		// TODO v2.0 ne générer des envois qu'à partir d'un temps donné
		// pour laisser le temps au distance vector de se stabiliser
		genererPremiersEvenementsEnvoiDesHotes();
		
		// On ajoute directement l'évènement de fin de simulation à la FEL
		// la fin doit avoir lieu après la durée d'initialisation + la durée de la simulation
		long dureeSimulation = getConfiguration()
		.getConfigurationSimulationReseau().getDuree();
		long dureeInitialisation = getConfiguration()
		.getConfigurationSimulationReseau().getDureeInitialisation();
		
		FinDeSimulation finDeSimulation =
				new FinDeSimulation(dureeInitialisation + dureeSimulation);
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
