package it.univpm.GiAle.twitterProj.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import it.univpm.GiAle.twitterProj.model.Tweet;

public interface TweetService {
	public abstract ArrayList<Tweet> getTweet(); 
	public abstract void addTweetsArray (Tweet[] tweetArray);
	public abstract Tweet[] addJson (String body);
	public abstract String getMetadata(Class<?> classe);
	
	public abstract ArrayList<Tweet> filtraggio (String body, ArrayList<Tweet> list);
	public abstract String GetFromTwitter (String url) throws MalformedURLException, IOException;
}
