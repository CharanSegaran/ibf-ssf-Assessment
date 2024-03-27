package sg.edu.nus.iss.ibfb4ssfassessment.service;

import java.io.StringReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.json.*;

import sg.edu.nus.iss.ibfb4ssfassessment.model.Movie;
import sg.edu.nus.iss.ibfb4ssfassessment.util.Util;

@Service
public class DatabaseService {

    // TODO: Task 2 (Save to Redis Map)
    @Autowired
    @Qualifier(Util.REDIS_ONE)
    RedisTemplate<String,String> template;

    HashOperations<String, String, String> hashOps;

    public void saveRecord(Movie movie) {
        JsonObject jsonObj = Json.createObjectBuilder()
                             .add("ID", movie.getMovieID())
                             .add("Title", movie.getTitle())
                             .add("Year", movie.getYear())
                             .add("Rated", movie.getRated())
                             .add("Released", movie.getReleaseDate())
                             .add("Runtime", movie.getRunTime())
                             .add("Genre", movie.getGenre())
                             .add("Director", movie.getDirector())
                             .add("Rating", movie.getRating())
                             .add("Count", movie.getCount())
                             .build();

        hashOps = template.opsForHash();
        hashOps.put(Util.MOVIES, movie.getMovieID().toString(), jsonObj.toString());
    }

    // TODO: Task 3 (Map or List - comment where necesary)
    public long getNumberOfEvents() {
        hashOps = template.opsForHash(); //Map
        return hashOps.size(Util.MOVIES);
    }

    // public Movie getMovie(Integer index) {
    //     return repo.getMovie(index);
    // }

    //TODO: Task 4 (Map)
    public Movie getMovieById(Integer movieId) {
        String movieID = String.valueOf(movieId);
        hashOps = template.opsForHash();
        String movie = hashOps.get(Util.MOVIES, movieID);

        JsonReader jsonReader = Json.createReader(new StringReader(movie));
        JsonObject jsonObject = jsonReader.readObject();

        Integer iD = jsonObject.getInt("ID");
        String title = jsonObject.getString("Title");
        String year = jsonObject.getString("Year");
        String rated = jsonObject.getString("Rated");
        Long released = jsonObject.getJsonNumber("Released").longValue();
        String runtime = jsonObject.getString("Runtime");
        String genre = jsonObject.getString("Genre");
        String director = jsonObject.getString("Director");
        Double rating = jsonObject.getJsonNumber("Rating").doubleValue();
        Long releasedDateInEpoch = jsonObject.getJsonNumber("Released").longValue();
        Date releaseDate = new Date(releasedDateInEpoch);
        Integer count = jsonObject.getInt("Count");


        Movie thisMovie = new Movie();
        thisMovie.setMovieID(iD);
        thisMovie.setTitle(title);
        thisMovie.setYear(year);
        thisMovie.setRated(rated);
        thisMovie.setReleaseDate(released);
        thisMovie.setRunTime(runtime);
        thisMovie.setGenre(genre);
        thisMovie.setDirector(director);
        thisMovie.setRating(rating);
        thisMovie.setFormattedReleaseDate(releaseDate);
        thisMovie.setCount(count);

        return thisMovie;
    }

    // TODO: Task 5
    public List<Movie> getAllMovies() {
        List<Movie> movies = new LinkedList<>();

        hashOps = template.opsForHash();
        Map<String,String> movieList = hashOps.entries(Util.MOVIES);
        
        for(String e:movieList.values()){
           
            JsonReader jsonReader = Json.createReader(new StringReader(e));
            JsonObject jsonObject = jsonReader.readObject();

            Integer iD = jsonObject.getInt("ID");
            String title = jsonObject.getString("Title");
            String year = jsonObject.getString("Year");
            String rated = jsonObject.getString("Rated");
            Long released = jsonObject.getJsonNumber("Released").longValue();
            String runtime = jsonObject.getString("Runtime");
            String genre = jsonObject.getString("Genre");
            String director = jsonObject.getString("Director");
            Double rating = jsonObject.getJsonNumber("Rating").doubleValue();
            Long releasedDateInEpoch = jsonObject.getJsonNumber("Released").longValue();
            Date releaseDate = new Date(releasedDateInEpoch);
            Integer count = jsonObject.getInt("Count");


            Movie thisMovie = new Movie();
            thisMovie.setMovieID(iD);
            thisMovie.setTitle(title);
            thisMovie.setYear(year);
            thisMovie.setRated(rated);
            thisMovie.setReleaseDate(released);
            thisMovie.setRunTime(runtime);
            thisMovie.setGenre(genre);
            thisMovie.setDirector(director);
            thisMovie.setRating(rating);
            thisMovie.setFormattedReleaseDate(releaseDate);
            thisMovie.setCount(count);

            movies.add(thisMovie);
        }
        return movies;
    }
}
