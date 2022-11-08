package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.Roles;
import aubay.lu.projetrh.model.Utilisateur;
import aubay.lu.projetrh.repository.RolesRepository;
import aubay.lu.projetrh.repository.UtilisateurRepository;
import aubay.lu.projetrh.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {


    private UtilisateurRepository utilisateurRepository;
    private RolesRepository rolesRepository;
    private PasswordEncoder passwordEncoder;


    @Autowired
    UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder){
        this.utilisateurRepository = utilisateurRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public boolean isUserExisting(String userLogin) {
        Optional<Utilisateur> existingUser = utilisateurRepository.findUserWithLogin(userLogin);
        if(existingUser.isPresent()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean mailAddressInUse(String mailAddress) {
        Optional<Utilisateur> existingUser = utilisateurRepository.findIfUsedMailAddress(mailAddress);
        if(existingUser.isPresent()){
            return true;
        } else {
            return false;
        }
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

        boolean isExistingUser = isUserExisting(utilisateur.getUserLogin());
        if(isExistingUser){
            return null; //pseudo déja pris
        }

        boolean mailInUse = mailAddressInUse(utilisateur.getMailAddress());
        if(mailInUse){
            return null; //email déja utilisé
        }

        utilisateur.setUserPassword(passwordEncoder.encode(utilisateur.getUserPassword()));
        return utilisateurRepository.saveAndFlush(utilisateur);
    }

    @Override
    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        Optional<Utilisateur> userToUpdate = utilisateurRepository.findById(utilisateur.getId());
        if(userToUpdate.isEmpty()){
            return null;
        }

        boolean isAlreadyExisting = isUserExisting(utilisateur.getUserLogin());
        if(isAlreadyExisting && !utilisateur.getUserLogin().equals(userToUpdate.get().getUserLogin())){
            return null; //cela veut dire que le login renseigné par l'utilisateur lors de la maj de son profil est déja pris
        } else if(utilisateur.getUserLogin() == null){
            utilisateur.setUserLogin(userToUpdate.get().getUserLogin());
        }

        boolean mailInUse = mailAddressInUse(utilisateur.getMailAddress());
        if(mailInUse && !utilisateur.getMailAddress().equals(userToUpdate.get().getMailAddress())){
            return null; //email déja utilisé
        } else if(utilisateur.getMailAddress() == null) {
            utilisateur.setMailAddress(userToUpdate.get().getMailAddress());
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
                return null;
            }
            utilisateur.setRole(role.get());
            rolesRepository.saveAndFlush(role.get());
        }


        if(utilisateur.getUserPassword() != null){
            utilisateur.setUserPassword(passwordEncoder.encode(utilisateur.getUserPassword()));
        } else {
            utilisateur.setUserPassword(userToUpdate.get().getUserPassword());
        }

        return utilisateurRepository.saveAndFlush(utilisateur);
    }

    @Override
    public String deleteUtilisateur(UUID userid) {
        utilisateurRepository.deleteById(userid);
        return "L'utilisateur avec l'id " + userid + " a été supprimé.";
    }

    @Override
    public Utilisateur userProfileUpdate(Utilisateur utilisateur, String userlogin) {

        Optional<Utilisateur> userToUpdate = utilisateurRepository.findUserWithLogin(userlogin);
        if(userToUpdate.isEmpty()){
            return null; //return erreur
        }

        boolean isAlreadyExisting = isUserExisting(utilisateur.getUserLogin());
        if(isAlreadyExisting && !utilisateur.getUserLogin().equals(userToUpdate.get().getUserLogin())){
            return null; //cela veut dire que le login renseigné par l'utilisateur lors de la maj de son profil est déja pris
        } else if(utilisateur.getUserLogin() == null){
            utilisateur.setUserLogin(userToUpdate.get().getUserLogin());
        }

        if(utilisateur.getRole() != null){
            return null; //le candidat ne peut évidemment pas modifier son propre rôle
        } else {
            utilisateur.setRole(userToUpdate.get().getRole());
        }

        if(utilisateur.getUserPassword() != null){
            utilisateur.setUserPassword(passwordEncoder.encode(utilisateur.getUserPassword()));
        } else if(utilisateur.getUserPassword() == null){
            utilisateur.setUserPassword(userToUpdate.get().getUserPassword());
        }

        boolean mailInUse = mailAddressInUse(utilisateur.getMailAddress());
        if(mailInUse && !utilisateur.getMailAddress().equals(userToUpdate.get().getMailAddress())){
            return null; //email déja utilisé
        } else if(utilisateur.getMailAddress() == null){
            utilisateur.setMailAddress(userToUpdate.get().getMailAddress());
        }

        if(utilisateur.getFirstName() == null){
            utilisateur.setFirstName(userToUpdate.get().getFirstName());
        }

        if(utilisateur.getLastName() == null){
            utilisateur.setLastName(userToUpdate.get().getLastName());
        }

        return utilisateurRepository.saveAndFlush(utilisateur);

    }

    @Override
    public Utilisateur userSelfRetrieve(String userlogin) {
        Optional<Utilisateur> retrievedUser = utilisateurRepository.findUserWithLogin(userlogin);
        if(retrievedUser.isEmpty()){
            return null; //return erreur
        }
        return retrievedUser.get();
    }
}
