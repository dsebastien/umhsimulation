package be.simulation.core.utilitaires;

import java.util.Random;

/**
 * Méthodes utilitaires pour la génération de nombres pseudo aléatoires;
 * distributions exponentielles de probabilités, ...
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public final class RandomUtils {
	/**
	 * Constructeur privé.
	 */
	private RandomUtils() {
	}



	/**
	 * Récupérer un nombre pseudo aléatoire d'une distribution exponentielle de
	 * probabilités.
	 * 
	 * @param prng
	 *        le générateur de nombres pseudo aléatoires à utiliser
	 * @param moyenne
	 *        la moyenne de la distribution exponentielle.
	 * @return un nombre pseudo aléatoire
	 */
	public static double exponential(final Random prng, final double moyenne) {
		return -moyenne * Math.log(prng.nextDouble());
	}
}
