package aubay.lu.projetrh.service;

import aubay.lu.projetrh.model.Utilisateur;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UtilisateurService {

    public List<Utilisateur> getAllUtilisateurs();

    public Optional<Utilisateur> getUtilisateurById(UUID userid);

    public Optional<Utilisateur> getUtilisateurByLogin(String userlogin);

    public Utilisateur createUtilisateur(Utilisateur utilisateur);

    public Utilisateur updateUtilisateur(Utilisateur utilisateur);

    public String deleteUtilisateur(UUID userid);

}
