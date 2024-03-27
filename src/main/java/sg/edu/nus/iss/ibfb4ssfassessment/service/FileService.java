package sg.edu.nus.iss.ibfb4ssfassessment.service;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;


import jakarta.json.*;

import sg.edu.nus.iss.ibfb4ssfassessment.model.Movie;

@Service
public record FileService() {

    // TODO: Task 1
    public List<Movie> readFile(String fileName) {

        File file = new File(fileName);
        StringBuilder sb = new StringBuilder();
        
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while(null != (line = br.readLine())){
                sb.append(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        String fileStr = sb.toString();
        JsonReader jsonReader = Json.createReader(new StringReader(fileStr));
        JsonArray jsonArray = jsonReader.readArray();

        List<Movie> movieList =new ArrayList<>();

        jsonArray.forEach(prod -> {
            JsonObject jObj = prod.asJsonObject();
            Movie movie = new Movie();
            movie.setMovieID(jObj.getInt("Id"));
            movie.setTitle(jObj.getString("Title"));
            movie.setYear(jObj.getString("Year"));
            movie.setRated(jObj.getString("Rated"));
            movie.setReleaseDate(jObj.getJsonNumber("Released").longValue());
            movie.setRunTime(jObj.getString("Runtime"));
            movie.setGenre(jObj.getString("Genre"));
            movie.setDirector(jObj.getString("Director"));
            movie.setRating(jObj.getJsonNumber("Rating").doubleValue());
            Long releasedDateInEpoch = jObj.getJsonNumber("Released").longValue();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            Date releaseDate = new Date(releasedDateInEpoch);
            movie.setFormattedReleaseDate(releaseDate);
            movie.setCount(jObj.getInt("Count"));

            movieList.add(movie);
        });
        return movieList;
    }

}
