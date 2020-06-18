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
	 * Richiesta GET all'indirizzo "/data" per visualizzare i Tweet memorizzati
	 * 
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#getTweet()
	 * @return Lista di tutti i Tweet
	 */
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public ResponseEntity<Object> getTweet() {
		return new ResponseEntity<>(service.getTweet(), HttpStatus.OK);
	}

	/**
	 * Memorizza il JSON che viene passato nel body
	 *
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#addJSON(String)
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
	 * Scarica i dati dal link inserito nel body (API Twitter)
	 *
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#addJSON(String)
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#getFromTwitter(String)
	 * @param url URL in formato completo dell'API Twitter
	 * @return Messaggio di avvenuto caricamento da Twitter
	 * @throws MalformedURLException se l'URL non è corretto
	 * @throws IOException segnala un problema I/O
	 */
	@PostMapping("/data/twitter")
	public ResponseEntity<Object> postFromTwitter(@RequestBody String url) throws MalformedURLException, IOException {
		
		service.addTweetsArray(service.addJSON(service.getFromTwitter(url)));
		return new ResponseEntity<>(
				"Tweet scaricati correttamente da Twitter. Per verificare, fare una richiesta GET all'indirizzo /data",
				HttpStatus.OK);
	}

	/**
	 * Filtraggio dei dati in base al JSON inserito nel body
	 *
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#filtering(String, ArrayList)
	 * @param bodyFilter Il filtro richiesto in formato JSON (come Stringa)
	 * @return Elenco dei tweet filtrati
	 * @throws ParseException  nel caso del filtro della data con formato sbagliato
	 */
	@PostMapping("/data/filter")
	public ResponseEntity<Object> filtering(@RequestBody String bodyFilter) throws ParseException {
		
		return new ResponseEntity<Object> (service.filtering(bodyFilter, service.getTweet()), HttpStatus.OK);
	}

	/**
	 * Statistiche dei dati richiesti dal JSON inserito nel body (scritto come i Filtri)
	 *
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#filtering(String, ArrayList)
	 * @param bodyFilter Statistiche richieste scritte in formato JSON (come i filtri)
	 * @return Elenco delle statistiche
	 * @throws ParseException nel caso di filtro per Data
	 */
	@PostMapping("/data/stats")
	public ResponseEntity<Object> stats(@RequestBody String bodyFilter) throws ParseException {
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
