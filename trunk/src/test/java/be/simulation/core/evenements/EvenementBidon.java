package be.simulation.core.evenements;

/**
 * Classe utilisée pour les tests.
 * 
 * @author Dubois Sebastien
 */
public class EvenementBidon extends Evenement {
	public EvenementBidon(Double tempsPrevu) {
		super(tempsPrevu);
	}



	@Override
	public String toString() {
		return "" + this.getTempsPrevu();
	}
	
}