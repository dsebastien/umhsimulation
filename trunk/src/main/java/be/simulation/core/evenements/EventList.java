package be.simulation.core.evenements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import be.simulation.evenements.HoteTimeoutReceptionAccuse;
import be.simulation.messages.MessageSimple;

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
	private static final Logger LOGGER = Logger.getLogger(EventList.class
			.getName());
	/**
	 * L'event list (FEL) est maintenue grâce à une liste chaînée. L'insertion
	 * se fait de manière triée de manière à garder la chronologie.
	 */
	private final LinkedList<Evenement> eventList = new LinkedList<Evenement>();

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
		return evenementImminent;
	}
	
	/**
	 * Est-ce que la liste d'évènements est vide?
	 * @return vrai si la liste d'évènements est vide
	 */
	public boolean estVide(){
		return eventList.isEmpty();
	}

	/**
	 * Méthode qui retourne un évènement imminent d'un certain type (ce qui
	 * permet de traiter en priorité certains évènements comme les accusés de
	 * réception ou les messages de routage).
	 * 
	 * @param typeEvenement
	 *            le type d'évènement recherché
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
	 * @deprecated
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
	 *            l'évènement
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
			
			// on trie puisque l'évènement qu'on vient d'ajouter n'est (à priori) pas à sa place
			Collections.sort(eventList);
		}
	}

	/**
	 * Remettre la liste d'évènements à zéro.
	 */
	public void reset() {
		eventList.clear();
	}
	
	
	
	
	/**
	 * Essaie de trouver un évènement timeout correspondant à un message donné.
	 * 
	 * REMARQUE: par simplicité nous avons placé cette méthode ici mais si le but avait été de réutiliser le code, nous aurions pu la placer dans une sous classe
	 * @param messageOrigine le message d'origine
	 * @return l'évènement correspondant s'il existe, NULL sinon
	 */
	public HoteTimeoutReceptionAccuse trouverEvenementTimeoutPourMessage(final MessageSimple messageOrigine) {
		if(messageOrigine == null){
			throw new IllegalArgumentException("Le message d'origine ne peut pas être null!");
		}
		
		HoteTimeoutReceptionAccuse retVal = null;
		
		for(Evenement evt: eventList){
			if(evt instanceof HoteTimeoutReceptionAccuse){
				HoteTimeoutReceptionAccuse tmp = (HoteTimeoutReceptionAccuse) evt;
				
				if(tmp.getMessage().equals(messageOrigine)){
					// on l'a trouvé!
					retVal = tmp;
				}
			}
		}
		return retVal;
	}
	
	/**
	 * Supprime un évènement de la FEL
	 * @param evenement l'évènement à supprimer de la FEL
	 * @return vrai si l'évènement a bien été supprimé
	 */
	public void supprimer(final Evenement evenement){
		boolean resultat = eventList.remove(evenement);
		if(resultat == false){
			LOGGER.warn("L'évènement n'a pas été supprimé, il n'était apparemment pas dans l'event list");
		}
	}
}
