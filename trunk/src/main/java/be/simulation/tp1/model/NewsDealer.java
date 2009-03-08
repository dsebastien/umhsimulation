package be.simulation.tp1.model;

import java.util.Random;

public class NewsDealer {
	private final Random	demandGenerator			= new Random();
	private final Random	dayTypeGenerator		= new Random();
	private final double	buyPrice				= 0.33;
	private final double	sellPrice				= 0.5;
	private final double	scrapPrice				= 0.05;
	private final double	profitPerNewspaper		= sellPrice - buyPrice;
	private final int		newspapersPerDay;
	private int				simulatedDays			= 0;
	private double			totalProfit				= 0;
	private long			totalAskedNewspapers	= 0;



	public NewsDealer(int newsPapersPerDay) {
		this.newspapersPerDay = newsPapersPerDay;
	}



	/**
	 * Generates a random day type.
	 * 
	 * @return the generated day type
	 */
	private DayType generateDayType() {
		// Good: proba: 0.35 cumulative: 0.35 digits: 01-35
		// Fair: proba: 0.45 cumulative: 0.80 digits: 36-80
		// Poor: proba: 0.20 cumulative: 1.00 digits: 81-00
		int dayType = dayTypeGenerator.nextInt(100) + 1;
		DayType returnValue = null;
		if (dayType <= 35) {
			returnValue = DayType.GOOD;
		} else if (dayType <= 80) {
			returnValue = DayType.FAIR;
		} else {
			returnValue = DayType.POOR;
		}
		return returnValue;
	}



	/**
	 * Generates the newspaper demand based on the day type.
	 * 
	 * @param dayType
	 *        the day type
	 * @return the newspaper demand
	 */
	private int generateNewspaperDemand(DayType dayType) {
		int returnValue = 0;
		int randomDemand = demandGenerator.nextInt(100) + 1;
		// Random digit assignements (book p41)
		if (dayType == DayType.GOOD) {
			if (randomDemand <= 3) {
				returnValue = 40;
			} else if (randomDemand <= 8) {
				returnValue = 50;
			} else if (randomDemand <= 23) {
				returnValue = 60;
			} else if (randomDemand <= 43) {
				returnValue = 70;
			} else if (randomDemand <= 78) {
				returnValue = 80;
			} else if (randomDemand <= 93) {
				returnValue = 90;
			} else {
				returnValue = 100;
			}
		} else if (dayType == DayType.FAIR) {
			if (randomDemand <= 10) {
				returnValue = 40;
			} else if (randomDemand <= 28) {
				returnValue = 50;
			} else if (randomDemand <= 68) {
				returnValue = 60;
			} else if (randomDemand <= 88) {
				returnValue = 70;
			} else if (randomDemand <= 96) {
				returnValue = 80;
			} else {
				returnValue = 90;
			}
		} else if (dayType == DayType.POOR) {
			if (randomDemand <= 44) {
				returnValue = 40;
			} else if (randomDemand <= 66) {
				returnValue = 50;
			} else if (randomDemand <= 82) {
				returnValue = 60;
			} else if (randomDemand <= 94) {
				returnValue = 70;
			} else {
				returnValue = 80;
			}
		}
		return returnValue;
	}



	public void runOneDay() {
		// generate the day type
		DayType dayType = generateDayType();
		// generate the newspaper demand for the day
		int newspaperDemand = generateNewspaperDemand(dayType);
		// update the counters
		totalAskedNewspapers += newspaperDemand;
		int soldNewspapers = Math.min(newspaperDemand, newspapersPerDay);
		int lostNewspapers = Math.max(0, newspaperDemand - newspapersPerDay);
		int scrapNewspapers = Math.max(0, newspapersPerDay - newspaperDemand);
		simulatedDays++;
		double dailyProfit =
				(sellPrice * soldNewspapers) - (newspapersPerDay * buyPrice)
						- (profitPerNewspaper * lostNewspapers)
						+ (scrapPrice * scrapNewspapers);
		totalProfit += dailyProfit;
	}



	public double getAVGProfit() {
		return totalProfit / simulatedDays;
	}



	public double getAVGNewspaperDemandPerDay() {
		return (double) totalAskedNewspapers / simulatedDays;
	}
}
