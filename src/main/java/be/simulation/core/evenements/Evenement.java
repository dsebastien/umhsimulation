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
	private final Double	tempsPrevu;



	/**
	 * Création d'un nouvel évènement. Au minimum un évènement à un type (donné
	 * par le type de l'objet) et un temps d'occurence.
	 * 
	 * @param tempsPrevu
	 *        le temps où cet évènement doit se produire
	 */
	public Evenement(final Double tempsPrevu) {
		this.tempsPrevu = tempsPrevu;
	}



	/**
	 * Récupérer le temps auquel cet évènement doit se produire.
	 * 
	 * @return le temps auquel cet évènement doit se produire
	 */
	public Double getTempsPrevu() {
		return tempsPrevu;
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract String toString();



	/**
	 * Comparaison de deux évènements
	 */
	public int compareTo(Evenement o) {
		if (this.equals(o)
				|| o.getTempsPrevu().doubleValue() == this.getTempsPrevu()
						.doubleValue()) {
			return 0;
		} else if (this.getTempsPrevu().doubleValue() < o.getTempsPrevu()
				.doubleValue()) {
			return -1;
		} else {
			return 1;
		}
	}
}