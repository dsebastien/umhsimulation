package be.simulation.routage;

import java.util.ArrayList;
import java.util.List;
import be.simulation.entites.Agent;
import be.simulation.entites.Hote;
import be.simulation.messages.routage.InfosRoutage;

/**
 * Table de routage interne à un agent, lui permet de déterminer où envoyer un
 * message donné.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class TableDeRoutage {
	/**
	 * Les différentes routes disponibles.
	 */
	private final List<Route> routes = new ArrayList<Route>();

	/**
	 * Les voisins directs de cet hôte.
	 */
	private final List<Voisin> voisins = new ArrayList<Voisin>();

	/**
	 * Récupérer la liste des voisins.
	 * 
	 * @return la liste des voisins
	 */
	public List<Voisin> getVoisins() {
		return voisins;
	}
	
	/**
	 * Est-ce que le distance vector est activé?
	 */
	private final boolean distanceVectorActive;

	/**
	 * Constructeur par défaut.
	 * 
	 * @param distanceVectorActive est-ce que le distance vector est activé?
	 */
	public TableDeRoutage(final boolean distanceVectorActive) {
		this.distanceVectorActive = distanceVectorActive;
	}

	/**
	 * Ajouter une route à la table de routage.
	 * 
	 * @param destination
	 *            agent de destination de cette route
	 * @param voisin
	 *            l'agent voisin par lequel passer pour atteindre cette
	 *            destination
	 * @param distance
	 *            la distance entre cet agent et l'agent voisin
	 * @param cout
	 *            le coût du lien entre l'agent actuel et le voisin par lequel
	 *            passer
	 */
	public void ajouterRoute(final Agent destination, final Agent voisin,
			final int distance, final int cout) {
		Voisin voisinTmp = new Voisin(voisin, distance);
		if(!voisins.contains(voisinTmp)){
			throw new IllegalArgumentException("Le voisin spécifié est inconnu!");
		}
		this.routes.add(new Route(destination, voisinTmp, cout));
	}
	
	/**
	 * Ajouter un voisin à cet agent.
	 * @param agent l'agent voisin
	 * @param distance la distance vers ce voisin
	 */
	public void ajouterVoisin(final Agent agent, final int distance){
		Voisin voisinTmp = new Voisin(agent, distance);
		this.voisins.add(voisinTmp);
	}

	/**
	 * Récupérer la liste des routes.
	 * 
	 * @return la liste des routes
	 */
	public List<Route> getRoutes() {
		return routes;
	}

	/**
	 * Remise à zéro de cette table de routage.
	 */
	public void reset() {
		this.routes.clear();
		this.voisins.clear();
	}

	/**
	 * Trouver la meilleure route (obligatoirement l'un des voisins directs)
	 * pour faire suivre le message le plus efficacement vers sa destination
	 * finale.
	 * 
	 * @param destinataire
	 *            le destinataire final
	 * @return le meilleur voisin pour aller à la destination
	 */
	public Voisin trouverMeilleureVoisin(final Hote destinataire) {
		Voisin meilleurVoisin = null;
		
		// Si le DV n'est pas activé, on trouve simplement la route
		// puisqu'on l'a déjà (elle est fixée)
		if (distanceVectorActive) {
			// FIXME implémenter!
			throw new UnsupportedOperationException("Non implémenté");
		} else {
			// On cherche parmi les routes connues celle définie pour aller vers le
			// destinataire voulu
			for (Route route : routes) {
				if (route.getDestination().equals(destinataire.getAgent())) {
					meilleurVoisin = route.getVoisin();
				}
			}
		}

		if (meilleurVoisin == null) {
			throw new IllegalStateException(
					"Aucune voisin trouvée pour ce destinataire!");
		}

		return meilleurVoisin;
	}

	/**
	 * Utilisé quand le distance vector est activé.
	 * Tenter de mettre à jour la table de routage.
	 * 
	 * @param infosRoutage
	 *            les informations de routage d'un voisin
	 * @return vrai si la table de routage locale a été modifiée
	 */
	public boolean mettreAJour(final InfosRoutage infosRoutage) {
		// FIXME v2.0 implémenter! pas correct ni complet pour l'instant
		//throw new UnsupportedOperationException("oops");
		
		// on cherche quel voisin a envoyé les infos
		Voisin voisinAyantEnvoyeInfos = null;
		for(Voisin voisin: voisins){
			if(voisin.getAgent().equals(infosRoutage.getSource())){
				voisinAyantEnvoyeInfos = voisin;
			}
		}
		if(voisinAyantEnvoyeInfos == null){
			throw new IllegalStateException("Aucun voisin trouvé!");
		}
		
		
		boolean modifie = false;
		for(Route routeVoisin: infosRoutage.getRoutes()){
			// est-ce qu'on a une route avec la même destination 
			// passant par l'agent voisin nous ayant fourni les infos?
			boolean trouve = false;
			
			for(Route routeLocale: routes){
				if(routeLocale.getDestination().equals(routeVoisin.getDestination())){
					if(routeLocale.getVoisin().equals(routeVoisin.getVoisin())){
						// si trouvée, alors on met distance = cout
					}else{
						// sinon si existe Distance > Cout alors
						// via = voisin[N]
						// Distance = cout
					}
					trouve = true;
					break;
				}
			}
			
			if(trouve == false){
				// si non trouvée, on l'ajoute
				// et la route vers cette destination
				this.ajouterRoute(routeVoisin.getDestination(), infosRoutage.getSource(),
						// la distance totale c'est la distance vers le voisin +
						// la distance de la route
						routeVoisin.getVoisin().getDistance() + voisinAyantEnvoyeInfos.getDistance(),
						
						// le cout c'est le coût estimé par le voisin +
						// la distance vers ce voisin
						routeVoisin.getCout() + voisinAyantEnvoyeInfos.getDistance());
				modifie = true;
			}
		}
		return modifie;
	}
}
