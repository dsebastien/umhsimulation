package be.simulation.core;

import be.simulation.core.entites.AbstractEntiteSimulation;
import be.simulation.core.evenements.EventList;

/**
 * Classe générique pour les simulations. Regroupe le code commun à toute
 * simulation (chargement de la configuration de départ, gestion de l'horloge de
 * simulation, ...
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public abstract class AbstractSimulation extends AbstractEntiteSimulation {
	/**
	 * La FEL.
	 */
	private final EventList	futureEventList;

	/**
	 * Horloge de simulation
	 */
	private long		horloge;



	/**
	 * Création de la simulation.
	 */
	public AbstractSimulation() {
		futureEventList = new EventList();
	}



	/**
	 * Afficher les résultats de la simulation.
	 */
	public abstract void calculerEtAfficherResultats();



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
	}



	/**
	 * Ajouter un certain temps à l'horloge de simulation.
	 * 
	 * @param temps
	 *        le temps à ajouter
	 * @throws IllegalArgumentException
	 *         si le temps donné est null ou négatif (non autorisé)
	 */
	protected void ajouterTempsHorloge(final long temps) {
		if (temps < 0) {
			throw new IllegalArgumentException(
					"Le temps à ajouter à l'horloge ne peut pas être négatif!");
		}
		horloge += temps;
	}



	/**
	 * Démarre la simulation.
	 */
	public abstract void demarrer();



	/**
	 * Récupérer la FEL.
	 * 
	 * @return la FEL
	 */
	public EventList getFutureEventList() {
		return futureEventList;
	}


	/**
	 * Retourne l'horloge de simulation.
	 * 
	 * @return l'horloge de simulation
	 */
	public long getHorloge() {
		return horloge;
	}



	/**
	 * Réinitialise les informations basiques de simulation (FEL, horloge, ...).
	 */
	protected void resetBasicSimulation() {
		resetHorloge();
		futureEventList.reset();
	}



	/**
	 * Réinitialise l'horloge de simulation (0).
	 */
	protected void resetHorloge() {
		horloge = 0L;
	}
	
	/**
	 * Place le temps de simulation au temps donné.
	 * 
	 * @param temps
	 *        le temps auquel placer l'horloge (> 0)
	 * @throws IllegalArgumentException
	 *         si le temps donné est null ou <= 0
	 */
	protected void setHorloge(final long temps) {
		if (temps < 0) {
			throw new IllegalArgumentException(
					"Le temps auquel placer l'horloge doit être >= 0");
		}
		horloge = temps;
	}
}
