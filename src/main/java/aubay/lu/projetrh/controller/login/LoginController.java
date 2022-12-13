package aubay.lu.projetrh.controller.login;

import aubay.lu.projetrh.bean.AuthenticationBean;
import aubay.lu.projetrh.model.Utilisateur;
import aubay.lu.projetrh.repository.UtilisateurRepository;
import aubay.lu.projetrh.security.JwtUtil;
import aubay.lu.projetrh.security.SecurityUserDetails;
import aubay.lu.projetrh.security.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
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
    public ResponseEntity<String> authentification(@RequestBody Utilisateur utilisateur) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            utilisateur.getUserLogin(), utilisateur.getUserPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Mauvais pseudo / mot de passe");
        }

        SecurityUserDetails securityUserDetails = this.securityUserDetailsService.loadUserByUsername(utilisateur.getUserLogin());

        return ResponseEntity.ok(jwtUtil.generateToken(securityUserDetails));
    }


}
