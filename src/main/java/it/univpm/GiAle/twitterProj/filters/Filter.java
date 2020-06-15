package it.univpm.GiAle.twitterProj.filters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.gson.JsonElement;

import it.univpm.GiAle.twitterProj.model.Tweet;

public class Filter {
	public static ArrayList<Tweet> filterByLikes(ArrayList<Tweet> list, String filter, JsonElement param) {
		// TODO Auto-generated method stub
		ArrayList<Tweet> listaFiltrata = new ArrayList<Tweet>();
		for (int i = 0; i < list.size(); i++) {
			if (filter.equals("$gt") && list.get(i).getFavorite_count() > param.getAsInt()) {
				listaFiltrata.add(list.get(i));
			}
			if (filter.equals("$gte") && list.get(i).getFavorite_count() >= param.getAsInt()) {
				listaFiltrata.add(list.get(i));
			}
			if (filter.equals("$lt") && list.get(i).getFavorite_count() < param.getAsInt()) {
				listaFiltrata.add(list.get(i));
			}
			if (filter.equals("$lte") && list.get(i).getFavorite_count() <= param.getAsInt()) {
				listaFiltrata.add(list.get(i));
			}
			if (filter.equals("$bt")) {
				if (!param.isJsonArray()) {
					// eccezione
					return null;
				} else {
					if (list.get(i).getFavorite_count() >= param.getAsJsonArray().get(0).getAsInt()
							&& list.get(i).getFavorite_count() <= param.getAsJsonArray().get(1).getAsInt())
						listaFiltrata.add(list.get(i));
				}

			}
		}
		return listaFiltrata;
	}

	public static ArrayList<Tweet> filterByRetweet(ArrayList<Tweet> list, String filter, JsonElement param) {
		// TODO Auto-generated method stub
		ArrayList<Tweet> listaFiltrata = new ArrayList<Tweet>();
		for (int i = 0; i < list.size(); i++) {
			if (filter.equals("$gt") && list.get(i).getRetweet_count() > param.getAsInt()) {
				listaFiltrata.add(list.get(i));
			}
			if (filter.equals("$gte") && list.get(i).getRetweet_count() >= param.getAsInt()) {
				listaFiltrata.add(list.get(i));
			}
			if (filter.equals("$lt") && list.get(i).getRetweet_count() < param.getAsInt()) {
				listaFiltrata.add(list.get(i));
			}
			if (filter.equals("$lte") && list.get(i).getRetweet_count() <= param.getAsInt()) {
				listaFiltrata.add(list.get(i));
			}
			if (filter.equals("$bt")) {
				if (!param.isJsonArray()) {
					// eccezione
					return null;
				} else {
					if (list.get(i).getFavorite_count() >= param.getAsJsonArray().get(0).getAsInt()
							&& list.get(i).getFavorite_count() <= param.getAsJsonArray().get(1).getAsInt())
						listaFiltrata.add(list.get(i));
				}

			}
		}

		return listaFiltrata;
	}

	public static ArrayList<Tweet> filterByTime(ArrayList<Tweet> list, String filter, JsonElement param)
			throws ParseException {
		// TODO Auto-generated method stub
		ArrayList<Tweet> listaFiltrata = new ArrayList<Tweet>();
		SimpleDateFormat sf = new SimpleDateFormat("MMM dd, yyyy, HH:mm:ss", Locale.ENGLISH);
		sf.setLenient(false);

		if (filter.equals("$gte")) {
			for (int i = 0; i < list.size(); i++) {
				Date dataFiltro = sf.parse(param.getAsString());
				if (list.get(i).parsedDate().compareTo(dataFiltro) > 0)
					listaFiltrata.add(list.get(i));
			}
		}
		if (filter.equals("$gt")) {
			for (int i = 0; i < list.size(); i++) {
				Date dataFiltro = sf.parse(param.getAsString());
				Calendar giorno_dopo = Calendar.getInstance();
				giorno_dopo.setTime(dataFiltro);
				giorno_dopo.add(Calendar.DATE, 1);
				if (list.get(i).parsedDate().compareTo(giorno_dopo.getTime()) > 0)
					listaFiltrata.add(list.get(i));
			}
		}
		if (filter.equals("$lt")) {
			for (int i = 0; i < list.size(); i++) {
				Date dataFiltro = sf.parse(param.getAsString());
				if (list.get(i).parsedDate().compareTo(dataFiltro) < 0)
					listaFiltrata.add(list.get(i));
			}
		}
		if (filter.equals("$lte")) {
			for (int i = 0; i < list.size(); i++) {
				Date dataFiltro = sf.parse(param.getAsString());
				if (list.get(i).parsedDate().compareTo(dataFiltro) <= 0)
					listaFiltrata.add(list.get(i));
			}
		}
		if (filter.equals("$bt")) {
			for (int i = 0; i < list.size(); i++) {
				if (!param.isJsonArray()) {
					// eccezione
					return null;
				} else {
					Date dataFiltro1 = sf.parse(param.getAsJsonArray().get(0).getAsString());
					Date dataFiltro2 = sf.parse(param.getAsJsonArray().get(1).getAsString());

					if (list.get(i).parsedDate().compareTo(dataFiltro1) > 0
							&& list.get(i).parsedDate().compareTo(dataFiltro2) < 0)
						listaFiltrata.add(list.get(i));
				}
			}
		}

		return listaFiltrata;
	}
}
