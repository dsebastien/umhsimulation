package be.simulation.messages;

import be.simulation.entites.Hote;


/**
 * Un accusé de réception pour un message.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class AccuseReception extends Message {
	/**
	 * Message d'origine pour lequel cet accusé de réception a été envoyé.
	 */
	private final MessageSimple	messageOrigine;



	/**
	 * Création d'un accusé de réception.
	 * 
	 * @param source
	 *        hôte ayant envoyé cet accusé de réception
	 * @param destination
	 *        destinataire de cet accusé de réception
	 * @param messageOrigine
	 *        le message pour lequel auquel correspond cet accusé de réception.
	 * @param tempsEmission le temps d'émission de cet accusé
	 */
	public AccuseReception(final Hote source, final Hote destination,
			final MessageSimple messageOrigine, final long tempsEmission) {
		super(source, destination, tempsEmission);
		if (messageOrigine == null) {
			throw new IllegalArgumentException(
					"Le message d'origine ne peut pas être null");
		}
		this.messageOrigine = messageOrigine;
	}


	
	/**
	 * Récupérer le message d'origine pour lequel cet accusé de réception est
	 * envoyé.
	 * 
	 * @return le message d'origine
	 */
	public MessageSimple getMessageOrigine() {
		return messageOrigine;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Accusé de réception envoyé de "+getSource().getNumero()+" (Agent "+getSource().getAgent().getNumero()+") à "+getDestination().getNumero()+" (Agent "+getDestination().getAgent().getNumero()+")";
	}
}
