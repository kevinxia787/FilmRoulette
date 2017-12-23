package application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbDiscover;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbMovies.MovieMethod;
import info.movito.themoviedbapi.model.Language;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Video;
import info.movito.themoviedbapi.model.people.PersonCast;


public class MovieInfo {
	static String key = "863ed845decc8d1b3251092d426fc7d0";
	static TmdbDiscover discoverApi = new TmdbApi(key).getDiscover(); 
	static List<MovieDb> movies = null;
	static int selectedMovieId = 0;
	static int totalPages = 0;
	
	public static List<MovieDb> getMovieList(int randomPage, String withGenres, String releaseDateGte, String releaseDateLte) {
		int page = randomPage;
		String language = "en-US";
		String sortBy = "";
		boolean includeAdult = false;
		int voteCountGte = 300;
		float voteAverageGte = (float) 6;
		int year = 0;
		int primaryReleaseYear = 0;
		movies =  discoverApi.getDiscover(page, language, sortBy, includeAdult, year, primaryReleaseYear, 
				voteCountGte, voteAverageGte, withGenres, releaseDateGte, releaseDateLte, "", "", "").getResults();
		
		return movies;
	}
	
	public static int getTotalPages(String withGenres, String releaseDateGte, String releaseDateLte) {
		String language = "en-US";
		String sortBy = "popularity.desc";
		boolean includeAdult = false;
		int voteCountGte = 300;
		float voteAverageGte = (float) 6;
		int year = 0;
		int primaryReleaseYear = 0;
		totalPages =  discoverApi.getDiscover(1, language, sortBy, includeAdult, year, primaryReleaseYear, 
				voteCountGte, voteAverageGte, withGenres, releaseDateGte, releaseDateLte, "", "", "").getTotalPages();
		
		return totalPages;
	}
	
	public static int getSelectedMovieId(List<MovieDb> movies) {
		int randomIndex = ThreadLocalRandom.current().nextInt(0, 19+1);
		selectedMovieId = movies.get(randomIndex).getId();
		return selectedMovieId;
	}
	
	static TmdbMovies movieApi = new TmdbApi("redacted").getMovies();
	MovieDb selectedMovie = null;
	
	public static MovieDb getSelectedMovie(int id) {
		MovieDb selectedMovie = movieApi.getMovie(id,  "eng", MovieMethod.credits, MovieMethod.videos);
		return selectedMovie;
	}
	
	// Get posterurl, takes in selectedMovie
	public static String getPosterPath(MovieDb selectedMovie) {
		String posterPath = selectedMovie.getPosterPath();
		return posterPath;
	}
	
	// Get title, takes in selectedMovie
	public static String getTitle(MovieDb selectedMovie) {
		String title = selectedMovie.getTitle();
		return title;
	}
	
	public static String getReleaseDate(MovieDb selectedMovie) {
		String releaseDate = selectedMovie.getReleaseDate();
		return releaseDate;
	}
	
	// Get budget, takes in selectedMovie
	public static long getBudget(MovieDb selectedMovie) {
		long budget = selectedMovie.getBudget();
		return budget;
	}
	
	// Get revenue, takes in selectedMovie
	public static long getRevenue(MovieDb selectedMovie) {
		long revenue = selectedMovie.getRevenue();
		return revenue;
	}
	
	// Get overview
	public static String getOverview(MovieDb selectedMovie) {
		String overview = selectedMovie.getOverview();
		return overview;
	}
	
	public static String getLanguage(MovieDb selectedMovie) {
		List<Language> spokenLanguages = selectedMovie.getSpokenLanguages();
		String primaryLanguage = null;
		for (int i = 0; i < spokenLanguages.size(); ++i) {
			if (spokenLanguages.get(i).getName().equals("English")) {
				primaryLanguage = spokenLanguages.get(i).getName();
				break;
			}
			else {
				continue;
			}
		}
		return primaryLanguage;
	}
	
	public static ArrayList<String> getCast(MovieDb selectedMovie) {
		ArrayList<String>  cast = new ArrayList<String>();
		List<PersonCast> totalCast = selectedMovie.getCast();
		for (int i = 0; i < 5; ++i) {
			cast.add(totalCast.get(i).getName());
		}
		return cast;
	}
	
	public static String getTrailerUrl(MovieDb selectedMovie) {
		List<Video> videoUrls =  selectedMovie.getVideos();
		String key = videoUrls.get(0).getKey();
		String base = "https://www.youtube.com/watch?v=";
		return base + key;
	}
	
	
	
//	public static void main(String[] args) {
//		MovieInfo x = new MovieInfo();
//		List<MovieDb> movies = x.getMovieList(0, "28", "2011", "2014");
////		System.out.println(x.getMovieList("28", "2011", "2014").get(0).getId());
//		System.out.println("Here is the movie ID: " + x.getSelectedMovieId(movies));
//		int id = x.getSelectedMovieId(movies);
//		MovieDb movie = x.getSelectedMovie(id);
//		System.out.println("Here is the title: " + x.getTitle(movie));
//		System.out.println("Here is the movie: " + x.getSelectedMovie(id));
//		System.out.println("Here is the poster path: " + x.getPosterPath(movie));
//		System.out.println("Here is the budget: " + x.getBudget(movie));
//		System.out.println("Here is the revenue: " + x.getRevenue(movie));
//		System.out.println("Here is the primary language: " + x.getLanguage(movie));
//		ArrayList<String> cast = x.getCast(movie);
//		System.out.println("First cast: " + cast.get(0));
//		System.out.println("Second cast: " + cast.get(1));
//		System.out.println("Third cast: " + cast.get(2));
//		System.out.println("Fourth cast: " + cast.get(3));
//		System.out.println("Fifth cast: " + cast.get(4));
//		System.out.println("Youtube:" + getTrailerUrl(movie));
//		
//	}
}
