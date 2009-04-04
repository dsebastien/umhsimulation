package be.simulation.core;

import org.apache.log4j.Logger;
import be.simulation.core.entites.AbstractEntiteSimulation;
import be.simulation.core.evenements.EventList;

/**
 * Classe générique pour les simulations. Regroupe le code commun à toute
 * simulation (chargement de la configuration de départ, gestion de l'horloge de
 * simulation, ...
 * 
 * @param <T>
 *        Le type de configuration utilisée par la simulation
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public abstract class AbstractSimulation extends AbstractEntiteSimulation {
	protected static final Logger	LOGGER	=
													Logger
															.getLogger(AbstractSimulation.class
																	.getName());
	/**
	 * La FEL.
	 */
	private final EventList			futureEventList;
	/**
	 * Horloge de simulation
	 */
	// FIXME décider de ce qu'on utilise pour le suivi du temps de simulation
	// (long ou double?)
	private Double					horloge;



	/**
	 * Création de la simulation.
	 */
	public AbstractSimulation() {
		// création de la FEL
		futureEventList = new EventList();
	}



	/**
	 * Afficher les résultats de la simulation.
	 */
	public abstract void afficherResultats();



	/**
	 * Ajouter un certain temps à l'horloge de simulation.
	 * 
	 * @param temps
	 *        le temps à ajouter
	 * @throws IllegalArgumentException
	 *         si le temps donné est null ou négatif (non autorisé)
	 */
	protected void ajouterTempsHorloge(Double temps) {
		if (temps == null) {
			throw new IllegalArgumentException(
					"Le temps à ajouter à l'horloge ne peut pas être null!");
		} else {
			if (temps < 0) {
				throw new IllegalArgumentException(
						"Le temps à ajouter à l'horloge ne peut pas être négatif!");
			}
		}
		horloge = Double.valueOf(horloge + temps);
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
	protected EventList getFutureEventList() {
		return futureEventList;
	}



	/**
	 * Retourne l'horloge de simulation.
	 * 
	 * @return l'horloge de simulation
	 */
	protected Double getHorloge() {
		return horloge;
	}

	/**
	 * Initialise ou réinitialise le système (retour à l'état de départ).
	 */
	public abstract void reset();



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
		horloge = Double.valueOf(0);
	}



	/**
	 * Place le temps de simulation au temps donné.
	 * 
	 * @param temps
	 *        le temps auquel placer l'horloge (> 0)
	 * @throws IllegalArgumentException
	 *         si le temps donné est null ou <= 0
	 */
	protected void setHorloge(Double temps) {
		if (temps == null || temps < 0) {
			throw new IllegalArgumentException(
					"Le temps auquel placer l'horloge doit être > 0");
		}
		horloge = temps;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		LOGGER.info("Initialisation de la simulation...");
		reset();
		LOGGER.info("Simulation initialisée");
	}
}
