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
     * Récupérer le taux de messages qui seront à destination d'un hôte relié à
     * un autre agent.
     * 
     * @return le taux
     */
    public float getTauxMessagesVersAutreAgent() {
        return this.tauxMessagesVersAutreAgent;
    }

    /**
     * Définir le taux de messages qui seront à destination d'un hôte relié à un
     * autre agent (0 <= taux <= 1).
     * 
     * @param tauxMessagesVersAutreAgent
     *            le taux
     */
    public void setTauxMessagesVersAutreAgent(float tauxMessagesVersAutreAgent) {
        this.tauxMessagesVersAutreAgent = tauxMessagesVersAutreAgent;
    }
}
