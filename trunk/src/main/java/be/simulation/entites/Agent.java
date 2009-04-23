package be.simulation.entites;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import be.simulation.evenements.AgentFinTraitementMessage;
import be.simulation.evenements.AgentRecoitMessage;
import be.simulation.evenements.HoteRecoitMessage;
import be.simulation.messages.Message;
import be.simulation.messages.util.MessageEnAttente;
import be.simulation.routage.Routeur;

/**
 * Agent du système (= serveur).
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class Agent extends AbstractEntiteSimulationReseau {
	/**
	 * PRNG utilisé pour choisir un hôte au hasard parmi les hôtes connectés à cet agent.
	 */
	private final Random generateurChoixHote = new Random();
	
	/**
	 * PRNG utilisé pour déterminer si un message reçu est perdu ou non (suivant la configuration).
	 */
	private final Random generateurMessagesPerdus = new Random();
	/**
	 * Dernier temps de simulation ou le taux d'utilisation du buffer a été mis
	 * à jour.
	 */
	private long				dernierTempsMiseAJourTauxUtilisationBuffer	= 0;
	/**
	 * Hôtes connectés à cet agent.
	 */
	private final List<Hote>	hotes										=
																					new ArrayList<Hote>();
	/**
	 * Le nombre de messages en cours de traitement.
	 */
	private int					messagesEnCoursTraitement					= 0;
	/**
	 * Le nombre de messages perdus brutalement.
	 */
	private int					messagesPerdusBrutalement					= 0;
	/**
	 * Le nombre de messages reçus.
	 */
	private int					messagesRecus								= 0;
	/**
	 * Le numéro identifiant de cet agent.
	 */
	private int					numero;
	/**
	 * Les informations de routage dont dispose cet agent (lui permet de savoir
	 * vers où forwarder les messages).
	 */
	private final Routeur		routeur										=
																					new Routeur();
	/**
	 * La somme des taux d'utilisationdestination du buffer.
	 */
	private double				sommeTauxUtilisationBuffer					=
																					0.0;

	/**
	 * Le nombre de messages perdus car le buffer était plein.
	 */
private int messagesPerdusBufferPlein = 0;

