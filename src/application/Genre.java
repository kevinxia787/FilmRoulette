package application;

import java.util.HashMap;
import java.util.Map;

public class Genre {
	static Map<String, String> genreMap = new HashMap<String, String>() {{
		put("Action", "28");
		put("Adventure", "12");
		put("Animated", "16");
		put("Comedy", "35");
		put("Crime", "80");
		put("Drama", "18");
		put("Family", "10751");
		put("Fantasay", "14");
		put("Horror", "27");
		put("Mystery", "9648");
		put("Romance", "10749");
		put("Science Fiction", "878");
		put("Thriller", "53");
	}};
	
	public static String getGenreId(String genre) {
		return genreMap.get(genre);
	}
}
