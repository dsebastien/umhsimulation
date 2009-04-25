package be.simulation.messages.utilitaires;

import be.simulation.messages.MessageSimple;

/**
 * Message ayant mis le plus de temps pour effectuer son trajet complet.
 *
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
// TODO documenter...
public class MessageTempsMax {




	/**
	 * temps de trajet du message.
	 */
	private long			tempsTrajet	= 0;
	/**
	 * le message
	 */
	private MessageSimple	message		= null;


	/**
	 * Constructeur par défaut.
	 */
	public MessageTempsMax() {
	}



	public long getTempsTrajet() {
		return tempsTrajet;
	}



	public void setTempsTrajet(long tempsTrajet) {
		this.tempsTrajet = tempsTrajet;
	}



	public MessageSimple getMessage() {
		return message;
	}



	public void setMessage(MessageSimple message) {
		this.message = message;
	}



}
