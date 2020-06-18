package it.univpm.GiAle.twitterProj.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Classe del Tweet che permette di salvare solo i dati necessari di ogni Tweet
 */
public class Tweet {
	
	/** Data di creazione Tweet */
	private String created_at;
	
	/** ID del tweet*/
	private long id;
	
	/** Il contenuto del Tweet */
	private String text;
	
	/** Numero dei Retweets */
	private int retweet_count;
	
	/** Numero dei Likes */
	private int favorite_count;
	
	/**
	 * Costruttore del Tweet
	 */
	public Tweet() {
		super();
		this.setCreated_at(null);
		this.id = 0;
		this.text = "";
		this.favorite_count = 0;
		this.retweet_count = 0;
	}
	
	/**
	 * Restituisce il numero dei Retweets
	 *
	 * @return Il retweet count
	 */
	public int getRetweet_count() {
		return retweet_count;
	}
	
	/**
	 * Setta il retweet count.
	 *
	 * @param retweet_count Il nuovo retweet count
	 */
	public void setRetweet_count(int retweet_count) {
		this.retweet_count = retweet_count;
	}
	
	/**
	 * Restituisce il numero dei Retweets
	 *
	 * @return Il favorite count
	 */
	public int getFavorite_count() {
		return favorite_count;
	}
	
	/**
	 * Setta il favorite count.
	 *
	 * @param favorite_count Il nuovo favorite count
	 */
	public void setFavorite_count(int favorite_count) {
		this.favorite_count = favorite_count;
	}
	
	/**
	 * Setta l'ID
	 *
	 * @param id Il nuovo ID
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Istanzia un nuovo tweet con i parametri passati
	 *
	 * @param created_at Data di creazione
	 * @param id ID
	 * @param text Testo del Tweet
	 * @param retweet_count Numero Retweets
	 * @param favorite_count Numero Likes
	 */
	public Tweet(String created_at, long id, String text, int retweet_count, int favorite_count) {
		super();
		this.created_at = created_at;
		this.id = id;
		this.text = text;
		this.favorite_count = favorite_count;
		this.retweet_count = retweet_count;
	}

	/**
	 * Restituisce il numero dei Retweets
	 *
	 * @return L'ID
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Setta l'ID
	 *
	 * @param id Il nuovo ID
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Restituisce il contenuto del Tweet
	 *
	 * @return Il testo
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Setta il contenuto del Tweet
	 *
	 * @param text Il nuovo Testo
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Restituisce la data di creazione del Tweet
	 *
	 * @return La data di creazione
	 */
	public String getCreated_at() {
		return this.created_at;
	}

	/**
	 * Setta la data di creazione del Tweet
	 *
	 * @param created_at La nuova data di creazione
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	/**
	 * Parsing della data. Trasforma la stringa in un oggetto Date
	 *
	 * @return Oggetto data (con il formato di Twitter)
	 * @throws ParseException se ci sono problemi con il formato della data
	 */
	public Date parsedDate() throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);
		sf.setLenient(false);
		return sf.parse(this.created_at);
	}
}