package be.simulation.entites;

import java.util.Random;
import be.simulation.evenements.AgentRecoitMessage;
import be.simulation.evenements.HoteEnvoieMessageOriginal;
import be.simulation.evenements.HoteTimeoutReceptionAccuse;
import be.simulation.messages.Message;
import be.simulation.messages.MessageSimple;

/**
 * Hote du système, relié à un et un seul agent (lien statique).
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class Hote extends AbstractEntiteSimulationReseau {
	/**
	 * Nombre d'accusés de réception reçus.
	 */
	private int				accusesReceptionRecus		= 0;
	/**
	 * Agent auquel cet hote est relié (pour pouvoir communiquer avec lui).
	 */
	private Agent			agent;
	/**
	 * PRNG utilisé pour générer les temps d'envoi.
	 */
	private final Random	generateurTempsEnvoi		= new Random();
	/**
	 * PRNG utilisé pour déterminer si un nouveau message doit être ou non à
	 * destination d'un hôte d'un autre agent.
	 */
	private final Random	generateurTypeDestination	= new Random();
	/**
	 * Permet de placer un identifiant unique sur chaque message que cet hôte
	 * envoie.
	 */
	private int				identifiantMessages			= 0;
	/**
	 * Le nombre de messages en cours de traitement.
	 */
	private int				messagesEnCoursTraitement	= 0;
	/**
	 * Nombre de messages envoyés.
	 */
	private int				messagesEnvoyes				= 0;
	/**
	 * Nombre de messages réexpédiés.
	 */
	// FIXME à cause de timeouts trop courts?
	private int				messagesReexpedies			= 0;
	/**
	 * Le temps total de voyage des messages.
	 */
	private long			tempsTotalVoyageMessages	= 0;



	/**
	 * Créer un nouvel hote.
	 */
	public Hote() {
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Faire ici toute initialisation qui requiert l'utilisation de la
		// configuration
	}

	
	/**
	 * Timeout pour la réception d'un accusé.
	 * @param message le message pour lequel on a pas encore reçu d'accusé
	 */
	public void timeoutReceptionAccuse(final Message message) {
		// on incrémente le compteur de messages réexpédiés
		messagesReexpedies++;
		
		// on recrée le message pour le renvoyer (ça doit être une instance différence!)
		Message nouveauMessage = creerMessage(message.getDestination());
		
		// création de l'évènement de réception par l'agent de l'hôte
		AgentRecoitMessage evenementReceptionAgent = genererEvenementAgentRecoitMessage(nouveauMessage);
		
		// ajout de l'évènement de réception par l'agent à la FEL
		getSimulation().getFutureEventList().planifierEvenement(
				evenementReceptionAgent);
		
		// création de l'évènement timeout correspondant
		HoteTimeoutReceptionAccuse evenementTimeout = genererEvenementHoteTimeoutReceptionAccuse(nouveauMessage);
		
		// ajout de l'évènement timeout à la FEL (nouveau timeout)
		getSimulation().getFutureEventList().planifierEvenement(
				evenementTimeout);
		
		//FIXME implémenter
		
	}
	
	/**
	 * Génère un évènement de type AgentRecoitMessage.
	 * @param message le message que l'agent doit recevoir
	 * @return l'évènement créé
	 */
	private AgentRecoitMessage genererEvenementAgentRecoitMessage(final Message message){
		return
			new AgentRecoitMessage(message, this.getAgent(),
					getSimulation().getHorloge()
							+ getConfiguration()
									.getConfigurationSimulationReseau()
									.getDelaiEntreEntites());
	
	}
	
	/**
	 * Génère un évènement de type HoteTimeoutReceptionAccuse.
	 * @param message le message pour lequel aucun accusé de réception n'a été reçu
	 * @return l'évènement créé
	 */
	private HoteTimeoutReceptionAccuse genererEvenementHoteTimeoutReceptionAccuse(final Message message){
		return new HoteTimeoutReceptionAccuse(message, this, getSimulation()
				.getHorloge()
				+ getConfiguration().getConfigurationHotes()
						.getTimeoutReemissionMessages());
	}
	
	/**
	 * Créer un message.
	 * @param destinataire le destinataire
	 * @return le message généré
	 */
	private MessageSimple creerMessage(final Hote destinataire){
		return new MessageSimple(this, destinataire, ++identifiantMessages,
				getSimulation().getHorloge());
	}


	/**
	 * Envoi d'un message original par un hôte.
	 */
	public void envoyerMessageOriginal() {
		// FIXME vérifier si correct
		// par exemple 0.75 = 75 (75% vers hote d'un autre agent)
		int randomDigitsAutreAgent =
				(int) (getConfiguration().getConfigurationHotes()
						.getTauxMessagesVersAutreAgent() * 100);
		int random = generateurTypeDestination.nextInt(100) + 1;
		boolean messagePourHoteAutreAgent = false;
		// par exemple digits 1 à 75 = message à envoyer à un autre agent
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
		MessageSimple message = creerMessage(hoteDestination);
		
		// création de l'évènement de réception par l'agent de l'hôte
		AgentRecoitMessage evenementReceptionAgent = genererEvenementAgentRecoitMessage(message);
		
		// ajout de l'évènement de réception par l'agent à la FEL
		getSimulation().getFutureEventList().planifierEvenement(
				evenementReceptionAgent);
		
		// création de l'évènement timeout correspondant
		HoteTimeoutReceptionAccuse evenementTimeout = genererEvenementHoteTimeoutReceptionAccuse(message);
		
		// ajout de l'évènement timeout à la FEL
		getSimulation().getFutureEventList().planifierEvenement(
				evenementTimeout);
		
		// incrémentation du nombre de messages envoyés
		messagesEnvoyes++;
		// génération du prochain évènement d'envoi pour cet hôte
		genererEvenementHoteEnvoieMessageOriginal();
	}



	/**
	 * Génère et place sur la FEL un évènement de type HoteEnvoieMessageOriginal
	 * pour cet hôte.
	 */
	public void genererEvenementHoteEnvoieMessageOriginal() {
		long tempsEnvoi = genererTempsProchainEnvoi();
		getSimulation().getFutureEventList().planifierEvenement(
				new HoteEnvoieMessageOriginal(this, tempsEnvoi));
	}



	/**
	 * Générer le prochain temps d'envoi.
	 * 
	 * @return le prochain temps d'envoi.
	 */
	private long genererTempsProchainEnvoi() {
		// FIXME ok? (+1 donc par exemple 0-5 devient 1-6)
		int tempsInterEnvois =
				generateurTempsEnvoi.nextInt(getConfiguration()
						.getConfigurationHotes().getTempsMaxInterEnvois()) + 1;
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
	 * Récupérer le temps total de voyage des messages.
	 * 
	 * @return le temps total de voyage des messages
	 */
	public long getTempsTotalVoyageMessages() {
		return tempsTotalVoyageMessages;
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
		this.messagesEnCoursTraitement = 0;
		this.identifiantMessages = 0;
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
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Hote " + getNumero();
	}
}
