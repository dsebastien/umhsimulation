package be.simulation.routage;

import be.simulation.entites.Agent;

/**
 * Route disponible.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 * @version 1.0 On utilise une table de routage fixée dès le début et statique
 */
public class Route {
	/**
	 * Le coût associé à cette route.
	 */
	private int			cout;
	/**
	 * Destination finale.
	 */
	private final Agent	destination;
	/**
	 * Voisin chez qui envoyer le message pour atteindre le plus efficacement
	 * possible l'agent de destination.
	 */
	private Voisin		voisin;



	/**
	 * Création d'une route.
	 * 
	 * @param destination
	 *        agent de destination de cette route
	 * @param voisin
	 *        l'agent voisin par lequel passer pour atteindre cette destination
	 * @param cout
	 *        le coût du lien entre l'agent actuel et le voisin par lequel
	 *        passer
	 */
	public Route(final Agent destination, final Voisin voisin, final int cout) {
		if (destination == null) {
			throw new IllegalArgumentException(
					"L'agent de destination ne peut pas etre null!");
		}
		if (cout < 0) {
			throw new IllegalArgumentException("Le coût ne peut pas être < 0!");
		}
		this.destination = destination;
		this.voisin = voisin;
		this.cout = cout;
	}



	/**
	 * Récupérer le coût du lien entre l'agent actuel et le voisin par lequel
	 * passer.
	 * 
	 * @return le coût du lien entre l'agent actuel et le voisin par lequel
	 *         passer.
	 */
	public int getCout() {
		return cout;
	}



	/**
	 * Récupérer l'agent de destination de cette route
	 * 
	 * @return l'agent de destination de cette route
	 */
	public Agent getDestination() {
		return destination;
	}



	/**
	 * Récupérer le voisin par lequel passer pour atteindre la destination
	 * voulue.
	 * 
	 * @return le voisin par lequel passer pour atteindre la destination voulue.
	 */
	public Voisin getVoisin() {
		return voisin;
	}



	/**
	 * Définir le coût de cette route.
	 * 
	 * @param cout
	 *        le cout de la route
	 */
	public void setCout(final int cout) {
		this.cout = cout;
	}



	/**
	 * Définir le voisin chez qui envoyer le message pour atteindre le plus
	 * efficacement possible l'agent de destination
	 * 
	 * @param voisin
	 *        le voisin
	 */
	public void setVoisin(final Voisin voisin) {
		this.voisin = voisin;
	}



	@Override
	public String toString() {
		return "La route à destination de " + destination.getNumero()
				+ " passe par " + voisin+" avec un cout de "+getCout();
	}
}
