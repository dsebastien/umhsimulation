package be.simulation.routage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
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
	public static final int					INFINI			= Integer.MAX_VALUE;
	/**
	 * L'agent a qui appartient cette table de routage.
	 */
	private final Agent						agent;
	/**
	 * Les différentes routes disponibles (NON utilisé quand le DV est activé)
	 */
	private final List<Route>				routes			=
																	new ArrayList<Route>();
	/**
	 * Le distance vector maintenu par cette table de routage
	 */
	private final Map<Agent, List<Route>>	distanceVector	=
																	new HashMap<Agent, List<Route>>();
	protected final Logger					LOGGER			=
																	Logger
																			.getLogger(getClass()
																					.getName());



	/**
	 * Récupérer le distance vector.
	 * 
	 * @return le distance vector
	 */
	public Map<Agent, List<Route>> getDistanceVector() {
		return distanceVector;
	}
	/**
	 * Les voisins directs de cet hôte.
	 */
	private List<Voisin>	voisins	= new ArrayList<Voisin>();



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
	private final boolean	distanceVectorActive;



	/**
	 * Constructeur par défaut.
	 * 
	 * @param agent
	 *        l'agent a qui appartient cette table de routage
	 * @param distanceVectorActive
	 *        est-ce que le distance vector est activé?
	 */
	public TableDeRoutage(final Agent agent, final boolean distanceVectorActive) {
		if (agent == null) {
			throw new IllegalArgumentException("L'agent ne peut pas être null!");
		}
		this.agent = agent;
		this.distanceVectorActive = distanceVectorActive;
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
	 *        le destinataire final
	 * @return le meilleur voisin pour aller à la destination
	 */
	public Voisin trouverMeilleurVoisin(final Hote destinataire) {
		Voisin meilleurVoisin = null;
		// Si le DV n'est pas activé, on trouve simplement la route
		// puisqu'on l'a déjà (elle est fixée)
		if (!distanceVectorActive) {
			// On cherche parmi les routes connues celle définie pour aller vers
			// le
			// destinataire voulu
			for (Route route : routes) {
				if (route.getDestination().equals(destinataire.getAgent())) {
					meilleurVoisin = route.getVoisin();
				}
			}
		} else {
			Route meilleureRoute = null;
			for (Route route : distanceVector.get(destinataire.getAgent())) {
				if (meilleureRoute == null
						|| route.getCout() < meilleureRoute.getCout()) {
					meilleureRoute = route;
				}
			}
			if (meilleureRoute == null) {
				throw new IllegalStateException(
						"Aucune route n'a été trouvée, ça ne devrait pas arriver!");
			}
			meilleurVoisin = meilleureRoute.getVoisin();
		}
		if (meilleurVoisin == null) {
			throw new IllegalStateException(
					"Aucune voisin trouvé pour ce destinataire!");
		}
		return meilleurVoisin;
	}



	/**
	 * Utilisé quand le distance vector est activé. Tenter de mettre à jour la
	 * table de routage.
	 * 
	 * @param infosRoutage
	 *        les informations de routage d'un voisin
	 * @return vrai si la table de routage locale a été modifiée
	 */
	public boolean mettreAJour(final InfosRoutage infosRoutage,
			boolean initialisation) {
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
		// FIXME commenter ou supprimer:
		afficherDistanceVector();
		voisinAyantEnvoyeInfos.getAgent().getTableDeRoutage()
				.afficherDistanceVector();
		// variable qui nous permet à la fin de savoir si
		// le distance vector local a changé
		// suite à la mise à jour
		boolean distanceVectorLocalModifie = false;
		Map<Agent, List<Route>> distanceVectorVoisin =
				infosRoutage.getDistanceVector();
		for (Agent agentDestination : distanceVectorVoisin.keySet()) {
			if (agentDestination.equals(this.agent)) {
				// si l'agent de destination est l'agent actuel
				// alors on ignore les routes (on sait comment
				// aller jusque soi même :))
				continue;
			}
			List<Route> routesVoisin =
					distanceVectorVoisin.get(agentDestination);
			List<Route> routesLocales = distanceVector.get(agentDestination);
			// pour chaque route dans le DV du voisin
			// pour cette destination
			for (Route routeVoisin : routesVoisin) {
				if (this.agent.equals(routeVoisin.getVoisin().getAgent())) {
					// si on est le next hop
					// d'une route du voisin
					// on l'ignore
					continue;
				}
				// on cherche dans les routes connues pour cette destination
				// si on en a déjà une qui passe par ce voisin
				for (Route routeLocale : routesLocales) {
					// FIXME les seuls coûts qui peuvent changer ce sont
					// d'abord les coûts
					// des voisins directs (0 -> 5000)
					// donc d'abord voir ça puis uh? .. :(
					if (routeLocale.getVoisin().equals(voisinAyantEnvoyeInfos)) {
						if (!agent.equals(routeVoisin.getVoisin().getAgent())) {
							// ok on a une route via ce voisin
							// est-ce que le coût a changé?
							// FIXME prob: on rajoute sans arrêt la distance
							// au cout!
							int nouveauCout =
									routeVoisin.getCout()
											+ voisinAyantEnvoyeInfos
													.getDistance();
							
							if (routeVoisin.getCout() == INFINI) {
								nouveauCout = INFINI;
							}
							
							boolean coutModifie =
									routeLocale.getCout() != nouveauCout;
							boolean doitChanger = false;
							if (initialisation) {
								if (coutModifie
										&& nouveauCout < routeLocale.getCout()) {
									doitChanger = true;
								}
							} else {
								// le coût ne doit pas changer si
								// l'agent de destination est un voisin!
								// sinon on va faire des tours inutiles
								// dans le réseau
								if (coutModifie
										&& !agentDestination
												.equals(voisinAyantEnvoyeInfos
														.getAgent())) {
									doitChanger = true;
								} else if (coutModifie
										&& agentDestination
												.equals(voisinAyantEnvoyeInfos
														.getAgent())) {
									List<Route> rTmps =
											distanceVector
													.get(voisinAyantEnvoyeInfos
															.getAgent());
									for (Route rTmp : rTmps) {
										// FIXME implement
									}
								}
							}
							if (doitChanger) {
								routeLocale.setCout(nouveauCout);
								distanceVectorLocalModifie = true;
							}
						}
					}
				}
			}
		}
		// FIXME commenter ou supprimer:
		LOGGER.info("après modifs ");
		afficherDistanceVector();
		LOGGER.info("  ");
		return distanceVectorLocalModifie;
	}



	/**
	 * Récupérer le coût actuellement estimé pour atteindre un voisin donné.
	 * 
	 * @param voisin
	 *        le voisin
	 * @return le coût actuellement estimé
	 */
	private int getCoutEstimePourAllerVers(final Voisin voisin) {
		int retVal = INFINI;
		if (distanceVector.containsKey(voisin.getAgent())) {
			List<Route> tmp = distanceVector.get(voisin.getAgent());
			int coutRouteMin = INFINI;
			for (Route route : tmp) {
				if (route.getCout() < coutRouteMin) {
					coutRouteMin = route.getCout();
				}
			}
			retVal = coutRouteMin;
		}
		if (retVal == INFINI) {
			throw new IllegalStateException(
					"Aucune route trouvée pour ce voisin, ça ne devrait pas arriver!");
		}
		if (retVal == 0) {
			throw new IllegalStateException("Un coût de 0 est impossible!");
		}
		return retVal;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Table de routage de: " + agent;
	}



	/**
	 * Affiche le distance vector actuel de cet agent.
	 */
	public void afficherDistanceVector() {
		LOGGER.info("DISTANCE VECTOR: " + agent.toString());
		Map<Agent, List<Route>> dv =
				agent.getTableDeRoutage().getDistanceVector();
		for (Agent agentDestination : dv.keySet()) {
			// LOGGER.info("Routes à destination de: "
			// + agentDestionation.toString());
			for (Route route : dv.get(agentDestination)) {
				LOGGER.info(route.toString());
			}
		}
		LOGGER.info("-----------------------------------------------------");
	}
}
