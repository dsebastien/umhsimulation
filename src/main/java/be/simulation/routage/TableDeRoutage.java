package be.simulation.routage;

import java.util.ArrayList;
import java.util.List;
import be.simulation.entites.Agent;
import be.simulation.entites.Hote;

/**
 * Table de routage interne à un agent, lui permet de déterminer où envoyer un message
 * donné.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class TableDeRoutage {
	/**
	 * Les différentes routes disponibles.
	 */
	private final List<Route>	routes	= new ArrayList<Route>();
	/**
	 * Constructeur par défaut.
	 */
	public TableDeRoutage() {
	}
	
	/**
	 * Trouver la meilleure route (obligatoirement l'un des voisins directs) pour faire suivre le message le plus efficacement vers sa destination finale.
	 * @param destinataire le destinataire final
	 * @return la meilleure route (connue)
	 */
	public Route trouverMeilleureRoute(final Hote destinataire) {
		Route meilleureRoute = null;

		// On cherche parmi les routes connues celle définie pour aller vers le destinataire voulu
		for(Route route: routes){
			if(route.getDestination().equals(destinataire.getAgent())){
				meilleureRoute = route;
			}
		}
		
		if(meilleureRoute == null){
			throw new IllegalStateException("Aucune route trouvée pour ce destinataire!");
		}
		
		return meilleureRoute;
	}
	
	/**
	 * Remise à zéro de cette table de routage.
	 */
	public void reset() {
		this.routes.clear();
	}



	/**
	 * Ajouter une route à la table de routage.
	 * 
	 * @param route
	 *        la route à ajouter
	 */
	public void ajouterRoute(final Route route) {
		if (route == null) {
			throw new IllegalArgumentException(
					"La route ne peut pas être null!");
		}
		this.routes.add(route);
	}
	
	/**
	 * Ajouter une route à la table de routage.
	 * 
	 * @param destination agent de destination de cette route
	 * @param voisin l'agent voisin par lequel passer pour atteindre cette destination
	 * @param cout le coût du lien entre l'agent actuel et le voisin par lequel
	 *        passer
	 */
	public void ajouterRoute(final Agent destination, final Agent voisin, final int cout){
		this.routes.add(new Route(destination,voisin,cout));
	}
}
