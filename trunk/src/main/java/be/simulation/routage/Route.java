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
	 * Destination finale.
	 */
	private final Agent	destination;

	/**
	 * Voisin chez qui envoyer le message pour atteindre le plus efficacement
	 * possible l'agent de destination.
	 */
	private final Agent	voisin;
	/**
	 * Le coût du lien entre l'agent actuel et le voisin par lequel passer.
	 */
	private final int		cout;



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
	 * Récupérer le voisin par lequel passer pour atteindre la destination
	 * voulue.
	 * 
	 * @return le voisin par lequel passer pour atteindre la destination voulue.
	 */
	public Agent getVoisin() {
		return voisin;
	}



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
	public Route(final Agent destination, final Agent voisin, final int cout) {
		if (destination == null) {
			throw new IllegalArgumentException(
					"L'agent de destination ne peut pas etre null!");
		}
		if (voisin == null) {
			throw new IllegalArgumentException(
					"Le voisin ne peut pas être null!");
		}
		if (cout < 0) {
			throw new IllegalArgumentException(
					"Le coût ne peut pas être < 0!");
		}
		this.destination = destination;
		this.voisin = voisin;
		this.cout = cout;
	}



	/**
	 * Récupérer l'agent de destination de cette route
	 * 
	 * @return l'agent de destination de cette route
	 */
	public Agent getDestination() {
		return destination;
	}
	
	@Override
	public String toString(){
		return "La route pour à destination de "+destination.getNumero()+" passe par "+voisin;
	}
}
