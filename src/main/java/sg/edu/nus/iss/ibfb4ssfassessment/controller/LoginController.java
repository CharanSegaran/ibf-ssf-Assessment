package sg.edu.nus.iss.ibfb4ssfassessment.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.ibfb4ssfassessment.model.Login;
import sg.edu.nus.iss.ibfb4ssfassessment.service.HttpBinService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/home")
public class LoginController {
    
    // TODO: Task 6
    @GetMapping("/login")
    public ModelAndView login(HttpSession sess) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("login", new Login());
        mav.setViewName("view0");
        return mav;
    }

    // TODO: Task 7
    @PostMapping("/login")
    public ModelAndView processlogin(@Valid @ModelAttribute("login") Login login, 
                                    BindingResult result, HttpSession sess) throws ParseException {
       
        ModelAndView mav = new ModelAndView();

        if(result.hasErrors()){
            mav.addObject("login", login);
            mav.setViewName("view0");
            return mav;
        }else{
            mav.setViewName("view1");
            sess.setAttribute("email", login.getEmail());
            sess.setAttribute("dob", login.getDob());
            
            return mav;
        }
        
    }


    // For the logout button shown on View 2
    // On logout, session should be cleared
    @GetMapping("/logout")
    public String logout(HttpSession sess) {
        sess.invalidate();
        return "redirect:/home/login";
    }

   @Autowired
   private HttpBinService httpbinSvc;


   @GetMapping("/healthz")
   @ResponseBody
   public ResponseEntity<String> getHealthz() {
      JsonObject j = Json.createObjectBuilder()
         .build();
      if (httpbinSvc.isAlive())
         return ResponseEntity.ok(j.toString());

      return ResponseEntity.status(400).body(j.toString());
    
    }
}
