package be.simulation.modele;

import java.util.ArrayList;
import java.util.List;
import be.simulation.configuration.AgentsConfiguration;
import be.simulation.core.entites.AbstractSimulationEntity;

/**
 * Agent du système (= serveur).
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-Francois
 */
public class Agent extends AbstractSimulationEntity<AgentsConfiguration> {
	/**
	 * Le numéro identifiant de cet agent.
	 */
	private final int			numeroAgent;
	private final List<Hote>	hotes	= new ArrayList<Hote>();



	/**
	 * Créer un nouvel agent.
	 * 
	 * @param numeroAgent
	 *        le numéro identifiant de cet agent
	 */
	public Agent(final int numeroAgent) {
		this.numeroAgent = numeroAgent;
		// Quand l'agent est créé, les hôtes viennent directement s'y connecter
		for (int i = 1; i <= getConfiguration().getNombreHotes(); i++) {
			hotes.add(new Hote(this, i));
		}
	}
}
