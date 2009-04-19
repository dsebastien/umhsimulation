package be.simulation.core.evenements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests de l'event list.
 * 
 * @author Dubois Sebastien
 */
public class EventListTest {
	private EventList	futureEventList;



	@Before
	public void setup() {
		futureEventList = new EventList();
	}
	
	/**
	 * Vérifier que l'ajout fonctionne correctement.
	 */
	@Test
	public void ajoutBasique() {
		Evenement evt = new EvenementBidon(new Double(5)); 
		
		futureEventList.planifierEvenement(evt);
		futureEventList.planifierEvenement(evt);
		assertEquals(futureEventList.getEvenementImminent(), evt);
		assertNull(futureEventList.getEvenementImminent());
	}
	
	/**
	 * Vérifier qu'on récupère bien les évènements d'un certain type.
	 */
	@Test
	public void recuperationEvenementImminentDeType() {
		Evenement evt = new EvenementBidon(new Double(5));
		futureEventList.planifierEvenement(evt);
		Evenement result = futureEventList.getEvenementImminent(evt.getClass());
		assertNotNull(result);
		assertEquals(evt, result);
		result = futureEventList.getEvenementImminent(evt.getClass());
		assertNull(result);
		
		Evenement premierEvenement = new EvenementBidonAussi(new Double(1));
		futureEventList.planifierEvenement(premierEvenement);
		futureEventList.planifierEvenement(evt);
		result = futureEventList.getEvenementImminent(evt.getClass());
		assertNull(result);
	}
	
	/**
	 * Vérifier qu'on ne récupère bien que les évènements imminents.
	 */
	@Test
	public void recuperationEvenementsImminents() {
		assertNotNull(futureEventList.getEvenementsImminents());
		assertTrue(futureEventList.getEvenementsImminents().isEmpty());
		
		Evenement e1 = new EvenementBidon(new Double(1));
		Evenement e2 = new EvenementBidon(new Double(1));
		futureEventList.planifierEvenement(e1);
		futureEventList.planifierEvenement(e2);
		List<Evenement> imminents = futureEventList.getEvenementsImminents();
		assertNotNull(imminents);
		assertFalse(imminents.isEmpty());
		assertEquals(imminents.size(), 2);
		assertNull(futureEventList.getEvenementImminent());
		
		Evenement e0 = new EvenementBidon(new Double(0));
		futureEventList.planifierEvenement(e1);
		futureEventList.planifierEvenement(e2);
		futureEventList.planifierEvenement(e0);
		imminents = futureEventList.getEvenementsImminents();
		assertNotNull(imminents);
		assertEquals(imminents.size(), 1);
	}
	
	/**
	 * Vérifier que les évènements sont bien triés selon leur temps prévu.
	 */
	@Test
	public void insertionTriee() {
		Evenement e1 = new EvenementBidon(new Double(1));
		Evenement e2 = new EvenementBidon(new Double(2));
		Evenement e3 = new EvenementBidon(new Double(3));
		Evenement e4 = new EvenementBidon(new Double(4));
		Evenement e5 = new EvenementBidon(new Double(5));
		futureEventList.planifierEvenement(e5);
		futureEventList.planifierEvenement(e1);
		futureEventList.planifierEvenement(e3);
		futureEventList.planifierEvenement(e2);
		futureEventList.planifierEvenement(e4);
		assertEquals(futureEventList.getEvenementImminent(), e1);
		assertEquals(futureEventList.getEvenementImminent(), e2);
		assertEquals(futureEventList.getEvenementImminent(), e3);
		assertEquals(futureEventList.getEvenementImminent(), e4);
		assertEquals(futureEventList.getEvenementImminent(), e5);
	}

}

