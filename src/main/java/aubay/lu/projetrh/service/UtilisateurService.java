package aubay.lu.projetrh.service;

import aubay.lu.projetrh.model.Utilisateur;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UtilisateurService {

    public boolean isUserExisting(String userLogin);

    public boolean mailAddressInUse(String mailAddress);

    public List<Utilisateur> getAllUtilisateurs();

    public Optional<Utilisateur> getUtilisateurById(UUID userid);

    public Optional<Utilisateur> getUtilisateurByLogin(String userlogin);

    public Utilisateur createUtilisateur(Utilisateur utilisateur);

    public Utilisateur updateUtilisateur(Utilisateur utilisateur);

    public String deleteUtilisateur(UUID userid);

    public Utilisateur userProfileUpdate(Utilisateur utilisateur, String userlogin);

    public Utilisateur userSelfRetrieve(String userlogin);

}
