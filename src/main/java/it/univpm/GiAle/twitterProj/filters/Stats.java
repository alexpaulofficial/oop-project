package it.univpm.GiAle.twitterProj.filters;

import java.util.ArrayList;
import java.util.HashMap;


import it.univpm.GiAle.twitterProj.model.Tweet;

/*
 * Classe delle statistiche
 */
public class Stats {
	/**
	 * Classe delle statistiche (leggere la Readme per la lista completa)
	 * @param list Lista di cui calcolare le statistiche
	 * @return Elenco delle statistiche
	 */
	public static HashMap<String, Float> stats(ArrayList<Tweet> list) {
		float meanFavorite = 0;
		float minFavorite = list.get(0).getFavorite_count();
		float maxFavorite = -1;
		float meanRetweet = 0;
		float minRetweet = list.get(0).getRetweet_count();
		float maxRetweet = -1;
		float varianceLikes = 0;
		float varianceRetweets = 0;

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getFavorite_count() > maxFavorite) {
				maxFavorite = list.get(i).getFavorite_count();
			}
			if (list.get(i).getFavorite_count() < minFavorite) {
				minFavorite = list.get(i).getFavorite_count();
			}
			if (list.get(i).getRetweet_count() > maxRetweet) {
				maxRetweet= list.get(i).getRetweet_count();
			}
			if (list.get(i).getRetweet_count() < minRetweet) {
				minRetweet = list.get(i).getRetweet_count();
			}
			meanFavorite += list.get(i).getFavorite_count();
			meanRetweet += list.get(i).getRetweet_count();
		}
		meanFavorite = meanFavorite / list.size();
		meanRetweet = meanRetweet / list.size();
		
		// ciclo for variaza
		for (int i = 0; i < list.size(); i++) {
			varianceLikes += Math.pow(((float)list.get(i).getFavorite_count() - meanFavorite), 2);
			varianceRetweets += Math.pow(((float)list.get(i).getRetweet_count() - meanRetweet), 2);
		}
		varianceLikes = varianceLikes / list.size();
		varianceRetweets = varianceRetweets / list.size();
		
		float standardDeviationLikes = (float) Math.sqrt(varianceLikes);
		float standardDeviationRetweets = (float) Math.sqrt(varianceRetweets);
		
		HashMap<String, Float> statMap = new HashMap<String, Float>();
		statMap.put("Media dei Likes", meanFavorite);
		statMap.put("Minimo dei Likes", minFavorite);
		statMap.put("Massimo dei Likes", maxFavorite);
		statMap.put("Media dei Retweets", meanRetweet);
		statMap.put("Minimo dei Retweets", minRetweet);
		statMap.put("Massimo dei Retweets", maxRetweet);
		statMap.put("Varianza dei Likes", varianceLikes);
		statMap.put("Varianza dei Retweet", varianceRetweets);
		statMap.put("Deviazione Standard dei Likes", standardDeviationLikes);
		statMap.put("Deviazione Standard dei Retweets", standardDeviationRetweets);
		
		return statMap;		
		
	}
}
