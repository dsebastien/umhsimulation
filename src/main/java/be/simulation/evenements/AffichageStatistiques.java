package be.simulation.evenements;

import be.simulation.core.evenements.Evenement;

/**
 * Evenement déclenché quand la simulation doit réafficher les statistiques.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
//FIXME supprimer...
public class AffichageStatistiques extends Evenement {

	/**
	 * Constructeur par défaut.
	 * @param tempsPrevu le temps prévu
	 */
	public AffichageStatistiques(long tempsPrevu) {
		super(tempsPrevu);
	}

	@Override
	public String toString() {
		return "Affichage des statistiques au temps: "+getTempsPrevu();
	}
}
