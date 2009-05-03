package be.simulation.entites;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import be.simulation.evenements.AgentEnvoieInfosRoutage;
import be.simulation.evenements.AgentFinTraitementMessage;
import be.simulation.evenements.AgentRecoitInfosRoutage;
import be.simulation.evenements.AgentRecoitMessage;
import be.simulation.evenements.HoteRecoitMessage;
import be.simulation.messages.Message;
import be.simulation.messages.routage.InfosRoutage;
import be.simulation.messages.utilitaires.MessageEnAttente;
import be.simulation.routage.Route;
import be.simulation.routage.TableDeRoutage;
import be.simulation.routage.Voisin;

/**
 * Agent du système (= serveur).
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class Agent extends AbstractEntiteSimulationReseau {
	// variables tenant l'information globale (pour tous les agents)
	public static int			TOTAL_MESSAGES_EN_COURS_TRAITEMENT					=
																							0;
	public static int			TOTAL_MESSAGES_PERDUS_BRUTALEMENT					=
																							0;
	public static int			TOTAL_MESSAGES_PERDUS_BUFFER_PLEIN					=
																							0;
	public static int			TOTAL_MESSAGES_RECUS								=
																							0;
	public static int			TOTAL_SOMME_NIVEAUX_OCCUPATION_BUFFERS				=
																							0;
	/**
	 * Dernier temps de simulation ou le taux d'utilisation du buffer a été mis
	 * à jour.
	 */
	private long				dernierTempsMiseAJourSommeNiveauxOccupationBuffer	=
																							0;
	/**
	 * PRNG utilisé pour choisir un hôte au hasard parmi les hôtes connectés à
	 * cet agent.
	 */
	private final Random		generateurChoixHote									=
																							new Random();
	/**
	 * PRNG utilisé pour déterminer si un message reçu est perdu ou non (suivant
	 * la configuration).
	 */
	private final Random		generateurMessagesPerdus							=
																							new Random();
	/**
	 * Hôtes connectés à cet agent.
	 */
	private final List<Hote>	hotes												=
																							new ArrayList<Hote>();
	/**
	 * Le nombre de messages en cours de traitement.
	 */
	private int					messagesEnCoursTraitement							=
																							0;
	/**
	 * Le nombre de messages perdus brutalement.
	 */
	private int					messagesPerdusBrutalement							=
																							0;
	/**
	 * Le nombre de messages perdus car le buffer était plein.
	 */
	private int					messagesPerdusBufferPlein							=
																							0;
	/**
	 * Le nombre de messages reçus.
	 */
	private int					messagesRecus										=
																							0;
	/**
	 * La somme des utilisations du buffer.
	 */
	private long				sommeNiveauOccupationBuffer							=
																							0L;
	/**
	 * Les informations de routage dont dispose cet agent (lui permet de savoir
	 * vers où forwarder les messages).
	 */
	private TableDeRoutage		tableDeRoutage;
	/**
	 * Le dernier temps où on a envoyé les infos de routage.
	 */
	// TODO ajouter à l'UML
	private final long			dernierTempsEnvoiInfosRoutage						=
																							0;

	/**
	 * Temps minimal entre deux envois des infos de routage.
	 */
	// TODO ajouter à l'UML
	private final long			deltaEntreEnvoisInfosRoutage						=
																							16;
	
	/**
	 * Crée un nouvel agent.
	 */
	public Agent() {
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		tableDeRoutage =
				new TableDeRoutage(this, getConfiguration()
						.getConfigurationSimulationReseau()
						.isDistanceVectorActive());
	}



	/**
	 * Méthode appelée quand cet agent envoie ses informations de routage à ses
	 * voisins.
	 */
	public void envoieInfosRoutage(boolean initialisation) {
		// TODO ajouter à l'UML!
		// on envoie notre distance vector
		// a chaque voisin
		for (Voisin voisin : tableDeRoutage.getVoisins()) {
			if(this.equals(voisin.getAgent())){
				// pas la peine de s'envoyer les informations
				continue;
			}
			
			// on ne va donner aux voisins que les meilleures routes
			// qu'on connaît pour chaque destination
			// (notre distance vector donc)
			// TODO implémenter poisoned reverse?
			InfosRoutage infosRoutage =
	 new InfosRoutage(this, voisin.getAgent(), tableDeRoutage
							.getDistanceVectorLocal());
			AgentRecoitInfosRoutage evtAgentRecoitInfosRoutage =
					new AgentRecoitInfosRoutage(infosRoutage,
							voisin.getAgent(), getSimulation().getHorloge()
									+ voisin.getDistance(), initialisation);
			getSimulation().getFutureEventList().planifierEvenement(
					evtAgentRecoitInfosRoutage);
		}
		// on génère le prochain évènement d'envoi
		long prochainTempsEnvoi =
				getSimulation().getHorloge()
						+ getConfiguration().getConfigurationAgents()
								.getTempsInterEnvoisInfosRoutage();
		AgentEnvoieInfosRoutage evtEnvoiInfosRoutage =
				new AgentEnvoieInfosRoutage(this, prochainTempsEnvoi,
						initialisation);
		getSimulation().getFutureEventList().planifierEvenement(
				evtEnvoiInfosRoutage);
	}



	/**
	 * Détermine si on perd le message ou non (sur base de la configuration) en
	 * utilisant un PRNG.
	 * 
	 * @return vrai si le message est perdu
	 */
	private boolean estPerdu() {
		int randomDigits =
				Math.round(getConfiguration().getConfigurationAgents()
						.getTauxPerteBrutale() * 100);
		boolean retVal = false;
		if (generateurMessagesPerdus.nextInt(100) + 1 <= randomDigits) {
			retVal = true;
		}
		return retVal;
	}



	/**
	 * Appelé quand cet agent termine de traiter un message.
	 * 
	 * @param message
	 *        le message que cet agent finit de traiter
	 */
	public void finitTraiterMessage(final Message message) {
		if (message == null) {
			throw new IllegalArgumentException(
					"Le message que l'agent termine de traiter ne peut pas être null!");
		}
		// on met à jour la statistique du taux d'utilisation du buffer
		mettreAJourStatTauxUtilisationBuffer();
		// vérification du destinataire final de ce message
		if (this.equals(message.getDestination().getAgent())) {
			// message à destination d'un hôte de cet agent
			// donc on peut le lui remettre
			HoteRecoitMessage evtHoteRecoitMessage =
					new HoteRecoitMessage(message, message.getDestination(),
							getSimulation().getHorloge()
									+ getConfiguration()
											.getConfigurationSimulationReseau()
											.getDelaiEntreEntites());
			// ajout de l'évènement de réception à la FEL
			getSimulation().getFutureEventList().planifierEvenement(
					evtHoteRecoitMessage);
		} else {
			// le message est à destination d'un hôte connecté à un autre agent
			// on cherche où forwarder le message
			// TODO màj UML
			Voisin voisin =
					tableDeRoutage.trouverMeilleurVoisin(message
							.getDestination());
			// on génère l'évènement de réception par le voisin correspondant à
			// cette route
			AgentRecoitMessage evtReceptionAgent =
					new AgentRecoitMessage(message, voisin.getAgent(),
							getSimulation().getHorloge() + voisin.getDistance());
			// on l'ajoute à la FEL
			getSimulation().getFutureEventList().planifierEvenement(
					evtReceptionAgent);
		}
		// on vérifie le contenu du buffer
		if (getBuffer().isEmpty()) {
			// plus rien à traiter, on décrémente le nombre de messages en cours
			// de traitement
			messagesEnCoursTraitement--;
			// on incrémente aussi l'info globale (pour tous les agents)
			TOTAL_MESSAGES_EN_COURS_TRAITEMENT--;
		} else {
			// puisqu'il y a encore des messages en attente, on en prend un et
			// on commence à le traiter tout de suite
			MessageEnAttente messageEnAttente = getBuffer().poll();
			Message messageATraiter = messageEnAttente.getMessage();
			// on met à jour le temps que ce message à passé dans des buffers
			messageATraiter.augmenterTempsPasseDansBuffers(getSimulation()
					.getHorloge()
					- messageEnAttente.getTempsArrivee());
			// on génère l'évènement de fin de traitement pour ce message à
			// traiter
			AgentFinTraitementMessage evtAgentFinTraitementMessage =
					genererEvenementAgentFinTraitementMessage(messageATraiter);
			// on l'ajoute sur la FEL
			getSimulation().getFutureEventList().planifierEvenement(
					evtAgentFinTraitementMessage);
		}
	}



	/**
	 * Génère et ajoute à la FEL un évènement de type AgentFinTraitementMessage
	 * 
	 * @param message
	 *        le message qu'on finira de traiter
	 */
	private AgentFinTraitementMessage genererEvenementAgentFinTraitementMessage(
			final Message message) {
		return new AgentFinTraitementMessage(message, this, getSimulation()
				.getHorloge() + 1);
	}



	/**
	 * Récupérer un hôte aléatoire de cet agent.
	 * 
	 * @return un hôte aléatoire de cet agent
	 */
	public Hote getHoteAleatoire() {
		// choix d'une position entre 0 et nombre d'hôtes - 1 (Random commence à
		// 0)
		int position = generateurChoixHote.nextInt(hotes.size());
		return hotes.get(position);
	}



	/**
	 * Récupérer un hôte aléatoire de cet agent pouvant être n'importe quel hôte
	 * sauf celui fourni en argument.
	 * 
	 * @param exception
	 *        le seul hôte ne pouvant pas être retourné
	 * @return un hôte aléatoire autre que celui donné en argument
	 */
	public Hote getHoteAleatoire(final Hote exception) {
		if (exception == null) {
			throw new IllegalArgumentException(
					"L'hôte exclus (l'exception) ne peut pas être null!");
		}
		Hote retVal = null;
		do {
			retVal = getHoteAleatoire();
		} while (retVal == null || exception.equals(retVal));
		return retVal;
	}



	/**
	 * Retourne les hôtes associés à cet agent.
	 * 
	 * @return les hôtes associés à cet agent
	 */
	public List<Hote> getHotes() {
		return hotes;
	}



	/**
	 * Récupérer le nombre de messages perdus brutalement.
	 * 
	 * @return le nombre de messages perdus brutalement.
	 */
	public int getMessagesPerdusBrutalement() {
		return messagesPerdusBrutalement;
	}



	/**
	 * Récupérer le nombre de messages perdus car le buffer était plein.
	 * 
	 * @return le nombre de messages perdus car le buffer était plein.
	 */
	public int getMessagesPerdusBufferPlein() {
		return messagesPerdusBufferPlein;
	}



	/**
	 * Récupérer le nombre de messages reçus.
	 * 
	 * @return le nombre de messages reçus.
	 */
	public int getMessagesRecus() {
		return messagesRecus;
	}



	/**
	 * Récupérer la somme des niveaux d'occupation du buffer.
	 * 
	 * @return la somme des niveaux d'occupation d'utilisation du buffer.
	 */
	public double getSommeNiveauxOccupationBuffer() {
		return sommeNiveauOccupationBuffer;
	}



	/**
	 * Récupérer les infos de routage de cet agent.
	 * 
	 * @return les infos de routage de cet agent
	 */
	public TableDeRoutage getTableDeRoutage() {
		return tableDeRoutage;
	}



	/**
	 * On crée les hôtes connectés à cet agent en fonction de la configuration.
	 */
	private void initialiserHotes() {
		LOGGER.trace("Initialisation des hôtes de l'agent");
		this.hotes.clear();
		for (int i = 1; i <= getConfiguration().getConfigurationAgents()
				.getNombreHotes(); i++) {
			Hote hote = (Hote) getApplicationContext().getBean("hote");
			hote.setAgent(this);
			hotes.add(hote);
		}
	}



	/**
	 * Met à jour la statistique concernant le taux d'utilisation du buffer.
	 */
	public void mettreAJourStatTauxUtilisationBuffer() {
		long tailleMaxBuffer =
				getConfiguration().getConfigurationAgents()
						.getTailleMaxBuffer();
		long differenceTempsActuelEtTempsDerniereMaj =
				getSimulation().getHorloge()
						- dernierTempsMiseAJourSommeNiveauxOccupationBuffer;
		// on ne le fait que si le buffer n'est pas illimité
		// et si la différence de temps entre la dernière mise à jour et le
		// temps actuel n'est pas =0
		if (differenceTempsActuelEtTempsDerniereMaj > 0
				&& tailleMaxBuffer < Long.MAX_VALUE) {
			// on l'incrémente de n * le nombre d'éléments actuellement dans le
			// buffer (pour représenter le fait que le buffer
			// a été à ce niveau d'occupation pendant n unités de temps
			long niveauOccupation =
					getBuffer().size()
							* differenceTempsActuelEtTempsDerniereMaj;
			sommeNiveauOccupationBuffer += niveauOccupation;
			// on incrémente aussi l'information globale (pour tous les agents)
			TOTAL_SOMME_NIVEAUX_OCCUPATION_BUFFERS += niveauOccupation;
		}
		dernierTempsMiseAJourSommeNiveauxOccupationBuffer =
				getSimulation().getHorloge();
	}



	/**
	 * Méthode appelée quand cet agent reçoit des informations de routage.
	 * 
	 * @param infosRoutage
	 *        les informations de routage reçues
	 */
	public void recoitInfosRoutage(final InfosRoutage infosRoutage,
			boolean initialisation) {
		// TODO v2.0 ajouter à l'UML (détailler la méthode replanifier...)
		// ce message est prioritaire et ne se préoccupe pas de
		// l'occupation de l'agent
		// on essaie de mettre à jour la table de routage avec les infos reçues
		boolean tableModifiee =
				tableDeRoutage.mettreAJour(infosRoutage, initialisation);
		// si la table de routage à été modifiée, alors on doit renvoyer
		// les nouvelles infos de routage aux voisins
		if (tableModifiee) {
			replanifierEvenementEnvoiInfosRoutage(initialisation);
		}
	}
	
	/**
	 * Les circonstances obligent l'agent à prévenir ses voisins de changements dans son distance vector.
	 */
	private void replanifierEvenementEnvoiInfosRoutage(boolean initialisation) {
		// on annule l'évènement d'envoi initialement prévu
		AgentEnvoieInfosRoutage evtASupprimer =
				getSimulation().getFutureEventList()
						.trouverEvenementEnvoiInfosRoutagePourAgent(this);
		if (evtASupprimer == null) {
			throw new IllegalStateException(
					"L'évènement d'envoi d'infos de routage à supprimer n'a pas été trouvé, ceci ne devrait pas arriver!");
		} else {
			getSimulation().getFutureEventList().supprimer(evtASupprimer);
		}
		// on en génère un nouveau (envoi immédiat)
		AgentEnvoieInfosRoutage evtAgentEnvoieInfosRoutage =
				new AgentEnvoieInfosRoutage(this, getSimulation()
						.getHorloge(),
						initialisation);
		getSimulation().getFutureEventList().planifierEvenement(
				evtAgentEnvoieInfosRoutage);
	}



	/**
	 * Appelé quand cet agent reçoit un message.
	 * 
	 * @param message
	 *        le message qu'il reçoit
	 */
	public void recoitMessage(final Message message) {
		if (message == null) {
			throw new IllegalArgumentException(
					"Le message ne peut pas être null!");
		}
		// on incrémente le nombre de messages reçus
		messagesRecus++;
		// on incrémente aussi le nombre de messages reçus par les agents en
		// général
		TOTAL_MESSAGES_RECUS++;
		// on détermine si ce message est perdu ou non
		// (utilisation d'une variable aléatoire)
		if (estPerdu()) {
			LOGGER.trace("L'agent " + getNumero()
					+ " perd brutalement un message au temps "
					+ getSimulation().getHorloge());
			// on incrémente le compteur de messages perdus brutalement
			messagesPerdusBrutalement++;
			// on incrémente le nombre de messages perdus brutalement par tous
			// les agents
			TOTAL_MESSAGES_PERDUS_BRUTALEMENT++;
		} else {
			// est-ce qu'on peut encore traiter un message?
			if (messagesEnCoursTraitement < getConfiguration()
					.getConfigurationAgents()
					.getNombreMaxTraitementsSimultanes()) {
				// on peut traiter le message directement donc on génère
				// l'évènement de fin de traitement
				AgentFinTraitementMessage evtAgentFinTraitementMessage =
						genererEvenementAgentFinTraitementMessage(message);
				// on l'ajoute sur la FEL
				getSimulation().getFutureEventList().planifierEvenement(
						evtAgentFinTraitementMessage);
				// on incrémente le compteur de messages en cours de traitement
				messagesEnCoursTraitement++;
				// on incrémente aussi l'info globale (pour tous les agents)
				TOTAL_MESSAGES_EN_COURS_TRAITEMENT++;
			} else {
				boolean bufferInfini =
					getConfiguration().getConfigurationAgents()
							.getTailleMaxBuffer() == Long.MAX_VALUE;
				//FIXME màj l'uml
				if (getConfiguration().getConfigurationSimulationReseau()
						.isDistanceVectorActive() && !bufferInfini) {
					// si le buffer n'est pas infini, on vérifie son état
					// pour déterminer s'il faut prévenir que
					// l'agent est surchargé si nécessaire
					//FIXME màj l'uml
					//FIXME v2.0 décider si on augmente de 20-50-100
					// ou si on place à + infini
					
					
					double occupationActuelle = (double)getBuffer().size() / (double) getConfiguration().getConfigurationAgents().getTailleMaxBuffer();
					int modificationCout = 10;
					

					boolean envoiInfosNecessaire = false;
					// TODO ajouter le check pour la différence de temps
					// dernierTempsEnvoiInfosRoutage
					// deltaEntreEnvoisInfosRoutage
					
					if(occupationActuelle >= 0.8){
						for (Route route : tableDeRoutage
								.getDistanceVectorComplet().get(this)) {
							// on va tout augmenter sauf la route locale
							// puisque ça n'a pas de sens
							if (route.getVoisin().getAgent().equals(this)) {
								continue;
							}
							int coutActuel = route.getCout();
							if (coutActuel < TableDeRoutage.INFINI) {
								envoiInfosNecessaire =
										envoiInfosNecessaire && true;
								route.setCout(coutActuel + modificationCout);
							}
						}
					}
					
					// else if (occupationActuelle <= 0.2) {
					// if(coutActuel > 0){
					// routeLocale.setCout(coutActuel - modificationCout);
					// envoiInfosNecessaire = true;
					// }
					// }
					
					// si un changement important a eu lieu, on doit prévenir les voisins
					// la méthode appelée supprime l'évènement d'envoi initialement prévu
					// et en génère un autre immédiat
					if(envoiInfosNecessaire){
						replanifierEvenementEnvoiInfosRoutage(false);
					}
				}
				// on met à jour la statistique du taux d'utilisation du buffer
				mettreAJourStatTauxUtilisationBuffer();
				// on ne peut plus traiter de message pour l'instant donc on
				// doit le mettre en attente on essaie 
				// donc de le placer dans le buffer si possible
				boolean bufferRempli =
						getBuffer().size() == getConfiguration()
								.getConfigurationAgents().getTailleMaxBuffer();
				// si le buffer n'est pas rempli ou est infini, pas de problème
				if (!bufferRempli || bufferInfini) {
					// le buffer n'est pas plein alors on peut y placer le
					// message.
					// on enregistre le temps de simulation auquel on l'y a
					// placé
					getBuffer().add(
							new MessageEnAttente(message, getSimulation()
									.getHorloge()));
				} else {
					// le buffer est plein donc le message est perdu
					messagesPerdusBufferPlein++;
					// on incrémente le nombre global de messages perdus à cause
					// d'un buffer plein (pour tous les agents)
					TOTAL_MESSAGES_PERDUS_BUFFER_PLEIN++;
				}
			}
		}
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		LOGGER.trace("Réinitialisation de l'agent " + getNumero());
		super.reset();
		sommeNiveauOccupationBuffer = 0L;
		messagesPerdusBrutalement = 0;
		dernierTempsMiseAJourSommeNiveauxOccupationBuffer = 0;
		messagesRecus = 0;
		messagesPerdusBufferPlein = 0;
		messagesEnCoursTraitement = 0;
		TOTAL_MESSAGES_RECUS = 0;
		TOTAL_MESSAGES_PERDUS_BRUTALEMENT = 0;
		TOTAL_MESSAGES_PERDUS_BUFFER_PLEIN = 0;
		TOTAL_MESSAGES_EN_COURS_TRAITEMENT = 0;
		TOTAL_SOMME_NIVEAUX_OCCUPATION_BUFFERS = 0;
		// on réinitialise les hôtes (le nombre d'hôtes par agent dans la
		// configuration peut avoir changé)
		initialiserHotes();
	}



	/**
	 * Définir le numéro de cet agent (réinitialise également ses hôtes)
	 * 
	 * @param numeroAgent
	 *        le numéro de cet agent
	 */
	@Override
	public void setNumero(final long numeroAgent) {
		super.setNumero(numeroAgent);
		initialiserHotes();
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Agent " + getNumero();
	}
}
