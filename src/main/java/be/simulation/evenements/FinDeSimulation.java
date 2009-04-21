package be.simulation.evenements;

import be.simulation.core.evenements.Evenement;

/**
 * Evenement spécial indiquant la fin de la simulation.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class FinDeSimulation extends Evenement {
	/**
	 * Evenement de fin de simulation.
	 */
	public FinDeSimulation(final long tempsPrevu) {
		super(tempsPrevu);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Fin de simulation au temps " + getTempsPrevu();
	}
}
