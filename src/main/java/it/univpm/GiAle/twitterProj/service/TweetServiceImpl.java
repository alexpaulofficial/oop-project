package it.univpm.GiAle.twitterProj.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.google.gson.Gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.univpm.GiAle.twitterProj.exception.GetTweetException;
import it.univpm.GiAle.twitterProj.exception.WrongFilterException;
import it.univpm.GiAle.twitterProj.filters.Filter;
import it.univpm.GiAle.twitterProj.model.Tweet;

/**
 * Implementazione dell'interfaccia Service
 */
@Service
public class TweetServiceImpl implements TweetService {
	
	/** ArrayList di Tweet per memorizzare i Tweet */
	private static ArrayList<Tweet> myTweetList = new ArrayList<Tweet>();
	
	/**
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#getTweet()
	 */
	@Override
	public ArrayList<Tweet> getTweet() throws GetTweetException{
		// TODO Auto-generated method stub
		/**Eccezione nel caso in cui la lista sia vuota*/
		if (myTweetList.isEmpty())
			throw new GetTweetException("La lista è vuota!");

		return myTweetList;
	}

	/**
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#addTweetsArray(Tweet[])
	 */
	@Override
	public void addTweetsArray(Tweet[] tweetArray) {
		// TODO Auto-generated method stub
		myTweetList.clear();
		for (int i = 0; i < tweetArray.length; i++)
		{
			myTweetList.add(tweetArray[i]);
		}
	}

	/**
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#addJSON(String body)
	 */
	@Override
	public Tweet[] addJSON(String body) {
		// TODO Auto-generated method stub
		JsonObject myObject = new Gson().fromJson(body, JsonObject.class);
		JsonArray array = myObject.getAsJsonArray("statuses");
		/**
		 * Se il tweet è un retweet, i dati vengono presi da retweeted_status altrimenti
		 * non sono verosimili, spesso sono 0, perchè Twitter salva il conteggio reale nel tweet "originale"
		 */
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).getAsJsonObject().has("retweeted_status")) {
				JsonObject obj = new JsonObject();
				obj = array.get(i).getAsJsonObject();
				obj.addProperty("retweet_count", array.get(i).getAsJsonObject().get("retweeted_status").getAsJsonObject().get("retweet_count").getAsBigInteger());
				obj.addProperty("favorite_count", array.get(i).getAsJsonObject().get("retweeted_status").getAsJsonObject().get("favorite_count").getAsBigInteger());
				array.set(i, obj);				
			}
//			if (!array.get(i).getAsJsonObject().has("favorite_count")) {
//				//eccezione favorite count
//			}
//			if (!array.get(i).getAsJsonObject().has("retweet_count")) {
//				//eccezione favorite count
//			}
//			if (!array.get(i).getAsJsonObject().has("favorite_count")) {
//				//eccezione favorite count
//			}
		}
		Gson GoogleSon = new Gson();
		Tweet[] gsonArray = GoogleSon.fromJson(array, Tweet[].class);
		return gsonArray;
	}
	
	/**
	 * Viene fatto il parsing del JSON dei filtri e il filed "data" serve per fare statistiche su
	 * tutti i dati in modo da poter usare esattamente questa funzione anche per le statistiche
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#filtering(String, ArrayList)
	 */
	public ArrayList<Tweet> filtering (String body, ArrayList<Tweet> list) throws ParseException, WrongFilterException {
		JsonObject gson = new Gson().fromJson(body, JsonObject.class);
		String filterFiled = gson.get("filter_field").getAsString();
		String filterType = gson.get("filter_type").getAsString();
		JsonElement param = gson.get("parameters");
		ArrayList<Tweet> filteredList = new ArrayList<Tweet>();
		
		/**Eccezione che parte dal momento in cui i filtri inseriti non sono corretti*/
		if (!filterFiled.equals("likes") && !filterFiled.equals("retweets") && !filterFiled.equals("time") && !filterFiled.equals("data") ) 
			throw new WrongFilterException("Il filtro inserito non è corretto!");
		
		if (filterFiled.equals("likes")) {
			filteredList = Filter.filterByLikes(list, filterType, param);
		}
		if (filterFiled.equals("retweets")) {
			filteredList = Filter.filterByRetweet(list, filterType, param);
		}
		if (filterFiled.equals("time")) {
			filteredList = Filter.filterByTime(list, filterType, param);
		}
		// serve per richiedere le statistiche su tutti i dati
		if (filterFiled.equals("data"))
		{
			filteredList = list;
		}
		return filteredList;
	}

	/**
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#getFromTwitter(String)
	 */
	@Override
	public String getFromTwitter(String url) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub

        HttpURLConnection httpClient =
                (HttpURLConnection) new URL(url).openConnection();

        // optional default is GET
        httpClient.setRequestMethod("GET");

        //add request header
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = httpClient.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            //print result
            return response.toString();

        }
	
	}

	/**
	 * @see it.univpm.GiAle.twitterProj.service.TweetService#getMetadata(Class myClass)
	 */
	@Override
	public String getMetadata(Class<?> myClass) {
		// TODO Auto-generated method stub
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
			com.fasterxml.jackson.module.jsonSchema.JsonSchema schema = schemaGen.generateSchema(myClass);
			return mapper.writeValueAsString(schema);
		} catch (JsonProcessingException e) {
			return e.getLocalizedMessage();
		}
	}

}
