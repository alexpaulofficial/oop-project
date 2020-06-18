package it.univpm.GiAle.twitterProj.exception;

/**
 * Eccezione nel caso in cui venga restituita una lista vuota
 * @author Verdolini Gian Paolo, Paolucci Alessio
 *
 */

public class GetTweetException extends Exception {
	
private static final long serialVersionUID = 1L;

	/**
	 * Viene stampato il messaggio d'errore passato come parametro
	 * @param message Messaggio di errore personalizzato
	 */

		
		public GetTweetException(String message) {
		super(message);
	
	}

}
