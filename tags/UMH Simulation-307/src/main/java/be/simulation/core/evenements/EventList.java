package be.simulation.core.evenements;

import java.util.LinkedList;
import java.util.Queue;
import org.apache.log4j.Logger;

/**
 * Une EventList gère une liste d'évènements. Elle peut:<br />
 * <ul>
 * <li>planifier des évènements (les mettre à une position appropriée en
 * fonction de leur temps d'occurence prévu</li>
 * <li>récupérer l'évènement imminent</li>
 * </ul>
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class EventList {
	private static final Logger		LOG		=
													Logger
															.getLogger(EventList.class
																	.getName());

	// FIXME on ne pourra sans doute pas utiliser une vraie Queue étant donné
	// qu'il faut faire des ajouts un peu partout en fonction du temps
	private final Queue<Evenement>	fileEvenements	= new LinkedList<Evenement>();



	/**
	 * Planifier un évènement (le placer à une position appropriée dans la file
	 * d'évènements).
	 * 
	 * @param evt
	 *        l'évènement
	 */
    public void planifierEvenement(final Evenement evt) {
		LOG.info("Planification de l'évènemment: " + evt.toString());
		// FIXME voir où placer l'évènement en fonction de son temps d'occurence
		// prévu
    	fileEvenements.add(evt);
    }



	/**
	 * Récupérer l'évènement imminent s'il existe, null sinon.
	 * 
	 * @return l'évènement imminent s'il existe, null sinon
	 **/
	public Evenement getEvenementImminent() {
		return fileEvenements.poll();
	}



	/**
	 * Remettre la liste d'évènements à zéro.
	 */
    public void reset(){
        fileEvenements.clear();
    }
}
