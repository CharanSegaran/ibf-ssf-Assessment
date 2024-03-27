package sg.edu.nus.iss.ibfb4ssfassessment.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.ibfb4ssfassessment.model.Movie;
import sg.edu.nus.iss.ibfb4ssfassessment.service.DatabaseService;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    DatabaseService dbs;

    // TODO: Task 8
    @GetMapping("/showall")
    public ModelAndView displayMovies() {
        ModelAndView mav = new ModelAndView();
        List<Movie> movies = dbs.getAllMovies();
        mav.addObject("movies", movies);
        mav.setViewName("view2");
        System.out.println(1);
        return mav;
    }

    // TODO: Task 9
    @PostMapping(path="/book/{name}")
    public ModelAndView bookMovie(@PathVariable("name") String name,HttpSession sess)  {
        ModelAndView mav = new ModelAndView();

        Movie movie =  dbs.getMovieById(Integer.parseInt(name));
        String rate = movie.getRated();
        System.out.println(rate);

        String dob = sess.getAttribute("dob").toString();
        System.out.println(dob);

        DateTimeFormatter f = DateTimeFormatter.ofPattern( "E MMM dd HH:mm:ss z uuuu" )
                                       .withLocale( Locale.US );
        ZonedDateTime zdt = ZonedDateTime.parse( dob , f );
        LocalDate ld = zdt.toLocalDate();

        String todayDate = new Date().toString();
        System.out.println(todayDate);
        ZonedDateTime zdt1 = ZonedDateTime.parse(todayDate,f);
        LocalDate today = zdt1.toLocalDate();
        int years = Period.between(ld, today).getYears();
        System.out.println(years);

        if(years >= 18){
            int newCount  = movie.getCount() + 1;
            movie.setCount(newCount);
            dbs.saveRecord(movie);
            mav.addObject("title",movie.getTitle());
            mav.setViewName("BookSuccess");
            return mav;
        }
        else if(years>=13 && years<18 && !rate.equals("R")){
            int newCount  = movie.getCount() + 1;
            movie.setCount(newCount);
            dbs.saveRecord(movie);
            mav.addObject("title",movie.getTitle());
            mav.setViewName("BookSuccess");
            return mav;
        }else{
            mav.setViewName("BookError");
            return mav;
        }

    }

    // TODO: Task 9
    // ... ...

}
