package sg.edu.nus.iss.ibfb4ssfassessment.model;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;

public class Login {

    @Email(message = "Not a valid email")
    @NotEmpty(message = "Please key in an email")
    @Length(max=50, message = "cannot exceed 50 characters")
    private String email;

    @Past(message = "Date of birth must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    public Login(@Email(message = "Not a valid email") @NotEmpty(message = "Please key in an email") String email,
            @Past(message = "Date of birth must be in the past") Date dob) {
        this.email = email;
        this.dob = dob;
    }

    public Login() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    
}
