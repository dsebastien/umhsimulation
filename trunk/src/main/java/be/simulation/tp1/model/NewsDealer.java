package be.simulation.tp1.model;

import java.util.Random;

public class NewsDealer {

	private final double buyPrice;
	private final double sellPrice;
	private final double profitPerNewspaper;
	private final double scrapPrice;
	private final long newspapersPerDay;
	private final long simulatedDays = 0;
	private double totalProfit;
	private long totalAskedNewspapers = 0;
	private final Random demandGenerator;

	public NewsDealer(long newsPapersPerDay) {
		this.newspapersPerDay = newsPapersPerDay;
		demandGenerator = new Random();
		buyPrice = 0.33;
		sellPrice = 0.5;
		scrapPrice = 0.05;
		profitPerNewspaper = sellPrice - buyPrice;
	}

	public void runOneDay(DayType dayType) {
		// FIXME implement
		// generate the number of sold news papers for this day
		// FIXME based on the bought news papers number, use proba to see how
		// much we gain & store the results

		// int newstypeR = type.nextInt(100)+1;
		// int newsdemandR = demande.nextInt(100)+1;
		// int qtty=0;
		//	        
		// if (newstypeR <=35) { // good
		//	            
		// if (newsdemandR <=3)
		// qtty = 40;
		// else if (newsdemandR <=8)
		// qtty = 50;
		// else if (newsdemandR <=23)
		// qtty = 60;
		// else if (newsdemandR <= 43)
		// qtty = 70;
		// else if (newsdemandR <=78)
		// qtty = 80;
		// else if (newsdemandR <= 93)
		// qtty = 90;
		// else //if (newsdemandR <=100)
		// qtty = 100;
		//	            
		// }else if (newstypeR <=80 ) { // fair
		//	            
		// if (newsdemandR <=10)
		// qtty = 40;
		// else if (newsdemandR <=28)
		// qtty = 50;
		// else if (newsdemandR <=68)
		// qtty = 60;
		// else if (newsdemandR <= 88)
		// qtty = 70;
		// else if (newsdemandR <=96)
		// qtty = 80;
		// else //if (newsdemandR <= 100)
		// qtty = 90;
		//	            
		// }else //if (newstypeR <=100) { // poor
		// {
		// if (newsdemandR <=44)
		// qtty = 40;
		// else if (newsdemandR <=66)
		// qtty = 50;
		// else if (newsdemandR <=82)
		// qtty = 60;
		// else if (newsdemandR <= 94)
		// qtty = 70;
		// else // if (newsdemandR <=100)
		// qtty = 80;
		// }
		//	        
		//	        
		// this.totalNewsAsked += qtty;
		// int nbrVendu = Math.min(qtty,orderedQtty);
		// int nbrLost = Math.max(0,qtty-orderedQtty);
		// int nbrScrap = Math.max(0,orderedQtty-qtty);
		// nbRun++;
		// double dailyProfit = (PVENTE*nbrVendu) - (orderedQtty*PACHAT) -
		// (PROFIT*nbrLost) + (PSCRAP*nbrScrap);
		//	   
		// totalProfit+= dailyProfit;
		// return dailyProfit;
	}

	// FIXME implement method to return the results
	// maybe try something with observable
	// NewsDealer extends Entity
	// Entity implements Observable
	// the simulation registers as Observer
	// callbacks with simulation step results ...
	// other interface for simulation for results gathering & so on??

	public double getAVGProfit() {
		return totalProfit / simulatedDays;
	}

	public double getAVGAskedNewspapers() {
		return (double) totalAskedNewspapers / simulatedDays;
	}
}
