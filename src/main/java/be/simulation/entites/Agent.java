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
	 * Hôtes connectés à cet agent.
	 */
	private final List<Hote>	hotes	= new ArrayList<Hote>();
	/**
	 * Le numéro identifiant de cet agent.
	 */
	private int					numero;
	/**
	 * Les informations de routage dont dispose cet agent (lui permet de savoir
	 * vers où forwarder les messages).
	 */
	private final Routeur		routeur	= new Routeur();



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



	@Override
	public void reset() {
		LOGGER.trace("Réinitialisation de l'agent " + getNumero());
		// on recrée les hôtes
		this.hotes.clear();
		initialiserHotes();
		// TODO ici tout remettre à zéro (compteurs, ...)
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
