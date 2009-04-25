package be.simulation.messages;

import be.simulation.entites.Hote;

/**
 * Un message simple.
 * 
 * @author Dubois Sebastien
 * @author Regnier Frederic
 * @author Mernier Jean-François
 */
public class MessageSimple extends Message {
	/**
	 * Créer un faux message (avec 1 émission). Utilisé pour le calcul des
	 * statistiques
	 * 
	 * @return un message bidon
	 */
	public static MessageSimple creerFauxMessage() {
		return new MessageSimple(new Hote(), new Hote(), 0, 1, 0);
	}
	/**
	 * le numéro d'émission de ce message (à chaque réexpédition ce numéro
	 * augmente).
	 */
	private final int	numeroEmission;
	/**
	 * le numéro identifiant de ce message.
	 */
	private final int	numeroMessage;
	/**
	 * temps de simulation au moment de l'émission de ce message.
	 */
	//FIXME Déplacer dans la super classe! Utile pour les accusés aussi!
	//et ajouter la mise à jour de l'info (ajout au constructeur + checks)
	private final long	tempsEmission;



	/**
	 * Message simple
	 */
	public MessageSimple(final Hote source, final Hote destination,
			final int numeroMessage, final int numeroEmission,
			final long tempsEmission) {
		super(source, destination);
		if (tempsEmission < 0) {
			throw new IllegalArgumentException(
					"Le temps d'émission ne peut pas etre < 0");
		}
		if (numeroEmission <= 0) {
			throw new IllegalArgumentException(
					"Le numéro d'émission ne peut pas être <= 0");
		}
		this.numeroMessage = numeroMessage;
		this.numeroEmission = numeroEmission;
		this.tempsEmission = tempsEmission;
	}



	/**
	 * Récupérer le numéro d'émission de ce message.
	 * 
	 * @return le numéro d'émission de ce message
	 */
	public int getNumeroEmission() {
		return numeroEmission;
	}



	/**
	 * Récupérer le numéro de ce message.
	 * 
	 * @return le numéro de ce message.
	 */
	public int getNumeroMessage() {
		return numeroMessage;
	}



	/**
	 * Récupérer le temps de simulation auquel ce message a été émis.
	 * 
	 * @return le temps de simulation auquel ce message a été émis.
	 */
	public long getTempsEmission() {
		return tempsEmission;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Message simple de " + getSource().getNumero() + " (Agent "
				+ getSource().getAgent().getNumero() + ") à "
				+ getDestination().getNumero() + " (Agent "
				+ getDestination().getAgent().getNumero() + ")";
	}
}
