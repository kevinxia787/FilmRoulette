package application;
	
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbDiscover;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbMovies.MovieMethod;
import info.movito.themoviedbapi.model.Language;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.PersonCast;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebView;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,1000,550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("FilmRoulette");
			
			// Year Input
			Label yearLB = new Label("Lower Year:");
			final TextField yearInputLB = new TextField();
			yearInputLB.setPrefWidth(70);
			yearLB.setTranslateY(5);
			Label yearUB = new Label("Upper Year:");
			final TextField yearInputUB = new TextField();
			yearInputUB.setPrefWidth(70);
			yearUB.setTranslateY(5);
			
			// Dropdown Box for Genres
			ObservableList<String> options = 
					FXCollections.observableArrayList(
						"Action",
						"Aventure",
						"Animated",
						"Comedy",
						"Crime",
						"Drama",
						"Family",
						"Fantasy",
						"Horror",
						"Mystery",
						"Romance",
						"Science Fiction",
						"Thriller"
					);
			final ComboBox genreBox = new ComboBox(options);
			Label genreLabel = new Label("Genre: ");
			genreLabel.setTranslateY(5);
			
					
			
			// Trigger Box
			Button submit = new Button("Submit");
			VBox triggerBox = new VBox(submit);
			triggerBox.setPadding(new Insets(30, 10, 10, 10));
			
			// Adding buttons to HBox
			VBox yearBox = new VBox(5, yearLB, yearInputLB, yearUB, yearInputUB);
			yearBox.setPadding(new Insets(0, 10, 5, 10));
			VBox dropdownBox = new VBox(5, genreLabel, genreBox);
			dropdownBox.setPadding(new Insets(0, 20, 5, 10));
			
			// HelpBox
			Text helpTitle = new Text("About:");
			helpTitle.setId("title");
			Text helpActual = new Text("Simply selected a year range and a genre, and you'll be able to find something to watch!\n\nNote: To find a new movie, reinput your year bounds and genre.\nThis app is powered by The Movie Database, and written in Java.");
			helpActual.setWrappingWidth(200);
			VBox helpBox = new VBox(helpTitle, helpActual);
			helpBox.setPadding(new Insets(0, 10, 10, 10));
			
			// Main left box
			VBox mainBox = new VBox(10, helpBox, yearBox, dropdownBox, triggerBox);
			mainBox.setPadding(new Insets(20, 10, 10, 0));
			
			
			// Movie INFO
			final BorderPane movieInfoBlock = new BorderPane();
			
			// Movie Title (placeholder)
			final Text title = new Text("Guardians of the Galaxy");
			title.setFont(Font.font("Verdana", 24));
			title.setId("titleText");
			title.setTextAlignment(TextAlignment.CENTER);
			HBox titleOfMovie = new HBox(title);
			titleOfMovie.setAlignment(Pos.CENTER);
			
			// Movie Image (placeholder)
			Image poster = new Image("/images/Main.jpg");
			ImageView posterView = new ImageView(poster);
			posterView.setFitHeight(600);
			posterView.setFitWidth(300);
			posterView.setPreserveRatio(true);
			
			// Other Movie Info
			final Text release = new Text("Release Date:");
			release.setId("accessRelease");
			final Text releaseDate = new Text("07-30-2014");
			final Text languageTitle = new Text("Language:");
			languageTitle.setId("language");
			final Text language1 = new Text("English");
			final Text overview = new Text("Overview:");
			final Text budget1 = new Text("Budget:");
			budget1.setId("budget");
			final Text budgetActual = new Text("170000000 Dollars");
			final Text revenue1 = new Text("Revenue:");
			revenue1.setId("revenue");
			final Text revenueActual = new Text("773328629 Dollars");
			overview.setId("overview");
			final Text overviewActual = new Text("Light years from Earth, 26 years after being abducted, Peter Quill finds himself the prime target of a manhunt after discovering an orb wanted by Ronan the Accuser");
			overviewActual.setFont(Font.font("Verdana", 12));
			overviewActual.setWrappingWidth(400);
			
			// Cast
			Text castTitle = new Text("Cast:");
			castTitle.setId("cast");
			final Text cast1 = new Text("Chris Pratt");
			final Text cast2 = new Text("Zoe Saldana");
			final Text cast3 = new Text("Dave Bautista");
			
			// Trailer
			Text trailerTitle = new Text("Trailer:");
			trailerTitle.setId("trailer");
			Button watchTrailer = new Button("Watch Trailer");
			Text trailerUrl = new Text("https://www.youtube.com/watch?v=d96cjJhvlMA");
			
			
			
			// VBox with other info
			VBox info = new VBox(2.5, release, releaseDate, languageTitle, language1 ,budget1, budgetActual, 
					revenue1, revenueActual, overview, overviewActual,
					castTitle, cast1, cast2, cast3, trailerTitle, watchTrailer);
			info.setPadding(new Insets(0, 10, 0, 20));
			
			watchTrailer.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					String urlx = trailerUrl.getText();
					System.out.println(urlx);
					getHostServices().showDocument(urlx);
				}
			});
			
			// Add to MovieInfo
			movieInfoBlock.setTop(titleOfMovie);
			movieInfoBlock.setLeft(posterView);
			movieInfoBlock.setCenter(info);
			submit.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					// Grab the year bounds
					String yearLB = yearInputLB.getText();
					String yearUB = yearInputUB.getText();
					
					// Grab genre
					String genre = (String) genreBox.getSelectionModel().getSelectedItem();
					
					// Get Genre ID
					String genreID = Genre.getGenreId(genre);
					
					// Set random page first
					int totalPages = MovieInfo.getTotalPages(genreID, yearLB, yearUB);
					int randomPages = ThreadLocalRandom.current().nextInt(1, totalPages);
					System.out.println(randomPages);
					
					// First we set the movie list 
					List<MovieDb> moviesList = MovieInfo.getMovieList(randomPages, genreID, yearLB, yearUB);
					
					// Get the movie id
					int movieId = MovieInfo.getSelectedMovieId(moviesList);
					
					// Get the movie
					MovieDb selectedMovie = MovieInfo.getSelectedMovie(movieId);
					
					// Get the release date
					String releaseDateActual = MovieInfo.getReleaseDate(selectedMovie);
					
					// Get the title
					String titleOfMovie = MovieInfo.getTitle(selectedMovie);
					
					// Get the poster path
					String posterUrl = MovieInfo.getPosterPath(selectedMovie);
					
					// Get the poster url
					String base = "http://image.tmdb.org/t/p/w300";
					String url = base + posterUrl;
					
					// Get the overview
					String overviewComplete = MovieInfo.getOverview(selectedMovie);
					
					// Get budget
					long budget = MovieInfo.getBudget(selectedMovie);
					
					// Get revenue
					long revenue = MovieInfo.getRevenue(selectedMovie);
					
					// Get primary language
					String primaryLanguage = MovieInfo.getLanguage(selectedMovie);
					
					// Get cast list
					ArrayList<String> castMembers = MovieInfo.getCast(selectedMovie);
					
					// Get individual cast
					String castFirst = castMembers.get(0);
					String castSecond = castMembers.get(1);
					String castThird = castMembers.get(2);
					String castFourth = castMembers.get(3);
					String castFifth = castMembers.get(4);
					
					// Get new trailer url
					String actualTrailerUrl = MovieInfo.getTrailerUrl(selectedMovie);
					
					// Set the new title
					title.setText(titleOfMovie);
					
					// Set the poster
					ImageView posterView = ProcessPoster.processPoster(url);
					posterView.setFitHeight(600);
					posterView.setFitWidth(300);
					posterView.setPreserveRatio(true);
				
					// Set release
					releaseDate.setText(releaseDateActual);
					
					// Set language
					language1.setText(primaryLanguage);
					
					// Set overview
					overviewActual.setText(overviewComplete);
					
					// Set Budget
					budgetActual.setText(String.valueOf(budget));
					
					// Set revenue
					revenueActual.setText(String.valueOf(revenue));
					
					// Set cast
					cast1.setText(castFirst);
					cast2.setText(castSecond);
					cast3.setText(castThird);
					
					// Set trailer
					trailerUrl.setText(actualTrailerUrl);
					
					
					
					// New VBox
					VBox updatedInfo = new VBox(release, releaseDate, languageTitle,
							language1, budget1, budgetActual, revenue1, revenueActual, overview, overviewActual, 
							castTitle, cast1, cast2, cast3, trailerTitle, watchTrailer);
					updatedInfo.setPadding(new Insets(0, 10, 0, 20));
					HBox titleBox = new HBox(title);
					titleBox.setAlignment(Pos.TOP_CENTER);
					movieInfoBlock.setTop(titleBox);
					movieInfoBlock.setLeft(posterView);
					movieInfoBlock.setCenter(updatedInfo);
					root.setCenter(movieInfoBlock);
					
				}
				
			});
			
			// Add to Stage
			root.setLeft(mainBox);
			root.setCenter(movieInfoBlock);
			root.setBottom(triggerBox);
			
			
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
