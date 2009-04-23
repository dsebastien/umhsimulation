package be.simulation.entites;

import java.util.Random;
import be.simulation.evenements.AgentRecoitMessage;
import be.simulation.evenements.HoteEnvoieMessageOriginal;
import be.simulation.evenements.HoteTimeoutReceptionAccuse;
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
	 * PRNG utilisé pour générer les temps d'envoi.
	 */
	private final Random	generateurTempsEnvoi		= new Random();
	/**
	 * PRNG utilisé pour déterminer si un nouveau message doit être ou non à
	 * destination d'un hôte d'un autre agent.
	 */
	private final Random	generateurTypeDestination	= new Random();
	/**
	 * Nombre d'accusés de réception reçus.
	 */
	private int				accusesReceptionRecus		= 0;
	/**
	 * Agent auquel cet hote est relié (pour pouvoir communiquer avec lui).
	 */
	private Agent			agent;
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
	 * Numero identifiant de l'hote.
	 */
	private int				numero;
	/**
	 * Le temps total de voyage des messages.
	 */
	private long			tempsTotalVoyageMessages	= 0;
	
	/**
	 * Permet de placer un identifiant unique sur chaque message que cet hôte envoie.
	 */
	private int identifiantMessages = 0;



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
	 * Récupérer le numero de l'hote.
	 * 
	 * @return le numéro de l'hote
	 */
	public int getNumero() {
		return numero;
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
	 * Définir le numéro de cet hote
	 * 
	 * @param numeroHote
	 *        le numéro de cet hôte
	 * @throw IllegalArgumentException si le numéro d'hote fourni est <= 0
	 */
	public void setNumero(final int numeroHote) {
		if (numeroHote <= 0) {
			throw new IllegalArgumentException(
					"Le numero d'hote fourni est invalide (il doit etre > 0)");
		}
		this.numero = numeroHote;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Hote " + getNumero();
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
			LOGGER.trace("Message de l'hôte "+numero+" pour un autre agent (génération au temps "+getSimulation().getHorloge()+")");
			// choix aléatoire, on ajoute l'agent de cet hôte comme exception
			hoteDestination =
					getSimulation().getAgentAleatoire(this.getAgent())
							.getHoteAleatoire();
		} else {
			LOGGER.trace("Message de l'hôte "+numero+" pour le même agent (génération au temps "+getSimulation().getHorloge()+")");
			// choix aléatoire, on ajoute cet hôte comme exception
			hoteDestination = this.getAgent().getHoteAleatoire(this);
		}
		
		// création du message
		MessageSimple message =
				new MessageSimple(this, hoteDestination, ++identifiantMessages,
						getSimulation().getHorloge());
		
		// création de l'évènement de réception par l'agent de l'hôte
		AgentRecoitMessage evenementReceptionAgent =
				new AgentRecoitMessage(message, this.getAgent(),
						getSimulation().getHorloge()
								+ getConfiguration()
										.getConfigurationSimulationReseau()
										.getDelaiEntreEntites());
		
		// ajout de l'évènement de réception par l'agent à la FEL
		getSimulation().getFutureEventList().planifierEvenement(evenementReceptionAgent);
		
		// création de l'évènement timeout correspondant
		HoteTimeoutReceptionAccuse evenementTimeout =
				new HoteTimeoutReceptionAccuse(message, this, getSimulation()
						.getHorloge()
						+ getConfiguration().getConfigurationHotes()
								.getTimeoutReemissionMessages());
		// ajout de l'évènement timeout à la FEL
		getSimulation().getFutureEventList().planifierEvenement(
				evenementTimeout);
		
		// incrémentation du nombre de messages envoyés
		messagesEnvoyes++;
		
		// génération du prochain évènement d'envoi pour cet hôte
		genererEvenementHoteEnvoieMessageOriginal();
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
	 * Génère et place sur la FEL un évènement de type HoteEnvoieMessageOriginal pour cet hôte.
	 */
	public void genererEvenementHoteEnvoieMessageOriginal(){
		long tempsEnvoi = genererTempsProchainEnvoi();
		getSimulation().getFutureEventList().planifierEvenement(
				new HoteEnvoieMessageOriginal(this, tempsEnvoi));
	}
}
