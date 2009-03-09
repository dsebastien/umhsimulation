package be.simulation.tp2;

/**
 * TP1 Entry point.
 * 
 * @author Dubois Sebastien
 */
public class Tp2 {
	public static void main(String[] args) {
		ServiceSimulation serviceSimulation = new ServiceSimulation();
		serviceSimulation.start();
		serviceSimulation.displayResults();
	}
}
