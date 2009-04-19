package be.simulation.core.evenements;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests de la classe Evenement.
 * 
 * @author Dubois Sebastien
 */
public class EvenementTest {
	@Test
	public void testComparaison(){
		Evenement e1 = new EvenementBidon(new Double(0));
		Evenement e2 = new EvenementBidon(new Double(0));
		Evenement e3 = new EvenementBidon(new Double(1));
		Evenement e4 = new EvenementBidon(new Double(2));
		
		assertTrue(e1.compareTo(e2) == 0);
		assertTrue(e2.compareTo(e1) == 0);
		assertTrue(e1.compareTo(e3) < 0);
		assertTrue(e1.compareTo(e4) < 0);
		assertTrue(e4.compareTo(e1) > 0);
		assertTrue(e4.compareTo(e3) > 0);
		assertTrue(e3.compareTo(e1) > 0);
		assertTrue(e2.compareTo(e3) < 0);
	}
	

}
