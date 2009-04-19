package be.simulation.entites;

import java.util.ArrayList;
import java.util.List;

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
	 * Définir le numéro de cet agent.
	 * 
	 * @param numeroAgent
	 *        le numéro de cet agent
	 */
	public void setNumero(int numeroAgent) {
		numero = numeroAgent;
	}
	/**
	 * Taille des buffers.
	 */
	private int					tailleBuffers;



	/**
	 * Crée un nouvel agent.
	 * 
	 */
	public Agent() {
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO dans un premier temps on utilise la même taille de buffers pour
		// tous. ensuite on pourra fixer des valeurs différentes pour chacun
		this.tailleBuffers =
			getConfiguration().getConfigurationSimulationReseau()
						.getTailleBuffersAgents();
		creerHotes();
	}



	/**
	 * On crée les hôtes connectés à cet agent en fonction de la configuration.
	 */
	private void creerHotes() {
		for (int i = 1; i <= getConfiguration().getConfigurationAgents()
		.getNombreHotes(); i++) {
			Hote hote = (Hote) getApplicationContext().getBean("hote");
			hote.setAgent(this);
			hote.setNumeroHote(i);
			hotes.add(hote);
		}
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
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Agent " + getNumero();
	}

	@Override
	public void reset() {
		// on recrée les hôtes
		this.hotes.clear();
		creerHotes();
		// TODO ici tout remettre à zéro (compteurs, ...)
	}
}
