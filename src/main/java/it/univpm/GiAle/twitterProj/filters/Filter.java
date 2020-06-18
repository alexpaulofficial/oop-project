package it.univpm.GiAle.twitterProj.filters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.gson.JsonElement;

import it.univpm.GiAle.twitterProj.exception.WrongFilterException;
import it.univpm.GiAle.twitterProj.model.Tweet;

/**
 * Classe che gestisce i filtri
 * 
 * @version 1.1
 */
public class Filter {
	/**
	 * Filtra in base ai Likes (favorite_count)
	 * 
	 * <p>Si poteva separare in più funzioni i vari filtri interni al for per avere un codice più pulito
	 * ma sarebbe stato molto più lungo e inutile dato che la complessità computazionale è la stessa </p>
	 *
	 * @param list La lista da filtrare
	 * @param filter Il JSON di richiesta dei filtri
	 * @param param Parametri dei filtri
	 * @return Lista filtrata
	 * @throws WrongFilterException Caso filtro errato
	 */
	public static ArrayList<Tweet> filterByLikes(ArrayList<Tweet> list, String filter, JsonElement param) throws WrongFilterException {
		// TODO Auto-generated method stub
		ArrayList<Tweet> filteredList = new ArrayList<Tweet>();
		for (int i = 0; i < list.size(); i++) {
			if (filter.equals("$gt") && list.get(i).getFavorite_count() > param.getAsInt()) {
				filteredList.add(list.get(i));
			}
			if (filter.equals("$gte") && list.get(i).getFavorite_count() >= param.getAsInt()) {
				filteredList.add(list.get(i));
			}
			if (filter.equals("$lt") && list.get(i).getFavorite_count() < param.getAsInt()) {
				filteredList.add(list.get(i));
			}
			if (filter.equals("$lte") && list.get(i).getFavorite_count() <= param.getAsInt()) {
				filteredList.add(list.get(i));
			}
			if (filter.equals("$bt")) {
				if (!param.isJsonArray() || param.getAsJsonArray().size() != 2) {
					/**Eccezione nel caso in cui sia sbagliato il filtro $bt*/
					throw new WrongFilterException("Filtro $bt errato, sono richiesti 2 valori");
				} else {
					/**
					 * Se sono inseriti in ordine errato (prima un paramatro maggiore e poi un minore) viene
					 * invertito il controllo
					 */
					if (param.getAsJsonArray().get(0).getAsInt() <= param.getAsJsonArray().get(1).getAsInt()) {
						if (list.get(i).getFavorite_count() >= param.getAsJsonArray().get(0).getAsInt()
								&& list.get(i).getFavorite_count() <= param.getAsJsonArray().get(1).getAsInt())
							filteredList.add(list.get(i));
					} else {
						if (list.get(i).getFavorite_count() <= param.getAsJsonArray().get(0).getAsInt()
								&& list.get(i).getFavorite_count() >= param.getAsJsonArray().get(1).getAsInt())
							filteredList.add(list.get(i));
					}
				}

			}
		}

		return filteredList;
	}

	/**
	 * Filtra in base ai Retweets (retweet_count)
	 * 
	 * @param list La lista da filtrare
	 * @param filter Il JSON di richiesta dei filtri
	 * @param param Parametri dei filtri
	 * @return Lista filtrata
	 * @throws WrongFilterException se il filtro è errato
	 */
	public static ArrayList<Tweet> filterByRetweet(ArrayList<Tweet> list, String filter, JsonElement param) throws WrongFilterException {
		// TODO Auto-generated method stub
		ArrayList<Tweet> filteredList = new ArrayList<Tweet>();
		for (int i = 0; i < list.size(); i++) {
			if (filter.equals("$gt") && list.get(i).getRetweet_count() > param.getAsInt()) {
				filteredList.add(list.get(i));
			}
			if (filter.equals("$gte") && list.get(i).getRetweet_count() >= param.getAsInt()) {
				filteredList.add(list.get(i));
			}
			if (filter.equals("$lt") && list.get(i).getRetweet_count() < param.getAsInt()) {
				filteredList.add(list.get(i));
			}
			if (filter.equals("$lte") && list.get(i).getRetweet_count() <= param.getAsInt()) {
				filteredList.add(list.get(i));
			}
			if (filter.equals("$bt")) {
				if (!param.isJsonArray() || param.getAsJsonArray().size() != 2) {
					/**Eccezione nel caso in cui sia sbagliato il filtro $bt*/
					throw new WrongFilterException("Filtro $bt errato, sono richiesti 2 valori");
				} else {
					if (param.getAsJsonArray().get(0).getAsInt() <= param.getAsJsonArray().get(1).getAsInt()) {
						if (list.get(i).getRetweet_count() >= param.getAsJsonArray().get(0).getAsInt()
								&& list.get(i).getRetweet_count() <= param.getAsJsonArray().get(1).getAsInt())
							filteredList.add(list.get(i));
					} else {
						if (list.get(i).getRetweet_count() <= param.getAsJsonArray().get(0).getAsInt()
								&& list.get(i).getRetweet_count() >= param.getAsJsonArray().get(1).getAsInt())
							filteredList.add(list.get(i));
					}
				}

			}
		}

		return filteredList;
	}

