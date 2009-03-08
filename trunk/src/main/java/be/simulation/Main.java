package be.simulation;

import be.simulation.tp1.NewsDealerSimulation;

public class Main {
	public static void main(String[] args) {
		NewsDealerSimulation simulation = new NewsDealerSimulation();
		simulation.start();
		simulation.displayResults();
		simulation.getConfig().setDaysToSimulate(5L);
		simulation.reset();
		simulation.start();
	}
}
