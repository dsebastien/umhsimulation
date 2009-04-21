package be.simulation.configuration;

import be.simulation.core.configuration.AbstractConfiguration;
import be.simulation.entites.Hote;

/**
 * Configuration des {@link Hote}.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class ConfigurationHotes extends AbstractConfiguration {
	/**
	 * Le nombre maximal de traitements simultanés.
	 */
	private int		nombreMaxTraitementsSimultanes;
	/**
	 * Taux de messages d'un hôte qui seront à destination d'un hôte relié à un
	 * autre agent (0 <= taux <= 1).
	 */
	private float	tauxMessagesVersAutreAgent;
	/**
	 * Le temps maximal entre deux envois d'un hôte (> 0).
	 */
	private int		tempsMaxInterEnvois;
	/**
	 * Le temps de traitement d'un message (0 <= temps traitement <= 1). 0 =
	 * traitement instantané.
	 */
	private float	tempsTraitementMessage;



	/**
	 * Récupérer le nombre maximal de traitements simultanés.
	 * 
	 * @return le nombre maximal de traitements simultanés
	 */
	public int getNombreMaxTraitementsSimultanes() {
		return nombreMaxTraitementsSimultanes;
	}



	/**
	 * Récupérer le taux de messages qui seront à destination d'un hôte relié à
	 * un autre agent.
	 * 
	 * @return le taux
	 */
	public float getTauxMessagesVersAutreAgent() {
		return this.tauxMessagesVersAutreAgent;
	}



	public int getTempsMaxInterEnvois() {
		return tempsMaxInterEnvois;
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
	 * Définir le taux de messages qui seront à destination d'un hôte relié à un
	 * autre agent (0 <= taux <= 1).
	 * 
	 * @param tauxMessagesVersAutreAgent
	 *        le taux
	 */
	public void setTauxMessagesVersAutreAgent(float tauxMessagesVersAutreAgent) {
		if (tauxMessagesVersAutreAgent < 0 || tauxMessagesVersAutreAgent > 1) {
			throw new IllegalArgumentException(
					"Le taux de messages vers un autre agent doit être tel que: 0 <= taux <= 1");
		}
		this.tauxMessagesVersAutreAgent = tauxMessagesVersAutreAgent;
	}



	/**
	 * Définir le temps maximal entre deux envois d'un hôte.
	 * 
	 * @param tempsMaxInterEnvois
	 *        le temps maximal entre deux envois d'un hôte.
	 */
	public void setTempsMaxInterEnvois(final int tempsMaxInterEnvois) {
		if (tempsMaxInterEnvois <= 0) {
			throw new IllegalArgumentException(
					"Le temps maximal entre deux envois d'un hôte doit être > 0");
		}
		this.tempsMaxInterEnvois = tempsMaxInterEnvois;
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