	/**
	 * Filtra in base alla data di creazione del Tweet
	 * 
	 * <p>Da notare che nel filtro gte si considera il giorno dato incluso. Nel filtro
	 * gt si considera il giorno dopo (lo stesso per lt e lte) </p>
	 *
	 * @param list La lista da filtrare
	 * @param filter Il JSON di richiesta dei filtri
	 * @param param I parametri dei filtri
	 * @return Lista filtrata
	 * @throws ParseException Caso di errore nel parsing della data
	 * @throws WrongFilterException Caso di filtro errato
	 */
	public static ArrayList<Tweet> filterByTime(ArrayList<Tweet> list, String filter, JsonElement param)
			throws ParseException, WrongFilterException {
		// TODO Auto-generated method stub
		ArrayList<Tweet> filteredList = new ArrayList<Tweet>();
		/**
		 * E' stato scelto questo formato per il datepicker di Bootstrap per
		 * l'interfaccia grafica
		 */
		SimpleDateFormat sf = new SimpleDateFormat("MMM dd, yyyy, HH:mm:ss", Locale.ENGLISH);
		sf.setLenient(false);

		if (filter.equals("$gte")) {
			for (int i = 0; i < list.size(); i++) {
				Date dateFilter;

				dateFilter = sf.parse(param.getAsString());
				if (list.get(i).parsedDate().compareTo(dateFilter) > 0)
					filteredList.add(list.get(i));

			}
		}
		if (filter.equals("$gt")) {
			for (int i = 0; i < list.size(); i++) {
				Date dateFilter;
				dateFilter = sf.parse(param.getAsString());
				/**
				 * Per aumentare il giorno (e diminuire) si utilizza la classe Calendar
				 */

				Calendar dateAfter = Calendar.getInstance();
				dateAfter.setTime(dateFilter);
				dateAfter.add(Calendar.DATE, 1);
				if (list.get(i).parsedDate().compareTo(dateAfter.getTime()) > 0)
					filteredList.add(list.get(i));
			}
		}
		if (filter.equals("$lt")) {
			for (int i = 0; i < list.size(); i++) {
				Date dateFilter;
				dateFilter = sf.parse(param.getAsString());
				
				Calendar dateBefore = Calendar.getInstance();
				dateBefore.setTime(dateFilter);
				dateBefore.add(Calendar.DATE, -1);
				
				if (list.get(i).parsedDate().compareTo(dateFilter) < 0)
					filteredList.add(list.get(i));

			}
		}
		if (filter.equals("$lte")) {
			for (int i = 0; i < list.size(); i++) {
				Date dateFilter;
				dateFilter = sf.parse(param.getAsString());
				
				if (list.get(i).parsedDate().compareTo(dateFilter) < 0)
					filteredList.add(list.get(i));

			}
		}
		if (filter.equals("$bt")) {
			for (int i = 0; i < list.size(); i++) {
				if (!param.isJsonArray() || param.getAsJsonArray().size() != 2) {
					/**Eccezione in cui il filtro $bt non sia corretto*/
					throw new WrongFilterException("Filtro $bt errato, sono richiesti 2 valori");
				} else {
					Date dateFilter1;
					try {
						dateFilter1 = sf.parse(param.getAsJsonArray().get(0).getAsString());
						Date dateFilter2 = sf.parse(param.getAsJsonArray().get(1).getAsString());
						/**
						 * Se le date da filtrare sono inserite in verso decrescente si risolve
						 * invertendole
						 */
						if (dateFilter1.compareTo(dateFilter2) > 0) {
							Date tmp = dateFilter2;
							dateFilter2 = dateFilter1;
							dateFilter1 = tmp;

						}
						if (list.get(i).parsedDate().compareTo(dateFilter1) > 0
								&& list.get(i).parsedDate().compareTo(dateFilter2) < 0)
							filteredList.add(list.get(i));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}

		return filteredList;
	}
}
