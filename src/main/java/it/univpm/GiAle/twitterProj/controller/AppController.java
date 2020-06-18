package it.univpm.GiAle.twitterProj.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.GiAle.twitterProj.exception.GetTweetException;
import it.univpm.GiAle.twitterProj.exception.WrongFilterException;
import it.univpm.GiAle.twitterProj.filters.Stats;
import it.univpm.GiAle.twitterProj.model.Tweet;
import it.univpm.GiAle.twitterProj.service.TweetService;


/**
 * Classe del controller
 */
// serve per l'interfaccia grafica, gestisce la CORS Policy
@CrossOrigin
@RestController

/**
 * Il Controller gestisce le richieste varie di GET e PUT collegandosi poi al Service
 * 
 * @see it.univpm.GiAle.twitterProj.service.TweetService
 */
public class AppController {
	
	/**
	 * Inizializzazione Service
	 */
	@Autowired
	TweetService service;

	/**
	 * Richiesta GET all'indirizzo "/data" per visualizzare i tweet memorizzati
	 * 
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#getTweet()
	 * @return lista di tutti i tweet
	 * @throws GetTweetException 
	 */
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public ResponseEntity<Object> getTweet() throws GetTweetException {
		return new ResponseEntity<>(service.getTweet(), HttpStatus.OK);
	}

	/**
	 * Memorizza il JSON che viene passato nel body
	 *
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#addJSON(String body)
	 * @param body Il body passato dal client
	 * @return Messaggio di avvenuto inserimento
	 */
	@PostMapping("/data")
	public ResponseEntity<Object> postJSONBody(@RequestBody String body) {

		service.addTweetsArray(service.addJSON(body));
		return new ResponseEntity<>(
				"Tweet caricati correttamente. Per verificare, fare una richiesta GET allo stesso indirizzo",
				HttpStatus.OK);
	}
	
	/**
	 * Scarica i dati dal link inserito nel body
	 *
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#addJSON(String body)
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#getFromTwitter(String url)
	 * @param url URL in formato completo dell'API Twitter
	 * @return Messaggio di download avvenuto e avvenuta memorizzazione
	 * @throws MalformedURLException se l'URL non è scritto bene
	 * @throws IOException segnala un problema I/O 
	 */
	@PostMapping("/data/twitter")
	public ResponseEntity<Object> postFromTwitter(@RequestBody String url) throws MalformedURLException, IOException {
		
		service.addTweetsArray(service.addJSON(service.getFromTwitter(url)));
		return new ResponseEntity<>(
				"Tweet scaricati correttamente da twitter. Per verificare, fare una richiesta GET all'indirizzo /data",
				HttpStatus.OK);
	}

	/**
	 * Filtraggio dei dati in base al JSON inserito nel body
	 *
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#filtering(String, ArrayList)
	 * @param bodyFilter Il filtro richiesto in formato JSON
	 * @return Elenco dei tweet filtrati
	 * @throws ParseException  nel caso del filtro della data con formato sbagliato
	 * @throws GetTweetException 
	 * @throws WrongFilterException 
	 */
	@PostMapping("/data/filter")
	public ResponseEntity<Object> filtering(@RequestBody String bodyFilter) throws ParseException, WrongFilterException, GetTweetException {
		
		return new ResponseEntity<Object> (service.filtering(bodyFilter, service.getTweet()), HttpStatus.OK);
	}

	/**
	 * Statistiche dei dati richiesti dal JSON inserito nel body
	 *
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#filtering(String, ArrayList)
	 * @param bodyFilter Statistiche richieste scritte in formato JSON (come i filtri)
	 * @return Elenco delle statistiche
	 * @throws ParseException nel caso di filtro per Data
	 * @throws GetTweetException 
	 * @throws WrongFilterException 
	 */
	@PostMapping("/data/stats")
	public ResponseEntity<Object> stats(@RequestBody String bodyFilter) throws ParseException, WrongFilterException, GetTweetException {
		return new ResponseEntity<Object> (Stats.stats(service.filtering(bodyFilter, service.getTweet())), HttpStatus.OK);
	}


	/**
	 * Restituisce i Meta Data della classe Tweet
	 *
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#getMetadata(Class myClass)
	 * @return I metadata in JSON
	 */
	@RequestMapping(value = "/metadata", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getMeta() {
		String meta = service.getMetadata(Tweet.class);
		return new ResponseEntity<>(meta, HttpStatus.OK);
	}
}
