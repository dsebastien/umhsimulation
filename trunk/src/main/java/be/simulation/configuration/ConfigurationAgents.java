package be.simulation.configuration;

import be.simulation.core.configuration.AbstractConfiguration;
import be.simulation.entites.Agent;

/**
 * Configuration des {@link Agent}.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class ConfigurationAgents extends AbstractConfiguration {
	/**
	 * Nombre d'hôtes par agent.
	 */
	private long	nombreHotes;
	/**
	 * Le nombre maximal de traitements simultanés.
	 */
	private int		nombreMaxTraitementsSimultanes;
	/**
	 * Taille du buffer des agents (>=0). 0 = infinie.
	 * 
	 * @deprecated On l'utilise dans un premier temps, mais ensuite ça devra
	 *             être différent pour chaque agent
	 */
	@Deprecated
	private long		tailleBuffer;
	/**
	 * Le taux de perte brutale des agents (0 <= taux < 1).
	 */
	private float	tauxPerteBrutale;
	/**
	 * Le temps de traitement d'un message (0 <= temps traitement <= 1). 0 =
	 * traitement instantané.
	 */
	private float	tempsTraitementMessage;



	/**
	 * Récupérer le nombre d'hôtes par agent.
	 * 
	 * @return le nombre d'hôtes par agent
	 */
	public long getNombreHotes() {
		return nombreHotes;
	}



	/**
	 * Récupérer le nombre maximal de traitements simultanés.
	 * 
	 * @return le nombre maximal de traitements simultanés
	 */
	public int getNombreMaxTraitementsSimultanes() {
		return nombreMaxTraitementsSimultanes;
	}



	/**
	 * Récupérer la taille du buffer des agents.
	 * 
	 * @return la taille du buffer des agents
	 * @deprecated On l'utilise dans un premier temps, mais ensuite ça devra
	 *             être différent pour chaque agent
	 */
	@Deprecated
	public long getTailleBuffer() {
		return tailleBuffer;
	}



	/**
	 * Récupérer le taux de perte brutale (0 <= taux < 1).
	 * 
	 * @return le taux de perte brutale
	 */
	public float getTauxPerteBrutale() {
		return tauxPerteBrutale;
	}



	/**
	 * Récupérer le temps de traitement d'un message (>=0). 0 = traitement
	 * instantané.
	 * 
	 * @return le temps de traitement d'un message
	 */
	public float getTempsTraitementMessage() {
		return tempsTraitementMessage;
	}



	/**
	 * Modifier le nombre d'hôtes par agent (effectif seulement après une
	 * réinitialisation de la simulation).
	 * 
	 * @param nombreHotes
	 *        le nombre d'hôtes par agent à utiliser
	 */
	public void setNombreHotes(final long nombreHotes) {
		if (nombreHotes <= 0) {
			throw new IllegalArgumentException(
					"Le nombre d'hôtes doit être >0!");
		}
		this.nombreHotes = nombreHotes;
	}



	/**
	 * Définir la taille du buffer des agents (>=0). 0 = infinie.
	 * 
	 * @param tailleBuffer
	 *        la taille du buffer des agents
	 * @deprecated On l'utilise dans un premier temps, mais ensuite ça devra
	 *             être différent pour chaque agent
	 */
	@Deprecated
	public void setTailleBuffer(final long tailleBuffer) {
		if (tailleBuffer < 0L) {
			throw new IllegalArgumentException(
					"La taille du buffer des agents doit etre >=0");
		}else if(tailleBuffer == 0L){
			this.tailleBuffer = Long.MAX_VALUE;
			
		}else{
			this.tailleBuffer = tailleBuffer;
		}
		
	}



	/**
	 * Définir le taux de perte brutale.
	 * 
	 * @param tauxPerteBrutale
	 *        le taux de perte brutale (0 <= taux < 1)
	 */
	public void setTauxPerteBrutale(final float tauxPerteBrutale) {
		if (tauxPerteBrutale < 0 || tauxPerteBrutale >= 1) {
			throw new IllegalArgumentException(
					"Le taux de perte brutale doit être tel que: 0 <= taux < 1");
		}
		this.tauxPerteBrutale = tauxPerteBrutale;
	}



	/**
	 * Définir le temps de traitement d'un message.
	 * 
	 * @param tempsTraitementMessage
	 *        le temps de traitement d'un message (>=0)
	 */
	public void setTempsTraitementMessage(final float tempsTraitementMessage) {
		if (tempsTraitementMessage < 0 || tempsTraitementMessage > 1) {
			throw new IllegalArgumentException(
					"Le temps de traitement d'un message pour un agent doit être tel que <= temps traitement <= 1");
		}
		this.tempsTraitementMessage = tempsTraitementMessage;
		
		// FIXME vérifier si ok
		if (tempsTraitementMessage == 0.0f) {
			this.nombreMaxTraitementsSimultanes = Integer.MAX_VALUE;
		} else {
			this.nombreMaxTraitementsSimultanes =
					(int) Math.floor(1 / tempsTraitementMessage);
		}
	}
}
