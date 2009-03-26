package be.simulation.entites;

import java.util.ArrayList;
import java.util.List;
import be.simulation.configuration.ConfigurationAgents;
import be.simulation.core.entites.AbstractSimulationEntity;

/**
 * Agent du système (= serveur).
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class Agent extends AbstractSimulationEntity<ConfigurationAgents> {
	/**
	 * Hôtes connectés à cet agent.
	 */
	private final List<Hote>	hotes	= new ArrayList<Hote>();
	
	/**
	 * Le numéro identifiant de cet agent.
	 */
	private final int			numeroAgent;

	/**
	 * Taille des buffers.
	 */
	private final int			tailleBuffers;
	/**
	 * Crée un nouvel agent.
	 * 
	 * @param numeroAgent
	 *        le numéro identifiant de cet agent
	 * @param tailleBuffers
	 *        la taille maximale des buffers
	 */
	public Agent(final int numeroAgent, final int tailleBuffers) {
		this.tailleBuffers = tailleBuffers;
		this.numeroAgent = numeroAgent;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// Quand l'agent est créé, les hôtes viennent directement s'y connecter
		for (int i = 1; i <= getConfiguration().getNombreHotes(); i++) {
			hotes.add(new Hote(this, i));
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
}
