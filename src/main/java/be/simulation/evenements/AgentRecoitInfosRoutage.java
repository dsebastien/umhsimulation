package be.simulation.evenements;

import be.simulation.core.evenements.Evenement;
import be.simulation.entites.Agent;
import be.simulation.messages.InfosRoutage;

/**
 * Evenement déclenché quand un agent reçoit des informations de routage.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class AgentRecoitInfosRoutage extends Evenement {
	/**
	 * L'agent qui reçoit les infos de routage.
	 */
	private final Agent			agent;
	/**
	 * Les informations de routage.
	 */
	private final InfosRoutage	infosRoutage;



	/**
	 * Construction d'un évènement AgentRecoitInfosRoutage.
	 * 
	 * @param infosRoutage
	 *        les informations de routage reçues
	 * @param agent
	 *        l'agent qui reçoit ces informations de routage
	 * @param tempsPrevu
	 *        le temps prévu pour cet évènement
	 */
	public AgentRecoitInfosRoutage(final InfosRoutage infosRoutage,
			final Agent agent, final long tempsPrevu) {
		super(tempsPrevu);
		if (infosRoutage == null) {
			throw new IllegalArgumentException(
					"Les informations de routage ne peuvent pas être null");
		}
		if (agent == null) {
			throw new IllegalArgumentException("L'agent ne peut pas être null!");
		}
		this.infosRoutage = infosRoutage;
		this.agent = agent;
	}



	/**
	 * Récupérer l'agent qui reçoit des informations de routage.
	 * 
	 * @return l'agent qui reçoit des informations de routage.
	 */
	public Agent getAgent() {
		return agent;
	}



	/**
	 * Récupérer les informations de routage.
	 * 
	 * @return les informations de routage.
	 */
	public InfosRoutage getInfosRoutage() {
		return infosRoutage;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Agent " + agent.getNumero()
				+ " reçoit des informations de routage au temps "
				+ getTempsPrevu();
	}
}
