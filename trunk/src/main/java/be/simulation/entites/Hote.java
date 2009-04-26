package be.simulation.entites;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import be.simulation.evenements.AgentRecoitMessage;
import be.simulation.evenements.HoteEnvoieMessageOriginal;
import be.simulation.evenements.HoteFinTraitementMessage;
import be.simulation.evenements.HoteTimeoutReceptionAccuse;
import be.simulation.messages.AccuseReception;
import be.simulation.messages.Message;
import be.simulation.messages.MessageSimple;
import be.simulation.messages.utilitaires.MessageEnAttente;
import be.simulation.messages.utilitaires.MessageTempsMax;

/**
 * Hote du système, relié à un et un seul agent (lien statique).
 *
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class Hote extends AbstractEntiteSimulationReseau {
	// variables tenant l'information globale (pour tous les hôtes)
	public static int TOTAL_ACCUSES_RECUS = 0;
	public static int TOTAL_MESSAGES_ENVOYES = 0;
	public static int TOTAL_MESSAGES_REEXPEDIES = 0;
	public static int TOTAL_TIMEOUTS_TROP_COURTS = 0;
	public static int TOTAL_MESSAGES_EN_COURS_TRAITEMENT = 0;
	public static MessageSimple MESSAGE_LE_PLUS_REEMIS = MessageSimple.creerFauxMessage();
	public static long TOTAL_TEMPS_VOYAGE_MESSAGES = 0L;
	public static long TOTAL_TEMPS_BUFFERS = 0L;
	public static MessageTempsMax	MESSAGE_TEMPS_MAX =	new MessageTempsMax();
	
	
	
	
	/**
	 * Utilisé pour attribuer un numéro unique à chaque hôte.
	 */
	public static long				TOTAL_HOTES					= 0;
	/**
	 * Liste qui nous permet de voir si on a déjà reçu un timeout pour un
	 * message original donné.
	 */
	private final List<Integer>		accusesNonRecus				=
																		new ArrayList<Integer>();
	/**
	 * Nombre d'accusés de réception reçus.
	 */
	private int						accusesReceptionRecus		= 0;
	/**
	 * Agent auquel cet hote est relié (pour pouvoir communiquer avec lui).
	 */
	private Agent					agent;
	/**
	 * PRNG utilisé pour générer les temps d'envoi.
	 */
	private final Random			generateurTempsEnvoi		= new Random();
	/**
	 * PRNG utilisé pour déterminer si un nouveau message doit être ou non à
	 * destination d'un hôte d'un autre agent.
	 */
	private final Random			generateurTypeDestination	= new Random();
	/**
	 * Permet de placer un identifiant unique sur chaque message que cet hôte
	 * envoie.
	 */
	private int						identifiantMessages			= 0;
	/**
	 * Message qu'on a réémis le plus de fois (par défaut on y met un message
	 * bidon)
	 */
	private MessageSimple			messageLePlusReemis			= null;
	/**
	 * Le nombre de messages en cours de traitement.
	 */
	private int						messagesEnCoursTraitement	= 0;
	/**
	 * Nombre de messages envoyés.
	 */
	private int						messagesEnvoyes				= 0;
	/**
	 * Nombre de messages réexpédiés.
	 */
	private int						messagesReexpedies			= 0;
	/**
	 * Contient le message ayant mis le plus de temps à effectuer son trajet
	 * complet.
	 */
	private final MessageTempsMax	messageTempsMax				=
																		new MessageTempsMax();
	/**
	 * Le temps total passé par les messages dans des buffers.
	 */
	private long					tempsTotalDansBuffers		= 0;
	/**
	 * Le temps total de voyage des messages.
	 */
	private long					tempsTotalVoyageMessages	= 0;
	/**
	 * Nombre de messages réexpédiés à cause d'un timeout trop court.
	 */
	private int						timeoutsTropCourts			= 0;



	/**
	 * Créer un nouvel hote.
	 */
	public Hote() {
		this.setNumero(++TOTAL_HOTES);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// Faire ici toute initialisation qui requiert l'utilisation de la
		// configuration
		messageLePlusReemis = MessageSimple.creerFauxMessage();
	}



	/**
	 * Envoi d'un message original par un hôte.
	 */
	public void envoiMessageOriginal() {
		// par exemple nombres 1 à 75 si l'option
		// était fixée à 0.75, donc 75% à envoyer à un autre agent
		int randomDigitsAutreAgent =
				(int) (getConfiguration().getConfigurationHotes()
						.getTauxMessagesVersAutreAgent() * 100);
		int random = generateurTypeDestination.nextInt(100) + 1;
		boolean messagePourHoteAutreAgent = false;
		if (random <= randomDigitsAutreAgent) {
			messagePourHoteAutreAgent = true;
		}
		Hote hoteDestination = null;
		if (messagePourHoteAutreAgent) {
			LOGGER.trace("Message de l'hôte " + getNumero()
					+ " pour un autre agent (génération au temps "
					+ getSimulation().getHorloge() + ")");
			// choix aléatoire, on ajoute l'agent de cet hôte comme exception
			hoteDestination =
					getSimulation().getAgentAleatoire(this.getAgent())
							.getHoteAleatoire();
		} else {
			LOGGER.trace("Message de l'hôte " + getNumero()
					+ " pour le même agent (génération au temps "
					+ getSimulation().getHorloge() + ")");
			// choix aléatoire, on ajoute cet hôte comme exception
			hoteDestination = this.getAgent().getHoteAleatoire(this);
		}
		// création du message
		MessageSimple message =
				new MessageSimple(this, hoteDestination, ++identifiantMessages,
						1, getSimulation().getHorloge());
		
		// on ajoute l'identifiant de ce message à la liste
		// des identifiants pour lesquels on a pas encore reçu
		// d'ack
		accusesNonRecus.add(message.getNumeroMessage());
		// création de l'évènement de réception par l'agent de l'hôte
		AgentRecoitMessage evenementReceptionAgent =
				genererEvenementAgentRecoitMessage(message);
		// ajout de l'évènement de réception par l'agent à la FEL
		getSimulation().getFutureEventList().planifierEvenement(
				evenementReceptionAgent);
		// création de l'évènement timeout correspondant
		HoteTimeoutReceptionAccuse evenementTimeout =
				genererEvenementHoteTimeoutReceptionAccuse(message);
		// ajout de l'évènement timeout à la FEL
		getSimulation().getFutureEventList().planifierEvenement(
				evenementTimeout);
		// incrémentation du nombre de messages envoyés
		messagesEnvoyes++;
		
		// on incrémente aussi l'information globale (pour tous les hôtes)
		TOTAL_MESSAGES_ENVOYES++;
		
		// génération du prochain évènement d'envoi pour cet hôte
		HoteEnvoieMessageOriginal evtProchainEnvoi =
				genererEvenementHoteEnvoieMessageOriginal();
		// ajout de cet évènement à la FEL
		getSimulation().getFutureEventList().planifierEvenement(
				evtProchainEnvoi);
	}



	/**
	 * Appelé quand cet hôte termine de traiter un message.
	 *
	 * @param message
	 *        le message que cet hôte finit de traiter
	 */
	public void finitTraiterMessage(final Message message) {
		if (message == null) {
			throw new IllegalArgumentException(
					"Le message que l'hôte termine de traiter ne peut pas être null!");
		}
		if (message instanceof MessageSimple) {
			LOGGER.trace("Hote " + getNumero()
					+ " termine de traiter un message simple au temps "
					+ getSimulation().getHorloge());
			MessageSimple tmp = (MessageSimple) message;
			// on crée l'accusé de réception
			AccuseReception accuseReception =
					new AccuseReception(this, tmp.getSource(), tmp, getSimulation().getHorloge());
			// création de l'évènement de réception par l'agent (on envoie notre
			// accusé de réception à notre agent)
			AgentRecoitMessage evtAgentRecoitMessage =
					genererEvenementAgentRecoitMessage(accuseReception);
			// ajout de l'évènement à la FEL
			getSimulation().getFutureEventList().planifierEvenement(
					evtAgentRecoitMessage);
		} else if (message instanceof AccuseReception) {
			LOGGER.trace("Hote " + getNumero()
					+ " termine de traiter un accuse de reception au temps "
					+ getSimulation().getHorloge());
			AccuseReception tmp = (AccuseReception) message;
			// on incrémente le compteur d'accusés de réception reçus
			accusesReceptionRecus++;
			
			// on incrémente aussi le compteur global (pour tous les hôtes
			TOTAL_ACCUSES_RECUS++;
			
			if (accusesNonRecus.contains(Integer.valueOf(tmp
					.getMessageOrigine().getNumeroMessage()))) {
				accusesNonRecus.remove(Integer.valueOf(tmp.getMessageOrigine()
						.getNumeroMessage()));
			}
			// on incrémente le temps total passé par les messages dans des
			// buffers
			long tempsDansBuffers = tmp.getTempsPasseDansBuffers() + tmp.getMessageOrigine().getTempsPasseDansBuffers();
			tempsTotalDansBuffers += tempsDansBuffers;
			
			// on incrémente aussi l'information globale (pour tous les hôtes)
			TOTAL_TEMPS_BUFFERS += tempsDansBuffers;
			
			// on incrémente le temps total de voyage des messages
			long tempsVoyageSupplementaire = getSimulation().getHorloge() - tmp.getMessageOrigine().getTempsEmission();			
			tempsTotalVoyageMessages += tempsVoyageSupplementaire;

			// on incrémente aussi l'info globale (pour tous les hôtes)
			TOTAL_TEMPS_VOYAGE_MESSAGES += tempsVoyageSupplementaire;
			
			// on vérifie si ce message est celui ayant mis le plus de temps à
			// effectuer
			// son trajet complet. Si oui il devient le max.
			long tempsTrajetTotalDuMessage =
					getSimulation().getHorloge()
							- tmp.getMessageOrigine().getTempsEmission();
			if (tempsTrajetTotalDuMessage > messageTempsMax.getTempsTrajet()) {
				messageTempsMax.setAccuse(tmp);
				messageTempsMax.setTempsTrajet(tempsTrajetTotalDuMessage);
			}
			
			// on fait pareil au niveau global (pour tous les hôtes)
			if(tempsTrajetTotalDuMessage > MESSAGE_TEMPS_MAX.getTempsTrajet()){
				MESSAGE_TEMPS_MAX.setAccuse(tmp);
				MESSAGE_TEMPS_MAX.setTempsTrajet(tempsTrajetTotalDuMessage);
			}
			


			// on vérifie sur la FEL s'il y a un évènement timeout correspondant
			// à ce message
			HoteTimeoutReceptionAccuse timeoutCorrespondant =
					getSimulation().getFutureEventList()
							.trouverEvenementTimeoutPourMessage(
									tmp.getMessageOrigine());
			if (timeoutCorrespondant == null) {
				// si on a pas trouvé de timeout correspondant à ce message, ça
				// veut dire qu'on
				// l'a déjà réexpédié (donc il y a eu un timeout trop court
				// on incrémente le compteur de messages réexpédiés à cause d'un
				// timeout trop court
				timeoutsTropCourts++;
				
				// on met aussi à jour l'info globale (pour tous les hôtes)
				TOTAL_TIMEOUTS_TROP_COURTS++;
			} else {
				// si on a trouvé un évènement correspondant (accusé reçu à
				// temps!)
				// on le supprime de la FEL (plus de timeout nécessaire
				// puisqu'on a l'accusé)
				getSimulation().getFutureEventList().supprimer(
						timeoutCorrespondant);
			}
		}
		// maintenant qu'on a traite le message, on vérifie si le buffer est
		// vide ou non
		// pour commencer si possible à en traiter un autre
		if (getBuffer().isEmpty()) {
			// plus rien à traiter, on décrémente le nombre de messages en cours
			// de traitement
			messagesEnCoursTraitement--;
			
			// on décrémente aussi l'info au niveau global (pour tous les hôtes)
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
			HoteFinTraitementMessage evtHoteFinTraitementMessage =
					genererEvenementHoteFinTraitementMessage(messageATraiter);
			// on l'ajoute sur la FEL
			getSimulation().getFutureEventList().planifierEvenement(
					evtHoteFinTraitementMessage);
		}
	}



	/**
	 * Génère un évènement de type AgentRecoitMessage.
	 *
	 * @param message
	 *        le message que l'agent doit recevoir
	 * @return l'évènement créé
	 */
	private AgentRecoitMessage genererEvenementAgentRecoitMessage(
			final Message message) {
		return new AgentRecoitMessage(message, this.getAgent(), getSimulation()
				.getHorloge()
				+ getConfiguration().getConfigurationSimulationReseau()
						.getDelaiEntreEntites());
	}



	/**
	 * Génère et place sur la FEL un évènement de type HoteEnvoieMessageOriginal
	 * pour cet hôte.
	 */
	public HoteEnvoieMessageOriginal genererEvenementHoteEnvoieMessageOriginal() {
		long tempsEnvoi = genererTempsProchainEnvoi();
		return new HoteEnvoieMessageOriginal(this, tempsEnvoi);
	}



	/**
	 * Génère et ajoute à la FEL un évènement de type HoteFinTraitementMessage
	 *
	 * @param message
	 *        le message qu'on finira de traiter
	 */
	private HoteFinTraitementMessage genererEvenementHoteFinTraitementMessage(
			final Message message) {
		return new HoteFinTraitementMessage(message, this, getSimulation()
				.getHorloge() + 1);
	}



	/**
	 * Génère un évènement de type HoteTimeoutReceptionAccuse.
	 *
	 * @param message
	 *        le message pour lequel aucun accusé de réception n'a été reçu
	 * @return l'évènement créé
	 */
	private HoteTimeoutReceptionAccuse genererEvenementHoteTimeoutReceptionAccuse(
			final MessageSimple message) {
		return new HoteTimeoutReceptionAccuse(message, this, getSimulation()
				.getHorloge()
				+ getConfiguration().getConfigurationHotes()
						.getTimeoutReemissionMessages());
	}



	/**
	 * Générer le prochain temps d'envoi.
	 *
	 * @return le prochain temps d'envoi.
	 */
	private long genererTempsProchainEnvoi() {
		int tempsInterEnvois =
				generateurTempsEnvoi.nextInt(getConfiguration()
						.getConfigurationHotes().getTempsMaxInterEnvois()) + 1;
		// on fait +1 et donc par exemple 0-5 devient 1-6
		return getSimulation().getHorloge() + tempsInterEnvois;
	}



	/**
	 * Récupérer le nombre d'accusés de réception reçus.
	 *
	 * @return le nombre d'accusés de réception reçus.
	 */
	public int getAccusesReceptionRecus() {
		return accusesReceptionRecus;
	}



	/**
	 * Récupérer l'agent auquel cet hôte est connecté.
	 *
	 * @return l'agent auquel cet hôte est connecté
	 */
	public Agent getAgent() {
		return agent;
	}



	/**
	 * Récupérer le message le plus réémis pour cet hôte.
	 *
	 * @return le message le plus réémis pour cet hôte
	 */
	public MessageSimple getMessageLePlusReemis() {
		return messageLePlusReemis;
	}



	/**
	 * Récupérer le nombre de messages envoyés.
	 *
	 * @return le nombre de messages envoyés.
	 */
	public int getMessagesEnvoyes() {
		return messagesEnvoyes;
	}



	/**
	 * Récupérer le nombre de messages réexpédiés.
	 *
	 * @return le nombre de messages réexpédiés
	 */
	public int getMessagesReexpedies() {
		return messagesReexpedies;
	}



	/**
	 * Récupérer le message ayant mis le plus de temps à effectuer son trajet
	 * complet.
	 *
	 * @return le message ayant mis le plus de temps à effectuer son trajet
	 *         complet
	 */
	public MessageTempsMax getMessageTempsMax() {
		return messageTempsMax;
	}



	/**
	 * Récupérer le temps total passé par les messages dans des buffers.
	 *
	 * @return le temps total passé par les messages dans des buffers
	 */
	public long getTempsTotalDansBuffers() {
		return tempsTotalDansBuffers;
	}



	/**
	 * Récupérer le temps total de voyage des messages.
	 *
	 * @return le temps total de voyage des messages
	 */
	public long getTempsTotalVoyageMessages() {
		return tempsTotalVoyageMessages;
	}



	/**
	 * Récupérer le nombre de messages réexpédiés à cause d'un timeout trop
	 * court.
	 *
	 * @return le nombre de messages réexpédiés à cause d'un timeout trop court
	 */
	public int getTimeoutsTropCourts() {
		return timeoutsTropCourts;
	}



	/**
	 * Appelé quand cet hôte reçoit un message.
	 *
	 * @param message
	 *        le message qu'il reçoit
	 */
	public void recoitMessage(final Message message) {
		if (message == null) {
			throw new IllegalArgumentException(
					"Le message ne peut pas être null!");
		}
		
		// on enregistre le temps de réception réel
		message.setTempsReception(getSimulation().getHorloge());
		
		if (messagesEnCoursTraitement < getConfiguration()
				.getConfigurationHotes().getNombreMaxTraitementsSimultanes()) {
			LOGGER.trace("Hote " + getNumero()
					+ " commence le traitement d'un message");
			// on peut traiter le message directement donc on génère
			// l'évènement de fin de traitement
			HoteFinTraitementMessage evtHoteFinTraitementMessage =
					genererEvenementHoteFinTraitementMessage(message);
			// on l'ajoute sur la FEL
			getSimulation().getFutureEventList().planifierEvenement(
					evtHoteFinTraitementMessage);
			// on incrémente le compteur de messages en cours de traitement
			messagesEnCoursTraitement++;
			
			// on incrémente aussi l'info au niveau global (pour tous les hôtes)
			TOTAL_MESSAGES_EN_COURS_TRAITEMENT++;
			
			return; // le traitement a commencé, on quitte la méthode
		}
		// on ne peut pas le traiter directement donc on le place dans le buffer
		// (le buffer des hôtes est supposé infini)
		LOGGER.trace("Hote " + getNumero()
				+ " place un message dans son buffer");
		getBuffer().add(
				new MessageEnAttente(message, getSimulation().getHorloge()));
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		LOGGER.trace("Réinitialisation de l'hôte " + getNumero());
		super.reset();
		this.messagesEnvoyes = 0;
		this.messagesReexpedies = 0;
		this.accusesReceptionRecus = 0;
		this.tempsTotalVoyageMessages = 0;
		this.tempsTotalDansBuffers = 0;
		this.messagesEnCoursTraitement = 0;
		this.identifiantMessages = 0;
		this.timeoutsTropCourts = 0;
		this.accusesNonRecus.clear();
		this.messageLePlusReemis = MessageSimple.creerFauxMessage();
		this.messageTempsMax.setAccuse(null);
		this.messageTempsMax.setTempsTrajet(0);
		TOTAL_ACCUSES_RECUS = 0;
		TOTAL_MESSAGES_ENVOYES = 0;
		TOTAL_MESSAGES_REEXPEDIES = 0;
		MESSAGE_LE_PLUS_REEMIS = MessageSimple.creerFauxMessage();
		TOTAL_TIMEOUTS_TROP_COURTS = 0;
		TOTAL_MESSAGES_EN_COURS_TRAITEMENT = 0;
		TOTAL_TEMPS_VOYAGE_MESSAGES = 0L;
		TOTAL_TEMPS_BUFFERS = 0L;
	}



	/**
	 * Définir l'agent auquel cet hôte est connecté.
	 *
	 * @param agent
	 *        l'agent auquel cet hôte est connecté
	 */
	public void setAgent(final Agent agent) {
		this.agent = agent;
	}



	/**
	 * Timeout pour la réception d'un accusé.
	 *
	 * @param message
	 *        le message pour lequel on a pas encore reçu d'accusé
	 */
	public void timeoutReceptionAccuse(final MessageSimple message) {
		// on ne réexpédie que si on a pas encore reçu d'accusé pour cet
		// identifiant
		if (accusesNonRecus.contains(Integer
				.valueOf(message.getNumeroMessage()))) {
			// on incrémente le compteur de messages réexpédiés
			messagesReexpedies++;
			
			// on incrémente aussi le nombre global 
			// de messages réexpédiés (par tous les hôtes)
			TOTAL_MESSAGES_REEXPEDIES++;
			
			// on recrée le message pour le renvoyer (ça doit être une instance
			// différente!) mais on garde le même ID (numéro de message)
			MessageSimple nouveauMessage =
					new MessageSimple(this, message.getDestination(), message
							.getNumeroMessage(),
							message.getNumeroEmission() + 1, getSimulation()
									.getHorloge());

			// on garde une trace du message ayant été réémis le plus de fois
			if (nouveauMessage.getNumeroEmission() > messageLePlusReemis
					.getNumeroEmission()) {
				messageLePlusReemis = nouveauMessage;
			}
			// on fait la même chose mais au niveau global (pour tous les hôtes)
			if(nouveauMessage.getNumeroEmission() > MESSAGE_LE_PLUS_REEMIS.getNumeroEmission()){
				MESSAGE_LE_PLUS_REEMIS = nouveauMessage;
			}
			
			
			// création de l'évènement de réception par l'agent de l'hôte
			AgentRecoitMessage evenementReceptionAgent =
					genererEvenementAgentRecoitMessage(nouveauMessage);
			// ajout de l'évènement de réception par l'agent à la FEL
			getSimulation().getFutureEventList().planifierEvenement(
					evenementReceptionAgent);
			// création de l'évènement timeout correspondant
			HoteTimeoutReceptionAccuse evenementTimeout =
					genererEvenementHoteTimeoutReceptionAccuse(nouveauMessage);
			// ajout de l'évènement timeout à la FEL (nouveau timeout)
			getSimulation().getFutureEventList().planifierEvenement(
					evenementTimeout);
		}
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Hote " + getNumero();
	}
}
