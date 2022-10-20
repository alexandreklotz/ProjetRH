package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.Roles;
import aubay.lu.projetrh.model.Utilisateur;
import aubay.lu.projetrh.repository.RolesRepository;
import aubay.lu.projetrh.repository.UtilisateurRepository;
import aubay.lu.projetrh.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {


    private UtilisateurRepository utilisateurRepository;
    private RolesRepository rolesRepository;


    @Autowired
    UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, RolesRepository rolesRepository){
        this.utilisateurRepository = utilisateurRepository;
        this.rolesRepository = rolesRepository;
    }


    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Optional<Utilisateur> getUtilisateurById(UUID userid) {
        return utilisateurRepository.findById(userid);
    }

    @Override
    public Optional<Utilisateur> getUtilisateurByLogin(String userlogin) {
        return utilisateurRepository.findUserWithLogin(userlogin);
    }

    @Override
    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        //TODO : Gérer les champs non remplis comme MDP ou email etc en renvoyant des erreurs
        return utilisateurRepository.saveAndFlush(utilisateur);
    }

    @Override
    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        Optional<Utilisateur> userToUpdate = utilisateurRepository.findById(utilisateur.getId());
        if(userToUpdate.isEmpty()){
            return null; //return une erreur
        }

        if(utilisateur.getUserLogin() == null){
            utilisateur.setUserLogin(userToUpdate.get().getUserLogin());
        }

        if(utilisateur.getFirstName() == null){
            utilisateur.setFirstName(userToUpdate.get().getFirstName());
        }

        if(utilisateur.getLastName() == null){
            utilisateur.setLastName(userToUpdate.get().getLastName());
        }

        if(utilisateur.getRole() == null){
            utilisateur.setRole(userToUpdate.get().getRole());
        } else {
            Optional<Roles> role = rolesRepository.findById(utilisateur.getRole().getId());
            if(role.isEmpty()){
                return null; //return erreur
            }
            utilisateur.setRole(role.get());
            rolesRepository.saveAndFlush(role.get());
        }

        if(utilisateur.getMailAddress() == null) {
            utilisateur.setMailAddress(userToUpdate.get().getMailAddress());
        }

        if(utilisateur.getUserPassword() == null){
            utilisateur.setUserPassword(userToUpdate.get().getUserPassword());
        }

        return utilisateurRepository.saveAndFlush(utilisateur);
    }

    @Override
    public String deleteUtilisateur(UUID userid) {
        utilisateurRepository.deleteById(userid);
        return "L'utilisateur avec l'id " + userid + " a été supprimé";
        //return un message plus complet ? Remplacer le string par une responseentity.
    }
}
