package it.univpm.GiAle.twitterProj.filters;

import java.util.ArrayList;
import java.util.HashMap;


import it.univpm.GiAle.twitterProj.model.Tweet;

public class Stats {
	public static HashMap<String, Float> stats(ArrayList<Tweet> list) {
		float mean_favorite = 0;
		float min_favorite = list.get(0).getFavorite_count();
		float max_favorite = -1;
		float mean_retweet = 0;
		float min_retweet = list.get(0).getRetweet_count();
		float max_retweet = -1;
		float variance_likes = 0;
		float variance_retweets = 0;

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getFavorite_count() > max_favorite) {
				max_favorite = list.get(i).getFavorite_count();
			}
			if (list.get(i).getFavorite_count() < min_favorite) {
				min_favorite = list.get(i).getFavorite_count();
			}
			if (list.get(i).getRetweet_count() > max_retweet) {
				max_retweet= list.get(i).getRetweet_count();
			}
			if (list.get(i).getRetweet_count() < min_retweet) {
				min_retweet = list.get(i).getRetweet_count();
			}
			mean_favorite += list.get(i).getFavorite_count();
			mean_retweet += list.get(i).getRetweet_count();
		}
		mean_favorite = mean_favorite / list.size();
		mean_retweet = mean_retweet / list.size();
		
		// ciclo for variaza
		for (int i = 0; i < list.size(); i++) {
			variance_likes += Math.pow(((float)list.get(i).getFavorite_count() - mean_favorite), 2);
			variance_retweets += Math.pow(((float)list.get(i).getRetweet_count() - mean_retweet), 2);
		}
		variance_likes = variance_likes / list.size();
		variance_retweets = variance_retweets / list.size();
		
		float standard_deviation_likes = (float) Math.sqrt(variance_likes);
		float standard_deviation_retweets = (float) Math.sqrt(variance_retweets);
		
		HashMap<String, Float> statMap = new HashMap<String, Float>();
		statMap.put("media_favorite", mean_favorite);
		statMap.put("min_favorite", min_favorite);
		statMap.put("max_favorite", max_favorite);
		statMap.put("media_retweet", mean_retweet);
		statMap.put("min_retweet", min_retweet);
		statMap.put("max_retweet", max_retweet);
		statMap.put("variance_likes", variance_likes);
		statMap.put("variance_retweet", variance_retweets);
		statMap.put("standard_devation_likes", standard_deviation_likes);
		statMap.put("standard_devation_retweet", standard_deviation_retweets);
		
		return statMap;		
		
	}
}
