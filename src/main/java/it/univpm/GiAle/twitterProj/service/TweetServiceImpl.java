package it.univpm.GiAle.twitterProj.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.google.gson.Gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import it.univpm.GiAle.twitterProj.filters.Filter;
import it.univpm.GiAle.twitterProj.filters.Stats;
import it.univpm.GiAle.twitterProj.model.Tweet;

@Service
public class TweetServiceImpl implements TweetService {
	private static ArrayList<Tweet> myTweetList = new ArrayList<Tweet>();
	@Override
	public ArrayList<Tweet> getTweet() {
		// TODO Auto-generated method stub
		return myTweetList;
	}

	@Override
	public void addTweetsArray(Tweet[] tweetArray) {
		// TODO Auto-generated method stub
		myTweetList.clear();
		for (int i = 0; i < tweetArray.length; i++)
		{
			myTweetList.add(tweetArray[i]);
		}
	}

	@Override
	public Tweet[] addJson(String body) {
		// TODO Auto-generated method stub
		JsonObject myObject = new Gson().fromJson(body, JsonObject.class);
		JsonArray array = myObject.getAsJsonArray("statuses");
		// serve per i retweet
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).getAsJsonObject().has("retweeted_status")) {
				JsonObject obj = new JsonObject();
				obj = array.get(i).getAsJsonObject();
				obj.addProperty("retweet_count", array.get(i).getAsJsonObject().get("retweeted_status").getAsJsonObject().get("retweet_count").getAsBigInteger());
				obj.addProperty("favorite_count", array.get(i).getAsJsonObject().get("retweeted_status").getAsJsonObject().get("favorite_count").getAsBigInteger());
				array.set(i, obj);				
			}
		}
		Gson GoogleSon = new Gson();
		Tweet[] gsonArray = GoogleSon.fromJson(array, Tweet[].class);
		return gsonArray;
	}
	
	public ArrayList<Tweet> filtraggio (String body, ArrayList<Tweet> list) {
		JsonObject gson = new Gson().fromJson(body, JsonObject.class);
		String filterFiled = gson.get("filter_field").getAsString();
		String filterType = gson.get("filter_type").getAsString();
		JsonElement param = gson.get("parameters");
		ArrayList<Tweet> filteredList = new ArrayList<Tweet>();
		if (filterFiled.equals("likes")) {
			filteredList = Filter.filterByLikes(list, filterType, param);
		}
		if (filterFiled.equals("retweets")) {
			filteredList = Filter.filterByRetweet(list, filterType, param);
		}
		if (filterFiled.equals("time")) {
			try {
				filteredList = Filter.filterByTime(list, filterType, param);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// serve per richiedere le statistiche su tutti i dati
		if (filterFiled.equals("data"))
		{
			filteredList = list;
		}
		return filteredList;
	}

	@Override
	public String GetFromTwitter(String url) throws MalformedURLException, IOException {
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

	@Override
	public String getMetadata(Class<?> classe) {
		// TODO Auto-generated method stub
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
			com.fasterxml.jackson.module.jsonSchema.JsonSchema schema = schemaGen.generateSchema(classe);
			return mapper.writeValueAsString(schema);
		} catch (JsonProcessingException e) {
			return e.getLocalizedMessage();
		}
	}

}
