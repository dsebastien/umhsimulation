package be.simulation.core.evenements;

/**
 * Notice d'évènement (event notice) basique. Tous les types d'évènements
 * héritent de cette classe. Les évènements peuvent être placés dans une
 * {@link EventList}.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public abstract class Evenement implements Comparable<Evenement> {
	// FIXME demander à l'assistant ce qu'il conseille pour la gestion du temps
	// (pas clair du tout pour moi)
	/**
	 * Temps auquel cet évènement doit se produire.
	 */
	private final long	tempsPrevu;



	/**
	 * Création d'un nouvel évènement. Au minimum un évènement à un type (donné
	 * par le type de l'objet) et un temps d'occurence.
	 * 
	 * @param tempsPrevu
	 *        le temps où cet évènement doit se produire
	 */
	public Evenement(final long tempsPrevu) {
		if (tempsPrevu < 0) {
			throw new IllegalArgumentException(
					"Le temps prévu pour un évènement ne peut pas être < 0");
		}
		this.tempsPrevu = tempsPrevu;
	}



	/**
	 * Comparaison de deux évènements
	 */
	public int compareTo(final Evenement o) {
		if (this.equals(o) || o.getTempsPrevu() == this.getTempsPrevu()) {
			return 0;
		} else if (this.getTempsPrevu() < o.getTempsPrevu()) {
			return -1;
		} else {
			return 1;
		}
	}



	/**
	 * Récupérer le temps auquel cet évènement doit se produire.
	 * 
	 * @return le temps auquel cet évènement doit se produire
	 */
	public long getTempsPrevu() {
		return tempsPrevu;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract String toString();
}
