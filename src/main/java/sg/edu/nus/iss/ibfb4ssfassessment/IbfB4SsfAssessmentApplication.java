package sg.edu.nus.iss.ibfb4ssfassessment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.ibfb4ssfassessment.model.Movie;
import sg.edu.nus.iss.ibfb4ssfassessment.service.DatabaseService;
import sg.edu.nus.iss.ibfb4ssfassessment.service.FileService;
import sg.edu.nus.iss.ibfb4ssfassessment.util.Util;

// TODO: Put in the necessary code as described in Task 1 & Task 2
@SpringBootApplication
public class IbfB4SsfAssessmentApplication  implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(IbfB4SsfAssessmentApplication.class, args);
	}

	@Value("${moviefilepath}")
	private String movieFilePath;

	@Autowired
	FileService fs;
	DatabaseService dbs;

	@Autowired
    @Qualifier(Util.REDIS_ONE)
    RedisTemplate<String,String> template;

	HashOperations<String, String, String> hashOps;
	
	@Override
	public void run(String... args) throws Exception{
		for(Movie movie:fs.readFile(movieFilePath)){
			System.out.println(movie.getTitle());
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
	}

}

//task 6 date