package be.simulation.core.evenements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Une EventList gère une liste d'évènements. Elle peut:<br />
 * <ul>
 * <li>planifier des évènements (les mettre à une position appropriée en
 * fonction de leur temps d'occurence prévu</li>
 * <li>récupérer l'évènement imminent</li>
 * <li>récupérer des évènements imminents d'un type donné</li>
 * <li>récupérer une liste d'évènements imminents</li>
 * </ul>
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class EventList {
	private static final Logger			LOGGER		=
															Logger
																	.getLogger(EventList.class
																			.getName());
	/**
	 * L'event list (FEL) est maintenue grâce à une liste chaînée. L'insertion
	 * se fait de manière triée de manière à garder la chronologie.
	 */
	private final LinkedList<Evenement>	eventList	=
															new LinkedList<Evenement>();



	/**
	 * Récupére l'évènement imminent s'il existe et le supprime de la FEL. Si
	 * aucun évènement, retourne null.
	 * 
	 * @return l'évènement imminent s'il existe, null sinon
	 **/
	public Evenement getEvenementImminent() {
		Evenement evenementImminent = null;
		if (!eventList.isEmpty()) {
			evenementImminent = eventList.pop();
		}
		// if (evenementImminent == null) {
		// LOGGER
		// .warn("Il n'y a aucun évènement imminent, ceci ne devrait pas se produire car la FEL devrait au moins contenir l'évènement de fin de simulation!");
		// }
		return evenementImminent;
	}



	/**
	 * Méthode qui retourne un évènement imminent d'un certain type (ce qui
	 * permet de traiter en priorité certains évènements comme les accusés de
	 * réception ou les messages de routage).
	 * 
	 * @param typeEvenement
	 *        le type d'évènement recherché
	 * @return l'évènement trouvé ou null si aucun
	 */
	public Evenement getEvenementImminent(
			final Class<? extends Evenement> typeEvenement) {
		Evenement retVal = null;
		Iterator<Evenement> it = eventList.iterator();
		int i = 0;
		while (it.hasNext()) {
			Evenement tmp = it.next();
			if (tmp.getClass().equals(typeEvenement)) {
				// si cet évènement n'est pas le premier on doit vérifier qu'il
				// est bien prévu au temps imminent également,
				// sinon inutile de le retourner.
				if (i > 0) {
					Evenement imminent = eventList.peekFirst();
					if (tmp.getTempsPrevu() == imminent.getTempsPrevu()) {
						retVal = tmp;
					}
				} else {
					retVal = tmp;
				}
			}
			i++;
		}
		if (retVal != null) {
			eventList.remove(retVal);
		}
		return retVal;
	}



	/**
	 * Retourne la liste des évènements imminents (prévus au même temps de
	 * simulation).
	 * 
	 * @return les évènements imminents
	 */
	public List<Evenement> getEvenementsImminents() {
		List<Evenement> retVal = new ArrayList<Evenement>();
		Evenement actuel = null;
		Evenement precedent = null;
		while (!eventList.isEmpty()) {
			actuel = eventList.peekFirst();
			if (precedent == null
					|| (precedent != null && actuel.compareTo(precedent) == 0)) {
				retVal.add(actuel);
				eventList.remove(actuel);
				precedent = actuel;
			} else if (precedent != null && actuel.compareTo(precedent) > 0) {
				break;
			}
		}
		return retVal;
	}



	/**
	 * Planifier un évènement (le placer à une position appropriée dans la FEL
	 * -> insertion triée). Les doublons sont ignorés (si on essaie de placer
	 * deux fois la même instance d'un objet sur la FEL).
	 * 
	 * @param evt
	 *        l'évènement
	 */
	public void planifierEvenement(final Evenement evt) {
		if (evt == null) {
			throw new IllegalArgumentException(
					"L'évènement à planifier ne peut pas être null");
		}
		LOGGER.trace("Planification d'un évènement: " + evt.toString());
		int index = Collections.binarySearch(eventList, evt);
		if (index < 0) {
			eventList.add(-index - 1, evt);
		} else if (!eventList.contains(evt)) {
			// cas où une autre instance du même type d'évènement est déjà sur
			// la FEL: on fait l'ajout en fin de liste et on trie
			eventList.add(evt);
			Collections.sort(eventList);
		}
	}



	/**
	 * Remettre la liste d'évènements à zéro.
	 */
	public void reset() {
		eventList.clear();
	}
}
