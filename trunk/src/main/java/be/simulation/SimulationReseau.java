package be.simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import be.simulation.core.AbstractSimulation;
import be.simulation.core.evenements.Evenement;
import be.simulation.entites.Agent;
import be.simulation.entites.Hote;
import be.simulation.evenements.AffichageStatistiques;
import be.simulation.evenements.AgentFinTraitementMessage;
import be.simulation.evenements.AgentRecoitMessage;
import be.simulation.evenements.FinDeSimulation;
import be.simulation.evenements.HoteEnvoieMessageOriginal;
import be.simulation.evenements.HoteFinTraitementMessage;
import be.simulation.evenements.HoteRecoitMessage;
import be.simulation.evenements.HoteTimeoutReceptionAccuse;
import be.simulation.evenements.routage.AgentEnvoieInfosRoutage;
import be.simulation.evenements.routage.AgentRecoitInfosRoutage;
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
	private Agent agent1;
	@Autowired
	private Agent agent2;
	@Autowired
	private Agent agent3;
	@Autowired
	private Agent agent4;
	@Autowired
	private Agent agent5;
	@Autowired
	private Agent agent6;
	@Autowired
	private Agent agent7;
	/**
	 * Liste des agents (utile pour les statistiques).
	 */
	private final List<Agent> agents = new ArrayList<Agent>();
	/**
	 * PRNG utilisé pour choisir un agent au hasard (utilisé pour les choix des
	 * agents de destination pour les nouveaux messages des hôtes).
	 */
	private final Random generateurChoixAgent = new Random();

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void calculerEtAfficherResultats() {
		double tempsMoyenEntreEmissionEtReceptionAccuse = 0;
		if (Hote.TOTAL_ACCUSES_RECUS > 0) {
			tempsMoyenEntreEmissionEtReceptionAccuse = (double) Hote.TOTAL_TEMPS_VOYAGE_MESSAGES
					/ (double) Hote.TOTAL_ACCUSES_RECUS;
		}
		double tempsMoyenDansBuffersVoyageAbsolu = 0;
		if (Hote.TOTAL_ACCUSES_RECUS > 0) {
			tempsMoyenDansBuffersVoyageAbsolu = ((double) Hote.TOTAL_TEMPS_BUFFERS / (double) Hote.TOTAL_ACCUSES_RECUS);
		}
		double tempsMoyenDansBuffersVoyageComplet = 0;
		if (Hote.TOTAL_TEMPS_VOYAGE_MESSAGES > 0) {
			tempsMoyenDansBuffersVoyageComplet = ((double) Hote.TOTAL_TEMPS_BUFFERS / (double) Hote.TOTAL_TEMPS_VOYAGE_MESSAGES);
		}
		int totalMessagesPerdus = Agent.TOTAL_MESSAGES_PERDUS_BRUTALEMENT
				+ Agent.TOTAL_MESSAGES_PERDUS_BUFFER_PLEIN;
		double pourcentageMessagesPerdus = 0;
		if (totalMessagesPerdus > 0) {
			pourcentageMessagesPerdus = (double) totalMessagesPerdus
					/ (double) Agent.TOTAL_MESSAGES_RECUS;
		}
		double pourcentageMessagesPerdusBrutalement = 0;
		if (Agent.TOTAL_MESSAGES_PERDUS_BRUTALEMENT > 0) {
			pourcentageMessagesPerdusBrutalement = (double) Agent.TOTAL_MESSAGES_PERDUS_BRUTALEMENT
					/ (double) Agent.TOTAL_MESSAGES_RECUS;
		}
		double pourcentageMessagesPerdusBuffersPleins = 0;
		if (Agent.TOTAL_MESSAGES_PERDUS_BUFFER_PLEIN > 0) {
			pourcentageMessagesPerdusBuffersPleins = (double) Agent.TOTAL_MESSAGES_PERDUS_BUFFER_PLEIN
					/ (double) Agent.TOTAL_MESSAGES_RECUS;
		}
		double pourcentageMessagesReexpedies = 0;
		if (Hote.TOTAL_MESSAGES_REEXPEDIES > 0) {
			pourcentageMessagesReexpedies = (double) Hote.TOTAL_MESSAGES_REEXPEDIES
					/ (double) Hote.TOTAL_MESSAGES_ENVOYES;
		}
		double sommeSommeTauxUtilisationBuffersAgents = 0;
		Map<Agent, Double> utilisationMoyenneBufferAgent = new HashMap<Agent, Double>();
		for (Agent agent : agents) {
			double moyenne = agent.getSommeNiveauxOccupationBuffer()
					/ getConfiguration().getConfigurationSimulationReseau()
							.getDuree()
					/ getConfiguration().getConfigurationAgents()
							.getTailleMaxBuffer();
			utilisationMoyenneBufferAgent.put(agent, moyenne);
			sommeSommeTauxUtilisationBuffersAgents += moyenne;
		}
		// globalement
		// on doit diviser par le nombre d'agents puisque là on a le total pour
		// tous les agents
		double utilisationMoyenneBuffersAgents = sommeSommeTauxUtilisationBuffersAgents
				/ agents.size();

		// le vrai temps de simulation correspond à:
		// temps d'horloge actuel - durée de la période d'initialisation
		long tempsActuelSimulation = getHorloge()
				- getConfiguration().getConfigurationSimulationReseau()
						.getDureeInitialisation();
		LOGGER.info("Temps actuel de simulation: "
				+ tempsActuelSimulation
				+ "/"
				+ getConfiguration().getConfigurationSimulationReseau()
						.getDuree());
		LOGGER.info("Agents - Messages en cours de traitement: "
				+ Agent.TOTAL_MESSAGES_EN_COURS_TRAITEMENT);
		LOGGER.info("Agents - Messages reçus: " + Agent.TOTAL_MESSAGES_RECUS);
		LOGGER.info("Agents - Messages perdus: " + totalMessagesPerdus + " ("
				+ Utilitaires.pourcentage(pourcentageMessagesPerdus) + ")");
		LOGGER.info("Agents - Messages perdus brutalement: "
				+ Agent.TOTAL_MESSAGES_PERDUS_BRUTALEMENT + " ("
				+ Utilitaires.pourcentage(pourcentageMessagesPerdusBrutalement)
				+ ")");
		LOGGER.info("Agents - Messages perdus buffer plein: "
				+ Agent.TOTAL_MESSAGES_PERDUS_BUFFER_PLEIN
				+ " ("
				+ Utilitaires
						.pourcentage(pourcentageMessagesPerdusBuffersPleins)
				+ ")");
		LOGGER.info("Agents - Buffers utilisés en moyenne à "
				+ Utilitaires.pourcentage(utilisationMoyenneBuffersAgents));
		for (Agent agent : agents) {
			LOGGER.info("Agent "
					+ agent.getNumero()
					+ " - Buffer utilisé en moyenne à "
					+ Utilitaires.pourcentage(utilisationMoyenneBufferAgent
							.get(agent)));
		}
		for (Agent agent : agents) {
			int perdusBufferPlein = agent.getMessagesPerdusBufferPlein();
			int recus = agent.getMessagesRecus();
			double pourcentageMessagesPerdusBufferPlein = 0;
			if (recus > 0) {
				pourcentageMessagesPerdusBufferPlein = (double) perdusBufferPlein
						/ (double) recus;
			}
			// pour calculer l'info globale
			LOGGER.info("Agent "
					+ agent.getNumero()
					+ " - "
					+ perdusBufferPlein
					+ " messages perdus sur "
					+ recus
					+ " recus car son buffer était plein ("
					+ Utilitaires
							.pourcentage(pourcentageMessagesPerdusBufferPlein)
					+ ")");
		}
		for (Agent agent : agents) {
			int perdusBrutalement = agent.getMessagesPerdusBrutalement();
			int recus = agent.getMessagesRecus();
			double pourcentagePerdusBrutalement = 0;
			if (recus > 0) {
				pourcentagePerdusBrutalement = (double) perdusBrutalement
						/ (double) recus;
			}
			// pour calculer l'info globale
			LOGGER.info("Agent " + agent.getNumero() + " - "
					+ perdusBrutalement + " messages perdus brutalement sur "
					+ recus + " recus ("
					+ Utilitaires.pourcentage(pourcentagePerdusBrutalement)
					+ ")");
		}
		LOGGER.info("Hotes - Messages originaux envoyés: "
				+ Hote.TOTAL_MESSAGES_ENVOYES);
		LOGGER.info("Hotes - Messages réexpédiés: "
				+ Hote.TOTAL_MESSAGES_REEXPEDIES + " ("
				+ Utilitaires.pourcentage(pourcentageMessagesReexpedies) + ")");
		LOGGER.info("Hotes - Accuses de réception reçus: "
				+ Hote.TOTAL_ACCUSES_RECUS);
		LOGGER.info("Hotes - Timeouts trop courts: "
				+ Hote.TOTAL_TIMEOUTS_TROP_COURTS);
		LOGGER.info("Hotes - Messages en cours de traitement: "
				+ Hote.TOTAL_MESSAGES_EN_COURS_TRAITEMENT);
		LOGGER.info("Hotes - Message le plus réémis: "
				+ (Hote.MESSAGE_LE_PLUS_REEMIS.getNumeroEmission() - 1)
				+ " fois");
		LOGGER
				.info("Le temps moyen entre l'émission d'un message et la réception de l'accusé correspondant est de "
						+ tempsMoyenEntreEmissionEtReceptionAccuse);
		LOGGER.info("Les " + Hote.TOTAL_ACCUSES_RECUS
				+ " messages ont passé en absolu "
				+ tempsMoyenDansBuffersVoyageAbsolu
				+ " unités de temps dans des buffers");
		LOGGER.info("Les messages acquittés ont passé en moyenne "
				+ Utilitaires.pourcentage(tempsMoyenDansBuffersVoyageComplet)
				+ " de leur temps de voyage complet dans des buffers");
		LOGGER
				.info("Le message ayant mis le plus de temps à être acquitté a pris "
						+ Hote.MESSAGE_TEMPS_MAX.getTempsTrajet()
						+ " unités de temps");
		LOGGER.info("-----------------------------------------------------");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void demarrer() {
		LOGGER.info("Démarrage de la simulation ("
				+ getConfiguration().getConfigurationSimulationReseau()
						.getNom() + ")");
		LOGGER
				.info("------------------------------------------------------------------");
		
		// TODO ajouter ici la logique pour le calcul du temps entre affichages
		// de stats
		// dans la boucle, afficher si delai dépassé.
		
		
		// boucle principale de la simulation
		while (!getFutureEventList().estVide()) {
			// Pour un temps t donné:
			// En tout premier lieu, on affiche les statistiques si nécessaire
			
			//TODO utiliser un timer ici
			// calculer en fonction de la cfg, le délai entre affichages des stats
			// ici, vérifier si ça fait au moins x temps qu'on a pas affiché les stats
			// si oui on affiche et on enregistre le dernier temps d'affichage
			
			Evenement evenementImminent = getFutureEventList()
					.getEvenementImminent(AffichageStatistiques.class);
			// S'il n'y a plus de statistiques à afficher, on vérifie si la
			// simulation ne doit pas
			// se terminer
			if (evenementImminent == null) {
				evenementImminent = getFutureEventList().getEvenementImminent(
						FinDeSimulation.class);
			}
			// TODO v2.0 déterminer lequel des deux devrait avoir priorité (si
			// utile)
			// on traiter d'abord l'envoi et la réception d'informations de
			// routage
			if (evenementImminent == null) {
				evenementImminent = getFutureEventList().getEvenementImminent(
						AgentRecoitInfosRoutage.class);
			}
			if (evenementImminent == null) {
				evenementImminent = getFutureEventList().getEvenementImminent(
						AgentEnvoieInfosRoutage.class);
			}
			// finalement on traite en priorité la réception de messages
			// de manière à éviter le déclenchement inutile de timeouts
			if (evenementImminent == null) {
				evenementImminent = getFutureEventList().getEvenementImminent(
						HoteRecoitMessage.class);
			}
			// dans tous les autres cas on traite l'évènement imminent
			if (evenementImminent == null) {
				evenementImminent = getFutureEventList().getEvenementImminent();
			}
			LOGGER.trace(evenementImminent.toString());
			// On avance le temps de simulation au temps de l'évènement imminent
			this.setHorloge(evenementImminent.getTempsPrevu());
			// Traitement de l'évènement imminent récupéré
			// chaque évènement contient toutes les informations nécessaires
			// pour le traiter
			if (evenementImminent instanceof HoteEnvoieMessageOriginal) {
				// envoi d'un message original par un hôte
				HoteEnvoieMessageOriginal evt = (HoteEnvoieMessageOriginal) evenementImminent;
				evt.getHote().envoiMessageOriginal();
			} else if (evenementImminent instanceof AgentRecoitMessage) {
				// réception d'un message par un agent
				AgentRecoitMessage evt = (AgentRecoitMessage) evenementImminent;
				evt.getAgent().recoitMessage(evt.getMessage());
			} else if (evenementImminent instanceof AgentFinTraitementMessage) {
				// fin de traitement d'un message par un agent
				AgentFinTraitementMessage evt = (AgentFinTraitementMessage) evenementImminent;
				evt.getAgent().finitTraiterMessage(evt.getMessage());
			} else if (evenementImminent instanceof HoteTimeoutReceptionAccuse) {
				// timeout réception d'un accusé
				HoteTimeoutReceptionAccuse evt = (HoteTimeoutReceptionAccuse) evenementImminent;
				evt.getHote().timeoutReceptionAccuse(evt.getMessage());
			} else if (evenementImminent instanceof HoteRecoitMessage) {
				// réception d'un message par un hôte
				HoteRecoitMessage evt = (HoteRecoitMessage) evenementImminent;
				evt.getHote().recoitMessage(evt.getMessage());
			} else if (evenementImminent instanceof HoteFinTraitementMessage) {
				// fin de traitement d'un message par un hote
				HoteFinTraitementMessage evt = (HoteFinTraitementMessage) evenementImminent;
				evt.getHote().finitTraiterMessage(evt.getMessage());
			} else if (evenementImminent instanceof AffichageStatistiques) {
				// on calcule affiche les statistiques actuelles
				calculerEtAfficherResultats();
			} else if (evenementImminent instanceof AgentEnvoieInfosRoutage) {
				AgentEnvoieInfosRoutage evt = (AgentEnvoieInfosRoutage) evenementImminent;
				evt.getAgent().envoyerInfosRoutage();
			} else if (evenementImminent instanceof AgentRecoitInfosRoutage) {
				AgentRecoitInfosRoutage evt = (AgentRecoitInfosRoutage) evenementImminent;
				evt.getAgent().recoitInfosRoutage(evt.getInfosRoutage());
			} else if (evenementImminent instanceof FinDeSimulation) {
				// on met à jour la statistique du taux d'utilisation du buffer
				// de chaque agent
				// une derniere fois pour que le résultat final soit correct
				for (Agent a : agents) {
					a.mettreAJourStatTauxUtilisationBuffer();
				}
				// on vide la FEL, ce qui provoque la sortie de cette boucle
				getFutureEventList().reset();
				LOGGER.info("Fin de la simulation");
			}
		}
	}

	/**
	 * Récupérer un agent aléatoire pouvant être n'importe lequel sauf celui
	 * fourni en argument.
	 * 
	 * @param exception
	 *            le seul agent ne pouvant pas être retourné
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
	 * Remise à zéro des tables de routage des agents (état de départ).
	 * Configure les tables en fonction de la figure 2 de l'énoncé. Si le
	 * distance vector est activé, seuls les routes vers les voisins directs
	 * sont ajoutées. Si le DV est désactivé, toutes les routes sont ajoutées
	 * (chemin le plus court)
	 * 
	 */
	private void reinitialiserTablesRoutageAgents() {
		// tout d'abord on remet les tables de routage à zéro
		for (Agent agent : agents) {
			agent.getTableDeRoutage().reset();
		}

		// initialisation des tables de routage
		// on ajoute d'abord les voisins
		agent1.getTableDeRoutage().ajouterVoisin(agent1, 0);
		agent1.getTableDeRoutage().ajouterVoisin(agent2, 10);
		agent1.getTableDeRoutage().ajouterVoisin(agent3, 20);

		agent2.getTableDeRoutage().ajouterVoisin(agent2, 0);
		agent2.getTableDeRoutage().ajouterVoisin(agent1, 10);
		agent2.getTableDeRoutage().ajouterVoisin(agent4, 10);
		agent2.getTableDeRoutage().ajouterVoisin(agent7, 30);

		agent3.getTableDeRoutage().ajouterVoisin(agent3, 0);
		agent3.getTableDeRoutage().ajouterVoisin(agent1, 20);
		agent3.getTableDeRoutage().ajouterVoisin(agent4, 10);

		agent4.getTableDeRoutage().ajouterVoisin(agent4, 0);
		agent4.getTableDeRoutage().ajouterVoisin(agent2, 10);
		agent4.getTableDeRoutage().ajouterVoisin(agent3, 10);
		agent4.getTableDeRoutage().ajouterVoisin(agent5, 20);

		agent5.getTableDeRoutage().ajouterVoisin(agent5, 0);
		agent5.getTableDeRoutage().ajouterVoisin(agent4, 20);
		agent5.getTableDeRoutage().ajouterVoisin(agent6, 40);
		agent5.getTableDeRoutage().ajouterVoisin(agent7, 10);

		agent6.getTableDeRoutage().ajouterVoisin(agent6, 0);
		agent6.getTableDeRoutage().ajouterVoisin(agent5, 40);
		agent6.getTableDeRoutage().ajouterVoisin(agent7, 20);

		agent7.getTableDeRoutage().ajouterVoisin(agent7, 0);
		agent7.getTableDeRoutage().ajouterVoisin(agent2, 30);
		agent7.getTableDeRoutage().ajouterVoisin(agent5, 10);
		agent7.getTableDeRoutage().ajouterVoisin(agent6, 20);

		// pour aller de l'agent A à l'agent B, on passe par le voisin X avec un
		// coût de Y: agentA...addRoute(agentB,agentX,Y);
		if (getConfiguration().getConfigurationSimulationReseau()
				.isDistanceVectorActive()) {
			// FIXME v2.0 ok?
			// Pour l'instant on commence uniquement avec le voisin lui même
			// dans sa table de routage (avec un cout et une distance nulle)
			agent1.getTableDeRoutage().ajouterRoute(agent1, agent1, 0, 0);
			agent2.getTableDeRoutage().ajouterRoute(agent2, agent2, 0, 0);
			agent3.getTableDeRoutage().ajouterRoute(agent3, agent3, 0, 0);
			agent4.getTableDeRoutage().ajouterRoute(agent4, agent4, 0, 0);
			agent5.getTableDeRoutage().ajouterRoute(agent5, agent5, 0, 0);
			agent6.getTableDeRoutage().ajouterRoute(agent6, agent6, 0, 0);
			agent7.getTableDeRoutage().ajouterRoute(agent7, agent7, 0, 0);

			// agent1.getTableDeRoutage().ajouterRoute(agent2,agent2,10);
			// agent1.getTableDeRoutage().ajouterRoute(agent3,agent3,20);
			//			
			// agent2.getTableDeRoutage().ajouterRoute(agent1,agent1,10);
			// agent2.getTableDeRoutage().ajouterRoute(agent4,agent4,10);
			// agent2.getTableDeRoutage().ajouterRoute(agent7,agent7,30);
			//			
			// agent3.getTableDeRoutage().ajouterRoute(agent1, agent1, 20);
			// agent3.getTableDeRoutage().ajouterRoute(agent4,agent4,10);
			//			
			// agent4.getTableDeRoutage().ajouterRoute(agent2,agent2,10);
			// agent4.getTableDeRoutage().ajouterRoute(agent3,agent3,10);
			// agent4.getTableDeRoutage().ajouterRoute(agent5,agent5,20);
			//			
			// agent5.getTableDeRoutage().ajouterRoute(agent4,agent4,20);
			// agent5.getTableDeRoutage().ajouterRoute(agent6,agent6,40);
			// agent5.getTableDeRoutage().ajouterRoute(agent7,agent7,10);
			//			
			// agent6.getTableDeRoutage().ajouterRoute(agent5,agent5,40);
			// agent6.getTableDeRoutage().ajouterRoute(agent7,agent7,20);
			//			
			// agent7.getTableDeRoutage().ajouterRoute(agent2,agent2,30);
			// agent7.getTableDeRoutage().ajouterRoute(agent5,agent5,10);
			// agent7.getTableDeRoutage().ajouterRoute(agent6,agent6,20);

		} else {
			// Si le distance vector n'est pas activé,
			// on ajoute toutes les routes aux agents (table de routage fixe)
			// Puisque le distance vector n'est pas activé, on met le coût
			// des routes à 0 (il ne sera pas utilisé)

			// routes de l'agent 1
			agent1.getTableDeRoutage().ajouterRoute(agent2, agent2, 10, 0);
			agent1.getTableDeRoutage().ajouterRoute(agent3, agent3, 20, 0);
			agent1.getTableDeRoutage().ajouterRoute(agent4, agent2, 10, 0);
			agent1.getTableDeRoutage().ajouterRoute(agent5, agent2, 10, 0);
			agent1.getTableDeRoutage().ajouterRoute(agent6, agent2, 10, 0);
			agent1.getTableDeRoutage().ajouterRoute(agent7, agent2, 10, 0);
			// routes de l'agent 2
			agent2.getTableDeRoutage().ajouterRoute(agent1, agent1, 10, 0);
			agent2.getTableDeRoutage().ajouterRoute(agent3, agent4, 10, 0);
			agent2.getTableDeRoutage().ajouterRoute(agent4, agent4, 10, 0);
			agent2.getTableDeRoutage().ajouterRoute(agent5, agent4, 10, 0);
			agent2.getTableDeRoutage().ajouterRoute(agent6, agent7, 30, 0);
			agent2.getTableDeRoutage().ajouterRoute(agent7, agent7, 30, 0);
			// routes de l'agent 3
			agent3.getTableDeRoutage().ajouterRoute(agent1, agent1, 20, 0);
			agent3.getTableDeRoutage().ajouterRoute(agent2, agent4, 10, 0);
			agent3.getTableDeRoutage().ajouterRoute(agent4, agent4, 10, 0);
			agent3.getTableDeRoutage().ajouterRoute(agent5, agent4, 10, 0);
			agent3.getTableDeRoutage().ajouterRoute(agent6, agent4, 10, 0);
			agent3.getTableDeRoutage().ajouterRoute(agent7, agent4, 10, 0);
			// routes de l'agent 4
			agent4.getTableDeRoutage().ajouterRoute(agent1, agent2, 10, 0);
			agent4.getTableDeRoutage().ajouterRoute(agent2, agent2, 10, 0);
			agent4.getTableDeRoutage().ajouterRoute(agent3, agent3, 10, 0);
			agent4.getTableDeRoutage().ajouterRoute(agent5, agent5, 20, 0);
			agent4.getTableDeRoutage().ajouterRoute(agent6, agent2, 10, 0);
			agent4.getTableDeRoutage().ajouterRoute(agent7, agent5, 20, 0);
			// routes de l'agent 5
			agent5.getTableDeRoutage().ajouterRoute(agent1, agent4, 20, 0);
			agent5.getTableDeRoutage().ajouterRoute(agent2, agent4, 20, 0);
			agent5.getTableDeRoutage().ajouterRoute(agent3, agent4, 20, 0);
			agent5.getTableDeRoutage().ajouterRoute(agent4, agent4, 20, 0);
			agent5.getTableDeRoutage().ajouterRoute(agent6, agent7, 10, 0);
			agent5.getTableDeRoutage().ajouterRoute(agent7, agent7, 10, 0);
			// routes de l'agent 6
			agent6.getTableDeRoutage().ajouterRoute(agent1, agent7, 20, 0);
			agent6.getTableDeRoutage().ajouterRoute(agent2, agent7, 20, 0);
			agent6.getTableDeRoutage().ajouterRoute(agent3, agent7, 20, 0);
			agent6.getTableDeRoutage().ajouterRoute(agent4, agent7, 20, 0);
			agent6.getTableDeRoutage().ajouterRoute(agent5, agent7, 20, 0);
			agent6.getTableDeRoutage().ajouterRoute(agent7, agent7, 20, 0);
			// routes de l'agent 7
			agent7.getTableDeRoutage().ajouterRoute(agent1, agent2, 30, 0);
			agent7.getTableDeRoutage().ajouterRoute(agent2, agent2, 30, 0);
			agent7.getTableDeRoutage().ajouterRoute(agent3, agent5, 10, 0);
			agent7.getTableDeRoutage().ajouterRoute(agent4, agent5, 10, 0);
			agent7.getTableDeRoutage().ajouterRoute(agent5, agent5, 10, 0);
			agent7.getTableDeRoutage().ajouterRoute(agent6, agent6, 20, 0);
		}
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
		reinitialiserTablesRoutageAgents();
		// on ajoute les agents à la liste
		// (pour simplifier certains traitements
		agents.clear();
		agents.add(agent1);
		agents.add(agent2);
		agents.add(agent3);
		agents.add(agent4);
		agents.add(agent5);
		agents.add(agent6);
		agents.add(agent7);
		if (getConfiguration().getConfigurationSimulationReseau()
				.isDistanceVectorActive()) {
			// Si le distance vector est activé, on planifie les évènements
			// réguliers d'envoi d'informations de routage
			
			
			long tempsTmp = 0L;
			while (tempsTmp <= getConfiguration()
					.getConfigurationSimulationReseau().getDuree()) {
				// pour l'instant on le génère pour tous
				for (Agent agent : agents) {
					AgentEnvoieInfosRoutage evtEnvoiInfosRoutage = new AgentEnvoieInfosRoutage(
							agent, tempsTmp);
					getFutureEventList().planifierEvenement(
							evtEnvoiInfosRoutage);
				}
				tempsTmp += getConfiguration().getConfigurationAgents()
						.getTempsInterEnvoisInfosRoutage();
			}
		}
		// Génération des premiers évènements de la simulation
		// avant d'ajouter quoi que ce soit sur la FEL on ajoute
		// à l'horloge la durée d'initialisation de la simulation
		// Le temps d'initialisation doit être suffisamment long pour s'assurer
		// qu'à la fin de la période d'initialisation, le distance vector soit
		// déjà
		// stabilisé.
		ajouterTempsHorloge(getConfiguration()
				.getConfigurationSimulationReseau().getDureeInitialisation());
		// on prévoit des évènements spéciaux pour afficher
		// les statistiques à des moments bien déterminés pendant la simulation
		// (par exemple tous les 10% de la simulation)
		float periodiciteAffichageStats = getConfiguration()
				.getConfigurationSimulationReseau()
				.getPeriodiciteAffichageStatistiques();
		long tempsEntreAffichageStats = Math.round(getConfiguration()
				.getConfigurationSimulationReseau().getDuree()
				* periodiciteAffichageStats);
		if (tempsEntreAffichageStats == 0) {
			// l'unité de temps minimale
			tempsEntreAffichageStats = 1;
		}
		long tempsTemp = 0;
		while (tempsTemp <= getConfiguration()
				.getConfigurationSimulationReseau().getDuree()) {
			AffichageStatistiques evtAffichageStats = new AffichageStatistiques(
					getHorloge() + tempsTemp);
			getFutureEventList().planifierEvenement(evtAffichageStats);
			tempsTemp += tempsEntreAffichageStats;
		}
		// premiers évènements d'envoi par les hôtes
		for (Agent agent : agents) {
			for (Hote h : agent.getHotes()) {
				HoteEnvoieMessageOriginal evt = h
						.genererEvenementHoteEnvoieMessageOriginal();
				getFutureEventList().planifierEvenement(evt);
			}
		}
		// On ajoute directement l'évènement de fin de simulation à la FEL
		// la fin doit avoir lieu après la durée d'initialisation + la durée de
		// la simulation
		long dureeSimulation = getConfiguration()
				.getConfigurationSimulationReseau().getDuree();
		long dureeInitialisation = getConfiguration()
				.getConfigurationSimulationReseau().getDureeInitialisation();
		FinDeSimulation finDeSimulation = new FinDeSimulation(
				dureeInitialisation + dureeSimulation);
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
