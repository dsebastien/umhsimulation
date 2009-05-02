package be.simulation.evenements;

import be.simulation.core.evenements.Evenement;
import be.simulation.entites.Agent;

/**
 * Evenement déclenché quand un agent envoie des informations de routage.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class AgentEnvoieInfosRoutage extends Evenement {
	/**
	 * L'agent qui envoie les infos de routage.
	 */
	private final Agent	agent;

	private final boolean	initialisation;


	public boolean isInitialisation() {
		return initialisation;
	}



	/**
	 * Construct d'un évènement AgentEnvoieInfosRoutage.
	 * 
	 * @param agent
	 *        l'agent qui envoie ces informations de routage
	 * @param tempsPrevu
	 *        le temps prévu pour cet évènement
	 */
	public AgentEnvoieInfosRoutage(final Agent agent, final long tempsPrevu,
			final boolean initialisation) {
		super(tempsPrevu);
		if (agent == null) {
			throw new IllegalArgumentException("L'agent ne peut pas être null!");
		}
		this.agent = agent;
		this.initialisation = initialisation;
	}



	/**
	 * Récupérer l'agent qui envoie des informations de routage.
	 * 
	 * @return l'agent qui envoie des informations de routage.
	 */
	public Agent getAgent() {
		return agent;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Agent " + agent.getNumero()
				+ " e des informations de routage au temps " + getTempsPrevu();
	}
}
