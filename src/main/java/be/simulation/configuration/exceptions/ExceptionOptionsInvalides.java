package be.simulation.configuration.exceptions;

/**
 * Exception lancée quand une option incorrecte est détectée au lancement de
 * l'application.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class ExceptionOptionsInvalides extends Exception {
	/**
	 * serial version uid.
	 */
	private static final long	serialVersionUID	= -3555306776472970714L;
	
	/**
	 * Constructeur avec uniquement un message.
	 * 
	 * @param msg
	 *        le message
	 */
	public ExceptionOptionsInvalides(final String msg) {
		super(msg);
	}



	/**
	 * Constructeur avec une exception englobée.
	 * 
	 * @param msg
	 *        le message
	 * @param t
	 *        l'exception englobée
	 */
	public ExceptionOptionsInvalides(final String msg, final Throwable t) {
		super(msg, t);
	}
}