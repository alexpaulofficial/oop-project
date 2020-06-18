package it.univpm.GiAle.twitterProj.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;

import it.univpm.GiAle.twitterProj.exception.GetTweetException;
import it.univpm.GiAle.twitterProj.exception.WrongFilterException;
import it.univpm.GiAle.twitterProj.model.Tweet;

/**
 * L'interfaccia del Service
 */
public interface TweetService {
	
	/**
	 * Restituisce tutti i Tweet inseriti
	 *
	 * @return ArrayList dei Tweet
	 */
	public abstract ArrayList<Tweet> getTweet() throws GetTweetException; 
	
	/**
	 * Memorizza l'array di Tweet passato, cancellando quelli già inseriti
	 *
	 * @param tweetArray Array di Tweet
	 */
	public abstract void addTweetsArray (Tweet[] tweetArray);
	
	/**
	 * Fa il parsing del JSON ritornando un array di Tweet
	 *
	 * @param body Stringa del JSON
	 * @return Array di Tweet
	 */
	public abstract Tweet[] addJSON (String body);
	
	/**
	 * Restituisce i metadata tramite ObjectMapper e JsonSchemaGenerator della classe passata
	 *
	 * @param myClass Una classe
	 * @return I Meta Data di tale classe
	 */
	public abstract String getMetadata(Class<?> myClass);
	
	/**
	 * Filtraggio dei dati
	 * 
	 * <p>Restituisce un ArrayList di Tweet a partire dai tipi di filtri passati
	 * (per fare più filtri in contemporanea, sarebbe meglio separare il controllo del JSON) </p>
	 * 
	 * @param body JSON dei Filtri
	 * @param list Lista da filtrare
	 * @return Lista filtrata
	 * @throws ParseException nel caso di filtraggio per Data
	 */
	public abstract ArrayList<Tweet> filtering (String body, ArrayList<Tweet> list) throws ParseException, WrongFilterException;
	
	/**
	 * Scarica i Tweet direttamente dall'API di Twitter
	 *
	 * @param url URL API completo
	 * @return JSON in Stringa da inserire
	 * @throws MalformedURLException per errore nell'URL
	 * @throws IOException Signals per errori di I/O
	 */
	public abstract String getFromTwitter (String url) throws MalformedURLException, IOException;
}
