package aubay.lu.projetrh.controller.login;

import aubay.lu.projetrh.bean.AuthenticationBean;
import aubay.lu.projetrh.model.Utilisateur;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200") //Décommenter par la suite
public class LoginController {

    @GetMapping("/login")
    public AuthenticationBean basicAuth(){
        return new AuthenticationBean("You are now logged in !");
    }

    @GetMapping("/logintest")
    public String getGreeting(){
        return "Hello World !";
    }

}
