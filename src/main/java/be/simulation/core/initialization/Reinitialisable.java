package be.simulation.core.initialization;

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
}
