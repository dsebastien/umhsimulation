package be.simulation.utilitaires;

import java.text.DecimalFormat;

/**
 * Méthodes utilitaires diverses.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class Utilitaires {
	/**
	 * Le format utilisé pour les pourcentages.
	 */
	private static final DecimalFormat formatPourcentages = new DecimalFormat("###.00%");
	
	/**
	 * Calculer un pourcentage
	 * 
	 * @param nombre
	 *        le nombre
	 * @param total
	 *        le total
	 * @return le pourcentage
	 */
	public static double calculerPourcentage(final int nombre, final int total) {
		long result = 0;
		if (nombre > 0) {
			return ((double) nombre / (double) total) * 100;
		}
		return result;
	}
	
	/**
	 * Récupérer le pourcentage correspondant
	 * @param nombre le nombre
	 * @param total le total
	 * @return la réprésentation du pourcentage
	 */
	public static String pourcentage(final double nombre, final double total){
		double resultat = nombre/total;
		return formatPourcentages.format(resultat);
	}



	/**
	 * Arrondi un double à un certain nombre de chiffres après la virgule.
	 * 
	 * @param val
	 *        la valeur à arrondir
	 * @param chiffres
	 *        le nombre de chiffres après la virgule
	 * @return la valeur arrondie
	 */
	public static double arrondir(double val, int chiffres) {
		long facteur = (long) Math.pow(10, chiffres);
		// On déplace la virgule à droite suffisamment
		val = val * facteur;
		// On arrondit à l'entier le plus proche
		long tmp = Math.round(val);
		// On déplace la virgule à gauche suffisamment
		return (double) tmp / facteur;
	}
}
