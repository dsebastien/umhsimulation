package be.simulation.messages.utilitaires;

import be.simulation.messages.AccuseReception;
import be.simulation.messages.MessageSimple;

/**
 * Simple classe utilisée pour stocker le message ayant pris le plus de temps à
 * être acquitté, ainsi que son accusé correspondant.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class MessageTempsMax {
	/**
	 * l'accusé de réception correspondant au message.
	 */
	private AccuseReception	accuse		= null;
	/**
	 * le message.
	 */
	//FIXME virer le message, l'accuse suffit puisqu'il contient tout
	private MessageSimple	message		= null;
	/**
	 * temps de trajet du message.
	 */
	private long			tempsTrajet	= 0;



	/**
	 * Constructeur par défaut.
	 */
	public MessageTempsMax() {
	}



	/**
	 * Récupérer l'accusé de réception.
	 * 
	 * @return l'accusé de réception
	 */
	public AccuseReception getAccuse() {
		return accuse;
	}



	/**
	 * Récupérer le message.
	 * 
	 * @return le message
	 */
	public MessageSimple getMessage() {
		return message;
	}



	/**
	 * Récupérer le temps de trajet.
	 * 
	 * @return le temps de trajet
	 */
	public long getTempsTrajet() {
		return tempsTrajet;
	}



	/**
	 * Définir l'accusé de réception.
	 * 
	 * @param accuse
	 *        l'accusé de réception
	 */
	public void setAccuse(final AccuseReception accuse) {
		this.accuse = accuse;
	}



	/**
	 * Définir le message.
	 * 
	 * @param message
	 *        le message.
	 */
	public void setMessage(final MessageSimple message) {
		this.message = message;
	}



	/**
	 * Définir le temps de trajet.
	 * 
	 * @param tempsTrajet
	 *        le temps de trajet
	 */
	public void setTempsTrajet(final long tempsTrajet) {
		this.tempsTrajet = tempsTrajet;
	}
}
