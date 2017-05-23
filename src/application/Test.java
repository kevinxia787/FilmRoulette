package application;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbDiscover;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbMovies.MovieMethod;
import info.movito.themoviedbapi.model.Language;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.PersonCast;

public class Test {
	
	public static void main(String[] args) {
		TmdbDiscover movies = new TmdbApi("863ed845decc8d1b3251092d426fc7d0").getDiscover();
		
		 //Testing discover
		int page = 1;
		String language = "en";
		String sortBy = "popularity.desc";
		boolean includeAdult = false;
		int voteCountGte = 0;
		float voteAverageGte = (float) 6;
		String withGenres = "28";
		String releaseDateGte = "2011";
		String releaseDateLte = "2014";
		int totalPages = movies.getDiscover(page, language, sortBy, includeAdult, 0, 0, voteCountGte,
				voteAverageGte, withGenres, releaseDateGte, releaseDateLte, "", "", "").getTotalPages();
		List<MovieDb> movi = movies.getDiscover(page, language, sortBy, includeAdult, 0, 0, voteCountGte,
				voteAverageGte, withGenres, releaseDateGte, releaseDateLte, "", "", "").getResults();
//		for (int i = 0; i < movi.size(); ++i) {
//			System.out.println(movi.get(i).getId());
//			System.out.println(movi.get(i));
//		}
		
		
		TmdbMovies test = new TmdbApi("863ed845decc8d1b3251092d426fc7d0").getMovies();
		MovieDb testMovie = test.getMovie(22, "en", MovieMethod.credits);
		
		System.out.println("Total Pages:" + totalPages);
		System.out.println(testMovie);
		System.out.println(testMovie.getPosterPath());
		System.out.println(testMovie.getBudget());
		System.out.println(testMovie.getTitle());
		System.out.println(testMovie.getRevenue());
		System.out.println(testMovie.getOverview());
		System.out.println(testMovie.getReleaseDate());
		List<Language> language2 = testMovie.getSpokenLanguages();
		System.out.println(language2.get(0).getName());
		List<PersonCast> cast = testMovie.getCast();
		System.out.println(cast.get(1).getName());
		
		
		
	}
	
}
