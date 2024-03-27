package sg.edu.nus.iss.ibfb4ssfassessment.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class EventController implements ErrorController{
    
    @RequestMapping("/error")
    public String handleError() {
        return "redirect:/home/login";
    }
    
}
