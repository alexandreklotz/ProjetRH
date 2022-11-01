package aubay.lu.projetrh.security;

import aubay.lu.projetrh.model.Utilisateur;
import aubay.lu.projetrh.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private UtilisateurRepository utilisateurRepository;

    @Autowired
    SecurityUserDetailsService(UtilisateurRepository utilisateurRepository){
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository
                .findUserWithRoles(username)
                .orElseThrow(() -> new UsernameNotFoundException("SECURITY ERROR : L'utilisateur " + username + " qui tente de se connecter n'existe pas."));

        return new SecurityUserDetails(utilisateur);
    }
}
