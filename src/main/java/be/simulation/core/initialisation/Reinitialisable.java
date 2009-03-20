package be.simulation.core.initialisation;

/**
 * Interface indiquant qu'une entité donnée peut etre réinitialisée.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-Francois
 */
public interface Reinitialisable {
	/**
	 * Rétablit l'état initial de l'entité concernée.
	 */
	public void reset();
	
	// FIXME implementer ça sur abstract simulation entite par exemple
	// idem pour tout ce qui peut être commun
}
