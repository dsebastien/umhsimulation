package be.simulation.routage;

import be.simulation.entites.Agent;

/**
 * Un voisin d'un agent.
 * 
 * @author duboiss
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class Voisin {
	/**
	 * L'agent voisin.
	 */
	private final Agent agent;

	/**
	 * La distance de ce voisin.
	 */
	private final int distance;

	/**
	 * Constructeur
	 * 
	 * @param agent
	 *            l'agent voisin
	 * @param distance
	 * 			  la distance de se voisin
	 */
	public Voisin(final Agent agent, final int distance) {
		if (agent == null) {
			throw new IllegalArgumentException(
					"L'agent voisin ne peut pas être null!");
		}
		if (distance < 0) {
			throw new IllegalArgumentException(
					"La distance pour se rendre jusqu'à l'agent voisin ne peut pas être < 0");
		}
		this.agent = agent;
		this.distance = distance;
	}

	/**
	 * Récupérer l'agent voisin.
	 * 
	 * @return l'agent voisin
	 */
	public Agent getAgent() {
		return agent;
	}

	/**
	 * Récupérer la distance pour se rendre jusqu'à ce voisin.
	 * 
	 * @return la distance pour se rendre jusqu'à ce voisin
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(!(obj instanceof Voisin)){
			return false;
		}
		
		Voisin tmp = (Voisin) obj;
		
		if(this.agent.equals(tmp.getAgent()) && this.distance == tmp.getDistance()){
			return true;
		}else{
			return false;
		}
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result;
        result = agent.hashCode();
        result = 29 * result + distance;
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
    	return "Voisin: "+getAgent().toString()+" (distance: "+getDistance()+")";
    }

}
