package be.simulation.entites;

import java.util.ArrayList;
import java.util.List;
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
	 * La somme des taux d'utilisation du buffer.
	 */
	private double				sommeTauxUtilisationBuffer					=
																					0.0;



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
		initialiserHotes();
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
		for (int i = 1; i <= getConfiguration().getConfigurationAgents()
				.getNombreHotes(); i++) {
			Hote hote = (Hote) getApplicationContext().getBean("hote");
			hote.setAgent(this);
			hote.setNumero(i);
			hotes.add(hote);
		}
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		LOGGER.trace("Réinitialisation de l'agent " + getNumero());
		// on recrée les hôtes
		this.hotes.clear();
		initialiserHotes();
		sommeTauxUtilisationBuffer = 0.0;
		messagesPerdusBrutalement = 0;
		dernierTempsMiseAJourTauxUtilisationBuffer = 0;
		messagesRecus = 0;
	}



	/**
	 * Définir le numéro de cet agent.
	 * 
	 * @param numeroAgent
	 *        le numéro de cet agent
	 */
	public void setNumero(final int numeroAgent) {
		numero = numeroAgent;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Agent " + getNumero();
	}
}
