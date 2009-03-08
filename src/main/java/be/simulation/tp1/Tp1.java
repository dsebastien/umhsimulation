package be.simulation.tp1;

/**
 * TP1 Entry point.
 * 
 * @author Dubois Sebastien
 */
public class Tp1 {
	public static void main(String[] args) {
		NewsDealerSimulation simulation = new NewsDealerSimulation();
		// first run with the default settings
		simulation.start();
		simulation.displayResults();
		
		// we change the simulation parameters before running it again
		// the new parameters could be injected from the command line
		simulation.getConfig().setNewsPapersPerDay(60);
		simulation.getConfig().setDaysToSimulate(100000);
		simulation.reset();
		simulation.start();
		simulation.displayResults();
	}
}
