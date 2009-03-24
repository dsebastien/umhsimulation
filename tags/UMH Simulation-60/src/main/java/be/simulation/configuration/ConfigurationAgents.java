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
    private long nombreHotes;

    /**
     * Le taux de perte brutale des agents (0 <= taux < 1).
     */
    private float tauxPerteBrutale;

    /**
     * Récupérer le nombre d'hôtes par agent.
     * 
     * @return le nombre d'hôtes par agent
     */
    public long getNombreHotes() {
        return nombreHotes;
    }

    /**
     * Récupérer le taux de perte brutale.
     * 
     * @return le taux de perte brutale
     */
    public float getTauxPerteBrutale() {
        return tauxPerteBrutale;
    }

    /**
     * Modifier le nombre d'hôtes par agent (effectif seulement après une
     * réinitialisation de la simulation).
     * 
     * @param nombreHotes
     *            le nombre d'hôtes par agent à utiliser
     */
    public void setNombreHotes(final long nombreHotes) {
        this.nombreHotes = nombreHotes;
    }

    /**
     * Définir le taux de perte brutale.
     * 
     * @param tauxPerteBrutale
     *            le taux de perte brutale (0 <= taux < 1)
     */
    public void setTauxPerteBrutale(final float tauxPerteBrutale) {
        this.tauxPerteBrutale = tauxPerteBrutale;
    }
}
