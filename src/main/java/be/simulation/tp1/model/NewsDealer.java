package be.simulation.tp1.model;

public class NewsDealer {
	private final long	newsPapersPerDay;

	public NewsDealer(long newsPapersPerDay) {
		this.newsPapersPerDay = newsPapersPerDay;
	}
	
	public void runOneDay(long boughtNewsPapers) {
		// FIXME implement
		// FIXME based on the bought news papers number, use proba to see how
		// much we gain & store the results
	}
	
	// FIXME implement method to return the results
	// maybe try something with observable
	// NewsDealer extends Entity
	// Entity implements Observable
	// the simulation registers as Observer
	// callbacks with simulation step results ...
	// other interface for simulation for results gathering & so on??
}
