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
	 * Message d'origine pour lequel cet accusé de réception est envoyé.
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
	 */
	public AccuseReception(final Hote source, final Hote destination,
			final MessageSimple messageOrigine) {
		super(source, destination);
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
}
