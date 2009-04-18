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
     * Taux de messages d'un hôte qui seront à destination d'un hôte relié à un
     * autre agent (0 <= taux <= 1).
     */
    private float tauxMessagesVersAutreAgent;
	/**
	 * Le temps maximal entre deux envois d'un hôte (> 0).
	 */
	private int		tempsMaxInterEnvois;
	/**
	 * Le temps de traitement d'un message (>=0). 0 = traitement instantané.
	 */
	private float	tempsTraitementMessage;



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
        this.tauxMessagesVersAutreAgent = tauxMessagesVersAutreAgent;
    }

    public void setTempsMaxInterEnvois(int tempsMaxInterEnvois) {
		this.tempsMaxInterEnvois = tempsMaxInterEnvois;
	}

    /**
	 * Définir le temps de traitement d'un message.
	 * 
	 * @param tempsTraitementMessage
	 *        le temps de traitement d'un message (>=0)
	 */
	public void setTempsTraitementMessage(final float tempsTraitementMessage) {
		this.tempsTraitementMessage = tempsTraitementMessage;
	}
}
