package it.univpm.GiAle.twitterProj.exception;

/**
 * Eccezione nel caso in cui venga inserito un filtro sbagliato
 * @author Verdolini Gian Paolo, Paolucci Alessio
 *
 */

public class WrongFilterException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public WrongFilterException(String message) {
		super(message);
	}
}
