package be.simulation.evenements;

import be.simulation.core.evenements.Evenement;
import be.simulation.entites.Agent;

/**
 * Evenement declenche quand un agent envoie des informations de routage.
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

	/**
	 * Construct d'un evenement AgentEnvoieInfosRoutage.
	 * 
	 * @param agent
	 *        l'agent qui envoie ces informations de routage
	 * @param tempsPrevu
	 *        le temps prevu pour cet evenement
	 */
	public AgentEnvoieInfosRoutage(final Agent agent, final long tempsPrevu) {
		super(tempsPrevu);
		if (agent == null) {
			throw new IllegalArgumentException("L'agent ne peut pas etre null!");
		}
		this.agent = agent;
	}



	/**
	 * Recuperer l'agent qui envoie des informations de routage.
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
		return agent.toString()+" envoie des informations de routage au temps " + getTempsPrevu();
	}
}
