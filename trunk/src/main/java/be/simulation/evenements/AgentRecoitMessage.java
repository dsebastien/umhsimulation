package be.simulation.evenements;

import be.simulation.core.evenements.Evenement;
import be.simulation.entites.Agent;
import be.simulation.messages.Message;

/**
 * Evenement déclenché quand un agent reçoit un message.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class AgentRecoitMessage extends Evenement {
	/**
	 * L'agent qui reçoit le message.
	 */
	private final Agent		agent;
	/**
	 * Le message reçu.
	 */
	private final Message	message;



	/**
	 * Evènement de réception.
	 * 
	 * @param message
	 *        le message reçu
	 * @param agent
	 *        l'agent qui reçoit ce message
	 * @param tempsPrevu
	 *        le temps auquel l'agent reçoit ce message
	 **/
	public AgentRecoitMessage(final Message message, final Agent agent,
			final long tempsPrevu) {
		super(tempsPrevu);
		if (message == null) {
			throw new IllegalArgumentException(
					"Le message ne peut pas être null!");
		}
		if (agent == null) {
			throw new IllegalArgumentException("L'agent ne peut pas être null!");
		}
		this.agent = agent;
		this.message = message;
	}



	public Agent getAgent() {
		return agent;
	}



	/**
	 * Récupérer le message reçu.
	 * 
	 * @return le message reçu
	 */
	public Message getMessage() {
		return message;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Agent " + agent.getNumero()
				+ " reçoit un message au temps " + getTempsPrevu();
	}
}