/**
 * Récupérer le nombre de messages perdus car le buffer était plein.
 * @return le nombre de messages perdus car le buffer était plein.
 */
	public int getMessagesPerdusBufferPlein() {
		return messagesPerdusBufferPlein;
	}



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
	 * Récupérer le nombre de messages reçus.
	 * 
	 * @return le nombre de messages reçus.
	 */
	public int getMessagesRecus() {
		return messagesRecus;
	}



	/**
	 * Récupérer le numéro de cet agent.
	 * 
	 * @return le numéro de cet agent
	 */
	public int getNumero() {
		return numero;
	}



	/**
	 * Récupérer les infos de routage de cet agent.
	 * 
	 * @return les infos de routage de cet agent
	 */
	public Routeur getRouteur() {
		return routeur;
	}



	/**
	 * Récupérer la somme des taux d'utilisation du buffer.
	 * 
	 * @return la somme des taux d'utilisation du buffer.
	 */
	public double getSommeTauxUtilisationBuffer() {
		return sommeTauxUtilisationBuffer;
	}



	/**
	 * On crée les hôtes connectés à cet agent en fonction de la configuration.
	 */
	private void initialiserHotes() {
		LOGGER.trace("Initialisation des hôtes de l'agent");
		this.hotes.clear();
		for (int i = 1; i <= getConfiguration().getConfigurationAgents()
				.getNombreHotes() + 1; i++) {
			Hote hote = (Hote) getApplicationContext().getBean("hote");
			hote.setAgent(this);
			hote.setNumero(numero + i); // ex: 1001, ...
			hotes.add(hote);
		}
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		LOGGER.trace("Réinitialisation de l'agent " + getNumero());
		super.reset();
		
		sommeTauxUtilisationBuffer = 0.0;
		messagesPerdusBrutalement = 0;
		dernierTempsMiseAJourTauxUtilisationBuffer = 0;
		messagesRecus = 0;
		messagesPerdusBufferPlein = 0;
		
		// on réinitialise les hôtes (le nombre d'hôtes par agent dans la configuration peut avoir changé)
		initialiserHotes();
	}



	/**
	 * Définir le numéro de cet agent (réinitialise également ses hôtes)
	 * 
	 * @param numeroAgent
	 *        le numéro de cet agent
	 */
	public void setNumero(final int numeroAgent) {
		numero = numeroAgent;
		initialiserHotes();
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Agent " + getNumero();
	}
	
	/**
	 * Récupérer un hôte aléatoire de cet agent.
	 * @return un hôte aléatoire de cet agent
	 */
	public Hote getHoteAleatoire(){
		// choix d'une position entre 0 et nombre d'hôtes - 1 (Random commence à 0)
		int position = generateurChoixHote.nextInt(hotes.size()); 
		return hotes.get(position);
	}
	
	/**
	 * Récupérer un hôte aléatoire de cet agent pouvant être n'importe quel hôte sauf celui fourni en argument.
	 * @param exception le seul hôte ne pouvant pas être retourné
	 * @return un hôte aléatoire autre que celui donné en argument
	 */
	public Hote getHoteAleatoire(final Hote exception){
		if(exception == null){
			throw new IllegalArgumentException("L'hôte exclus (l'exception) ne peut pas être null!");
		}
		
		Hote retVal = null;
		do{
			retVal = getHoteAleatoire();
		}while(retVal == null || exception.equals(retVal));
		
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
		
		// FIXME ne prend pas en compte le dernier temps de mise à jour du
		// buffer! (sans doute incorrect donc!!)
		// on ajoute taux actuel d'occupation du buffer au total
		double tauxOccupationActuel = 0;
		if (getBuffer().size() > 0) {
			tauxOccupationActuel =
					((double) getBuffer().size() / (double) getConfiguration()
						.getConfigurationAgents().getTailleBuffer()) / 100;
		}
		//TODO effacer:
		LOGGER.info("Taux occupation actuel: " + tauxOccupationActuel);
		
		// on actualise la variable retenant le dernier temps de simulation où 
		// l'occupation du buffer a été enregistrée pour les statistiques
		dernierTempsMiseAJourTauxUtilisationBuffer = getSimulation().getHorloge();
		
		// vérification du destinataire final de ce message
		if (this == message.getDestination().getAgent()) {
			// message à destination d'un hôte de cet agent donc on peut le lui
			// remettre
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
			Agent agentIntermediaire =
					routeur.trouverMeilleureRoute(message.getDestination());
		
			// on génère l'évènement de réception par cet agent
			//FIXME probleme, il faut le cout!!!!!
			AgentRecoitMessage evtReceptionAgent =
					new AgentRecoitMessage(message, agentIntermediaire,
							getSimulation().getHorloge()
									+ routeur.);
			
			// on l'ajoute à la FEL
			getSimulation().getFutureEventList().planifierEvenement(evtReceptionAgent);
		}
		
		// on vérifie le contenu du buffer
		if (getBuffer().isEmpty()) {
			// plus rien à traiter, on décrémente le nombre de messages en cours
			// de traitement
			messagesEnCoursTraitement--;
		} else {
			// puisqu'il y a encore des messages en attente, on en prend un et
			// on commence à le traiter tout de suite
			MessageEnAttente messageEnAttente = getBuffer().poll();
			
			Message messageATraiter = messageEnAttente.getMessage();
			// on met à jour le temps que ce message à passé dans des buffers
			messageATraiter.augmenterTempsPasseDansBuffers(getSimulation()
					.getHorloge()
					- messageEnAttente.getTempsArrivee());
			// on génère l'évènement de fin de traitement et on l'ajoute à la
			// FEL
			genererEvenementAgentFinTraitementMessageEtPlacerSurFEL(messageATraiter);
		}
	}



	/**
	 * Génère et ajoute à la FEL un évènement de type AgentFinTraitementMessage
	 * 
	 * @param message
	 *        le message qu'on finira de traiter
	 */
	private void genererEvenementAgentFinTraitementMessageEtPlacerSurFEL(
			final Message message) {
		AgentFinTraitementMessage evenement =
				new AgentFinTraitementMessage(message, this, getSimulation()
						.getHorloge() + 1);
		getSimulation().getFutureEventList().planifierEvenement(evenement);
	}


	/**
	 * Appelé quand cet agent reçoit un message.
	 * @param message le message qu'il reçoit
	 */
	public void recoitMessage(final Message message) {
		if(message == null){
			throw new IllegalArgumentException("Le message ne peut pas être null!");
		}
		
		// on incrémente le nombre de messages reçus
		messagesRecus++;
		
		// on détermine si ce message est perdu ou non (utilisation d'une variable aléatoire)
		if(estPerdu()){
			// dans ce cas on augmente le compteur de messages perdus brutalement
			messagesPerdusBrutalement++;
		}else{
			if(messagesEnCoursTraitement < getConfiguration().getConfigurationAgents().getNombreMaxTraitementsSimultanes()){
				// on peut traiter le message directement donc on génère
				// l'évènement de fin de traitement et on l'ajoute à la FEL
				genererEvenementAgentFinTraitementMessageEtPlacerSurFEL(message);
				
				// on incrémente le compteur de messages en cours de traitement
				messagesEnCoursTraitement++;
			}else{
				// on ne peut plus traiter de message pour l'instant donc on doit le mettre en attente
				
				//FIXME V2.0 ajouter les détails de la vérification de l'occupation du buffer
				//pour générer des évènements AgentEnvoieInfosRoutage (voir UML)
				if(getBuffer().size() < getConfiguration().getConfigurationAgents().getTailleBuffer()){
					// le buffer n'est pas plein
					// FIXME implémenter!
				}else{
					// le buffer est plein donc le message est perdu
					messagesPerdusBufferPlein++;
				}
			}
		}
	}
	
	/**
	 * Détermine si on perd le message ou non (sur base de la configuration) en utilisant un PRNG.
	 * @return vrai si le message est perdu
	 */
	private boolean estPerdu(){
		//FIXME vérifier si calcul ok
		int randomDigits = (int) getConfiguration().getConfigurationAgents().getTauxPerteBrutale() * 100;
		boolean retVal = false;
		if(generateurMessagesPerdus.nextInt(100) + 1 <= randomDigits){
			retVal = true;
		}
		return retVal;
	}
}
