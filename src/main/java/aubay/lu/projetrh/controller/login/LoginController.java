package aubay.lu.projetrh.controller.login;

import aubay.lu.projetrh.bean.AuthenticationBean;
import aubay.lu.projetrh.model.Utilisateur;
import aubay.lu.projetrh.repository.UtilisateurRepository;
import aubay.lu.projetrh.security.JwtUtil;
import aubay.lu.projetrh.security.SecurityUserDetails;
import aubay.lu.projetrh.security.SecurityUserDetailsService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(produces = "application/json")
@CrossOrigin(origins = "http://localhost:4200") //Décommenter par la suite
public class LoginController {

    private UtilisateurRepository utilisateurRepository;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private SecurityUserDetailsService securityUserDetailsService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    LoginController(UtilisateurRepository utilisateurRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager,
                    SecurityUserDetailsService securityUserDetailsService, PasswordEncoder passwordEncoder){

        this.utilisateurRepository = utilisateurRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.securityUserDetailsService = securityUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/authentification")
    public Map<String, Object> authentification(@RequestBody Utilisateur utilisateur) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            utilisateur.getUserLogin(), utilisateur.getUserPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Mauvais login ou mot de passe");
        }

        SecurityUserDetails securityUserDetails = this.securityUserDetailsService.loadUserByUsername(utilisateur.getUserLogin());

        return Collections.singletonMap("jwt_token", jwtUtil.generateToken(securityUserDetails));
    }

    @GetMapping("/recruteur/test")
    public String testRecruteur(){
        return "L'URL /recruteur fonctionne.";
    }


}
