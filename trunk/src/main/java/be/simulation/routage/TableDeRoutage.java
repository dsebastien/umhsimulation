package be.simulation.routage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import be.simulation.entites.Agent;
import be.simulation.entites.Hote;
import be.simulation.messages.InfosRoutage;

/**
 * Table de routage interne à un agent, lui permet de déterminer où envoyer un
 * message donné.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class TableDeRoutage {
	public static final int					INFINI					=
																			Integer.MAX_VALUE;
	/**
	 * L'agent a qui appartient cette table de routage.
	 */
	private final Agent						agent;
	/**
	 * Le distance vector maintenu par cette table de routage
	 */
	private final Map<Agent, List<Route>>	tableDeRoutageComplete	=
																			new HashMap<Agent, List<Route>>();
	protected final Logger					LOGGER					=
																			Logger
																					.getLogger(getClass()
																							.getName());
	/**
	 * Les différentes routes disponibles (NON utilisé quand le DV est activé)
	 */
	private final List<Route>				routes					=
																			new ArrayList<Route>();
	/**
	 * Les voisins directs de cet hôte.
	 */
	private final List<Voisin>				voisins					=
																			new ArrayList<Voisin>();



	/**
	 * Constructeur par défaut.
	 * 
	 * @param agent
	 *        l'agent a qui appartient cette table de routage
	 * @param distanceVectorActive
	 *        est-ce que le distance vector est activé?
	 */
	public TableDeRoutage(final Agent agent) {
		if (agent == null) {
			throw new IllegalArgumentException("L'agent ne peut pas être null!");
		}
		this.agent = agent;
	}



	/**
	 * Afficher une table de routage ou un DV.
	 * 
	 * @param ag
	 *        l'agent pour lequel on affiche
	 * @param dv
	 *        Le DV à afficher
	 * @param tableComplete
	 *        Si on affiche une table de routage ou un DV
	 */
	public void afficher(final Agent ag, Map<Agent, List<Route>> dv,
			final boolean tableComplete) {
		if (tableComplete) {
			LOGGER.info("TABLE DE ROUTAGE: " + ag.toString());
		} else {
			LOGGER.info("DISTANCE VECTOR: " + ag.toString());
		}
		for (Agent agentDestination : dv.keySet()) {
			for (Route route : dv.get(agentDestination)) {
				LOGGER.info(route.toString());
			}
		}
		LOGGER.info("-----------------------------------------------------");
	}



	/**
	 * Ajouter une route à la table de routage.
	 * 
	 * @param destination
	 *        agent de destination de cette route
	 * @param voisin
	 *        l'agent voisin par lequel passer pour atteindre cette destination
	 * @param distance
	 *        la distance entre cet agent et l'agent voisin
	 * @param cout
	 *        le coût du lien entre l'agent actuel et le voisin par lequel
	 *        passer
	 */
	public void ajouterRoute(final Agent destination, final Agent voisin,
			final int distance, final int cout) {
		Voisin voisinTmp = new Voisin(voisin, distance);
		if (!voisins.contains(voisinTmp)) {
			throw new IllegalArgumentException(
					"Le voisin spécifié est inconnu!");
		}
		this.routes.add(new Route(destination, voisinTmp, cout));
	}



	/**
	 * Ajouter un voisin à cet agent.
	 * 
	 * @param agent
	 *        l'agent voisin
	 * @param distance
	 *        la distance vers ce voisin
	 */
	public void ajouterVoisin(final Agent agent, final int distance) {
		Voisin voisinTmp = new Voisin(agent, distance);
		this.voisins.add(voisinTmp);
	}



	/**
	 * Créer une route.
	 * 
	 * @param destination
	 *        agent de destination
	 * @param agentVoisin
	 *        l'agent voisin par lequel passer
	 * @param distance
	 *        la distance entre cet agent et l'agent voisin
	 * @param cout
	 *        le coût du lien entre l'agent actuel et le voisin par lequel
	 *        passer
	 */
	public Route creerRoute(final Agent destination, final Agent agentVoisin,
			final int distance, final int cout) {
		Voisin voisinConnu = trouverVoisin(agentVoisin);
		if (voisinConnu == null) {
			throw new IllegalStateException("Le voisin spécifié est inconnu!");
		}
		if (voisinConnu.getDistance() != distance) {
			throw new IllegalStateException("La distance devrait être la même!");
		}
		return new Route(destination, voisinConnu, cout);
	}



	/**
	 * Récupérer la table de routage complete de cet agent.
	 * 
	 * @return la table de routage complete
	 */
	public Map<Agent, List<Route>> getTableDeRoutageComplete() {
		return tableDeRoutageComplete;
	}



	/**
	 * Retourne le DV local (celui de cet agent).
	 * 
	 * @return le DV local
	 */
	public Map<Agent, List<Route>> getDistanceVector() {
		Map<Agent, List<Route>> dvAgent = new HashMap<Agent, List<Route>>();
		List<Route> tmpRoutes;
		Route tmpRoute;
		// on ne va donner aux voisins que les meilleures routes
		// qu'on connaît pour chaque destination
		// (notre distance vector donc)
		for (Agent destination : tableDeRoutageComplete.keySet()) {
			tmpRoutes = new ArrayList<Route>();
			tmpRoute = trouverMeilleureRoute(destination);			
			Route nouvelleInstanceRoute = creerRoute(tmpRoute.getDestination(), tmpRoute.getVoisin().getAgent(), tmpRoute.getVoisin().getDistance(), tmpRoute.getCout());
			tmpRoutes.add(nouvelleInstanceRoute);
			dvAgent.put(destination, tmpRoutes);
		}
		return dvAgent;
	}



	/**
	 * Récupérer la liste des voisins.
	 * 
	 * @return la liste des voisins
	 */
	public List<Voisin> getVoisins() {
		return voisins;
	}



	/**
	 * Utilisé quand le distance vector est activé. Tenter de mettre à jour la
	 * table de routage.
	 * 
	 * @param infosRoutage
	 *        les informations de routage d'un voisin
	 * @return vrai si la table de routage locale a été modifiée
	 */
	public boolean mettreAJour(final InfosRoutage infosRoutage) {
		// on cherche quel voisin a envoyé les infos
		Voisin voisinAyantEnvoyeInfos = null;
		for (Voisin voisin : voisins) {
			if (voisin.getAgent().equals(infosRoutage.getSource())) {
				voisinAyantEnvoyeInfos = voisin;
			}
		}
		if (voisinAyantEnvoyeInfos == null) {
			throw new IllegalStateException(
					"Celui ayant envoyé les infos n'est pas un voisin connu!");
		}
		Map<Agent, List<Route>> distanceVectorVoisin =
				infosRoutage.getDistanceVector();
		
		// Pour voir facilement l'évolution des tables de routage
		//afficher(this.agent, this.tableDeRoutageComplete, true);
		//afficher(this.agent, this.getDistanceVector(), false);
		//afficher(voisinAyantEnvoyeInfos.getAgent(), distanceVectorVoisin,false);
		
		// variable qui nous permet à la fin de savoir si
		// le distance vector local a changé
		// suite à la mise à jour
		boolean distanceVectorLocalModifie = false;
		for (Agent agentDestination : distanceVectorVoisin.keySet()) {
			if (agentDestination.equals(this.agent)) {
				// si l'agent de destination est l'agent actuel
				// alors on ignore les routes (on sait comment
				// aller jusque soi même :))
				continue;
			}			
			
			Route routeVoisin =
					distanceVectorVoisin.get(agentDestination).get(0);
			if (this.agent.equals(routeVoisin.getVoisin().getAgent())) {
				// si on est le next hop
				// d'une route du voisin
				// on l'ignore
				continue;
			}
			
			
			// TODO décider si ok et si oui ajouter à l'UML!
			// Si la destination finale est un voisin, on
			// ne modifie pas nos coûts car ça n'a pas d'intérêt
			// on ferait juste tourner le paquet dans le réseau inutilement
			boolean destinationEstVoisin = false;
			for(Voisin voisin: voisins){
				if(agentDestination.equals(voisin.getAgent())){
					destinationEstVoisin = true;
				}
			}
			if(destinationEstVoisin){
				continue;
			}
			
			
			// routes locales pour cette destination
			List<Route> routesLocales =
					tableDeRoutageComplete.get(agentDestination);
			// pour chaque route pour cette destination
			for (Route routeLocale : routesLocales) {
				if (!routeLocale.getVoisin().equals(voisinAyantEnvoyeInfos)) {
					// on ne considère par les routes
					// passant par un autre voisin
					continue;
				}
				// est-ce que le coût a changé?
				int nouveauCout =
						routeVoisin.getCout()
								+ voisinAyantEnvoyeInfos.getDistance();
				if (routeVoisin.getCout() == INFINI) {
					nouveauCout = INFINI;
				}
				boolean coutModifie = routeLocale.getCout() != nouveauCout;
				if (coutModifie) {
					routeLocale.setCout(nouveauCout);
					// est-ce que la route devient plus intéressante que notre
					// meilleure route actuelle? (est-ce que notre DV a changé?)
					if (routeLocale.getCout() < trouverMeilleureRoute(
							agentDestination).getCout()) {
						distanceVectorLocalModifie = true;
					}
				}
			}
		}
		
		// Pour voir facilement l'évolution des tables de routage
		// LOGGER.info("Après mise à jour: ");
		//afficher(this.agent, distanceVectorComplet, true);
		//afficher(this.agent, this.getDistanceVector(), false);
		 
		return distanceVectorLocalModifie;
	}



	/**
	 * Remise à zéro de cette table de routage.
	 */
	public void reset() {
		this.routes.clear();
		this.voisins.clear();
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Table de routage de: " + agent;
	}



	/**
	 * Trouve la meilleure route pour une destination donnée.
	 * 
	 * @param destination
	 *        la destination
	 * @return la meilleure route connue pour cette destination
	 */
	public Route trouverMeilleureRoute(final Agent destination) {
		Route meilleureRoute = null;
		for (Route route : tableDeRoutageComplete.get(destination)) {
			if (meilleureRoute == null
					|| route.getCout() < meilleureRoute.getCout()) {
				meilleureRoute = route;
			}
		}
		if (meilleureRoute == null) {
			throw new IllegalStateException(
					"Aucune route n'a été trouvée, ça ne devrait pas arriver!");
		}
		return meilleureRoute;
	}



	/**
	 * Trouver la meilleure route (obligatoirement l'un des voisins directs)
	 * pour faire suivre le message le plus efficacement vers sa destination
	 * finale.
	 * 
	 * @param destinataire
	 *        le destinataire final
	 * @return le meilleur voisin pour aller à la destination
	 */
	public Voisin trouverMeilleurVoisin(final Hote destinataire) {
		Voisin meilleurVoisin = null;
		
		boolean dvActive = agent.getConfiguration().getConfigurationSimulationReseau().isDistanceVectorActive();
		
		// Si le DV n'est pas activé, on trouve simplement la route
		// puisqu'on l'a déjà (elle est fixée)
		if (!dvActive) {
			// On cherche parmi les routes connues celle définie pour aller vers
			// le
			// destinataire voulu
			for (Route route : routes) {
				if (route.getDestination().equals(destinataire.getAgent())) {
					meilleurVoisin = route.getVoisin();
				}
			}
		} else {
			// si le distance vector est activé
			// on cherche la route pour cette destination
			// ayant le meilleur coût (= route dans notre DV)
			Route meilleureRoute =
					trouverMeilleureRoute(destinataire.getAgent());
			meilleurVoisin = meilleureRoute.getVoisin();
		}
		if (meilleurVoisin == null) {
			throw new IllegalStateException(
					"Aucune voisin trouvé pour ce destinataire!");
		}
		return meilleurVoisin;
	}



	private Voisin trouverVoisin(final Agent agentVoisin) {
		if (agentVoisin == null) {
			throw new IllegalArgumentException(
					"L'agent voisin ne peut pas être null!");
		}
		Voisin retVal = null;
		for (Voisin voisinConnu : voisins) {
			if (voisinConnu.getAgent().equals(agentVoisin)) {
				retVal = voisinConnu;
			}
		}
		return retVal;
	}
}
