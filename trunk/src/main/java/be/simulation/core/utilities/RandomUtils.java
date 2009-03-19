package be.simulation.core.utilities;

import java.util.Random;

/**
 * Random utilities (producing pseudo-random numbers, exponential probability
 * distributions, ...).
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-Francois
 */
public final class RandomUtils {

	/**
	 * Private constructor.
	 */
	private RandomUtils() {
	}

	/**
	 * Get a pseudo-random number from an exponential distribution.
	 *
	 * @param prng
	 *        the pseudo-random number generator to use
	 * @param mean
	 *        mean of the exponential distribution
	 * @return a pseudo-random number in the exponential distribution
	 */
	public static double exponential(Random prng, double mean) {
		return -mean * Math.log(prng.nextDouble());
	}

}
