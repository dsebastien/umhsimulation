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
	 * Le temps de traitement d'un message (>=0). 0 = traitement instantané.
	 */
    private float	tempsTraitementMessage;

	/**
	 * Taille du buffer des agents (>=0). 0 = infinie.
	 * 
	 * @deprecated On l'utilise dans un premier temps, mais ensuite ça devra
	 *             être différent pour chaque agent
	 */
	@Deprecated
	private int		tailleBuffer;

	/**
	 * Récupérer le nombre d'hôtes par agent.
	 * 
	 * @return le nombre d'hôtes par agent
	 */
    public long getNombreHotes() {
        return nombreHotes;
    }
    
	/**
	 * Récupérer la taille du buffer des agents.
	 * 
	 * @return la taille du buffer des agents
	 * @deprecated On l'utilise dans un premier temps, mais ensuite ça devra
	 *             être différent pour chaque agent
	 */
	@Deprecated
	public int getTailleBuffer() {
		return tailleBuffer;
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
     *            le nombre d'hôtes par agent à utiliser
     */
    public void setNombreHotes(final long nombreHotes) {
        this.nombreHotes = nombreHotes;
    }

    /**
	 * Définir la taille du buffer des agents (>=0). 0 = infinie.
	 * 
	 * @param tailleBufferAgents
	 *        la taille du buffer des agents
	 * @deprecated On l'utilise dans un premier temps, mais ensuite ça devra
	 *             être différent pour chaque agent
	 */
	@Deprecated
	public void setTailleBuffer(final int tailleBufferAgents) {
		this.tailleBuffer = tailleBufferAgents;
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