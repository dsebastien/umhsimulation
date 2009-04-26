package be.simulation.messages.utilitaires;

import be.simulation.messages.AccuseReception;

/**
 * Simple classe utilisée pour stocker le message ayant pris le plus de temps à
 * être acquitté, ainsi que son accusé correspondant (en fait l'accusé contient le message).
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class MessageTempsMax {
	/**
	 * l'accusé de réception correspondant au message ayant pris le plus de
	 * temps à être acquitté.
	 */
	private AccuseReception	accuse		= null;
	/**
	 * temps de trajet du message et de son accusé.
	 */
	private long			tempsTrajet	= 0L;



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
	 * Définir le temps de trajet.
	 * 
	 * @param tempsTrajet
	 *        le temps de trajet
	 */
	public void setTempsTrajet(final long tempsTrajet) {
		this.tempsTrajet = tempsTrajet;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString(){
		return "Le trajet du message temps max a duré: "+tempsTrajet;
		
	}
}
