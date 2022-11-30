package aubay.lu.projetrh.controller.login;

import aubay.lu.projetrh.bean.AuthenticationBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200") //Décommenter par la suite
public class LoginController {

    @GetMapping("/basicauth")
    public AuthenticationBean basicAuth(){
        return new AuthenticationBean("You are now logged in !");
    }

    @GetMapping("/logintest")
    public String getGreeting(){
        return "Hello World !";
    }

}
