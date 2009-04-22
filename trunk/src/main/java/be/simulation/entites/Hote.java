package be.simulation.entites;

import java.util.Random;

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
		// TODO ici tout remettre à zéro (compteurs, ...)
		this.messagesEnvoyes = 0;
		this.messagesReexpedies = 0;
		this.accusesReceptionRecus = 0;
		this.tempsTotalVoyageMessages = 0;
		this.messagesEnCoursTraitement = 0;
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



	// FIXME comment choisir l'agent à qui on envoie un nouveau message
	// original?
	/**
	 * Générer le prochain temps d'envoi.
	 * 
	 * @return le prochain temps d'envoi.
	 */
	public long genererTempsProchainEnvoi() {
		// FIXME ok? (+1 donc par exemple 0-5 devient 1-6)
		int tempsInterEnvois =
				generateurTempsEnvoi.nextInt(getConfiguration()
						.getConfigurationHotes().getTempsMaxInterEnvois()) + 1;
		return getSimulation().getHorloge() + tempsInterEnvois;
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
		
		// par exemple digits 1 à 75 = pour autre agent
		if (random <= randomDigitsAutreAgent) {
			messagePourHoteAutreAgent = true;
		}
		
		// FIXME implement
		if (messagePourHoteAutreAgent) {
			// FIXME comment choisir l'autre agent?
			// FIXME comment choisir un hôte de l'agent?
		} else {
			// FIXME comment choisir un autre hote de cet agent ci? (méthode
			// dans agent?)
		}
		
		// FIXME voir diagramme
	}
}
