package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.Roles;
import aubay.lu.projetrh.model.Utilisateur;
import aubay.lu.projetrh.repository.RolesRepository;
import aubay.lu.projetrh.repository.UtilisateurRepository;
import aubay.lu.projetrh.service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {


    private UtilisateurRepository utilisateurRepository;
    private RolesRepository rolesRepository;
    private PasswordEncoder passwordEncoder;
    private static Logger log = LoggerFactory.getLogger(UtilisateurServiceImpl.class);


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
    public List<Utilisateur> getAllCandidats() {
        Optional<Roles> roleCandidat = rolesRepository.findRoleByName("CANDIDAT");
        if(roleCandidat.isEmpty()){
            return null;
        }
        List<Utilisateur> allCandidats = (List<Utilisateur>) roleCandidat.get().getUtilisateurs();
        return allCandidats;
    }

    @Override
    public Utilisateur createUtilisateur(Utilisateur utilisateur) {

        boolean isExistingUser = isUserExisting(utilisateur.getUserLogin());
        if(isExistingUser){
            log.error("Erreur lors de la création de {}. Ce login est déja assigné à un utilisateur existant.", utilisateur.getUserLogin());
            return null; //pseudo déja pris
        }

        boolean mailInUse = mailAddressInUse(utilisateur.getMailAddress());
        if(mailInUse){
            log.error("Erreur lors de la création de l'utilisateur {}. L'adresse mail {} est déja utilisée.", utilisateur.getUserLogin(), utilisateur.getMailAddress());
            return null; //email déja utilisé
        }

        utilisateur.setUserPassword(passwordEncoder.encode(utilisateur.getUserPassword()));
        log.info("L'utilisateur {} a été créé.", utilisateur.getUserLogin());
        return utilisateurRepository.saveAndFlush(utilisateur);
    }

    @Override
    public Utilisateur createCandidat(Utilisateur utilisateur) {
        boolean isExistingUser = isUserExisting(utilisateur.getUserLogin());
        if(isExistingUser){
            log.error("Erreur lors de la création de {}. Ce login est déja assigné à un utilisateur existant.", utilisateur.getUserLogin());
            return null; //pseudo déja pris
        }

        boolean mailInUse = mailAddressInUse(utilisateur.getMailAddress());
        if(mailInUse){
            log.error("Erreur lors de la création de l'utilisateur {}. L'adresse mail {} est déja utilisée.", utilisateur.getUserLogin(), utilisateur.getMailAddress());
            return null; //email déja utilisé
        }

        Optional<Roles> candidatRole = rolesRepository.findRoleByName("CANDIDAT");
        if(candidatRole.isEmpty()){
            log.info("ERROR : Le rôle candidat n'existe pas. Impossible de le récupérer. Création du candidat {} annulée.", utilisateur.getUserLogin());
            return null;
        }

        if(utilisateur.getRole() != null){
            utilisateur.setRole(candidatRole.get());
        } else if (utilisateur.getRole() == null){
            utilisateur.setRole(candidatRole.get());
        }


        utilisateur.setUserPassword(passwordEncoder.encode(utilisateur.getUserPassword()));
        log.info("Le candidat {} a été créé.", utilisateur.getUserLogin());
        return utilisateurRepository.saveAndFlush(utilisateur);
    }

    @Override
    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        Optional<Utilisateur> userToUpdate = utilisateurRepository.findById(utilisateur.getId());
        if(userToUpdate.isEmpty()){
            log.error("L'utilisateur avec le login {} n'existe pas. Impossible de le modifier.", utilisateur.getUserLogin());
            return null;
        }

        boolean isAlreadyExisting = isUserExisting(utilisateur.getUserLogin());
        if(isAlreadyExisting && !utilisateur.getUserLogin().equals(userToUpdate.get().getUserLogin())){
            log.error("Impossible d'assigner le login {} à l'utilisateur {}, il est déja utilisé.", utilisateur.getUserLogin(), userToUpdate.get().getUserLogin());
            return null; //cela veut dire que le login renseigné par l'utilisateur lors de la maj de son profil est déja pris
        } else if(utilisateur.getUserLogin() == null){
            utilisateur.setUserLogin(userToUpdate.get().getUserLogin());
        }

        boolean mailInUse = mailAddressInUse(utilisateur.getMailAddress());
        if(mailInUse && !utilisateur.getMailAddress().equals(userToUpdate.get().getMailAddress())){
            log.error("Erreur lors de la création de l'utilisateur {}. L'adresse mail {} est déja utilisée.", utilisateur.getUserLogin(), utilisateur.getMailAddress());
            return null; //email déja utilisé
        } else if(utilisateur.getMailAddress() == null) {
            utilisateur.setMailAddress(userToUpdate.get().getMailAddress());
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
                log.error("Impossible d'assigner le rôle avec l'ID {} à l'utilisateur {}. Ce rôle n'existe pas.", utilisateur.getRole().getId(), utilisateur.getUserLogin());
                return null;
            }
            utilisateur.setRole(role.get());
            rolesRepository.saveAndFlush(role.get());
            log.info("Le rôle {} a été assigné à l'utilisateur {}.", role.get().getDenomination(), utilisateur.getUserLogin());
        }


        if(utilisateur.getUserPassword() != null){
            log.info("Le mot de passe de l'utilisateur {} a été modifié.", utilisateur.getUserLogin());
            utilisateur.setUserPassword(passwordEncoder.encode(utilisateur.getUserPassword()));
        } else {
            utilisateur.setUserPassword(userToUpdate.get().getUserPassword());
        }

        log.info("L'utilisateur {} a été mis à jour.", utilisateur.getUserLogin());
        return utilisateurRepository.saveAndFlush(utilisateur);
    }

    @Override
    public Utilisateur updateCandidat(Utilisateur utilisateur) {
        Optional<Utilisateur> candidatToUpdate = utilisateurRepository.findById(utilisateur.getId());
        if(candidatToUpdate.isEmpty()){
            return null;
        }

        if(candidatToUpdate.get().getRole().getId() != 3L){
            return null; //L'ID du rôle ne correspond pas à l'id du rôle candidat
        }

        boolean isAlreadyExisting = isUserExisting(utilisateur.getUserLogin());
        if(isAlreadyExisting && !utilisateur.getUserLogin().equals(candidatToUpdate.get().getUserLogin())){
            log.error("Impossible d'assigner le login {} à l'utilisateur {}, il est déja utilisé.", utilisateur.getUserLogin(), candidatToUpdate.get().getUserLogin());
            return null; //cela veut dire que le login renseigné par l'utilisateur lors de la maj de son profil est déja pris
        } else if(utilisateur.getUserLogin() == null){
            utilisateur.setUserLogin(candidatToUpdate.get().getUserLogin());
        }

        boolean mailInUse = mailAddressInUse(utilisateur.getMailAddress());
        if(mailInUse && !utilisateur.getMailAddress().equals(candidatToUpdate.get().getMailAddress())){
            log.error("Erreur lors de la création de l'utilisateur {}. L'adresse mail {} est déja utilisée.", utilisateur.getUserLogin(), utilisateur.getMailAddress());
            return null; //email déja utilisé
        } else if(utilisateur.getMailAddress() == null) {
            utilisateur.setMailAddress(candidatToUpdate.get().getMailAddress());
        }


        if(utilisateur.getFirstName() == null){
            utilisateur.setFirstName(candidatToUpdate.get().getFirstName());
        }

        if(utilisateur.getLastName() == null){
            utilisateur.setLastName(candidatToUpdate.get().getLastName());
        }

        if(utilisateur.getUserPassword() != null){
            log.info("Le mot de passe de l'utilisateur {} a été modifié.", utilisateur.getUserLogin());
            utilisateur.setUserPassword(passwordEncoder.encode(utilisateur.getUserPassword()));
        } else {
            utilisateur.setUserPassword(candidatToUpdate.get().getUserPassword());
        }

        log.info("Le candidat {} a été mis à jour.", utilisateur.getUserLogin());
        return utilisateurRepository.saveAndFlush(utilisateur);
    }

    @Override
    public String deleteUtilisateur(UUID userid) {
        utilisateurRepository.deleteById(userid);
        log.info("L'utilisateur avec l'id {} a été supprimé.", userid);
        return "L'utilisateur avec l'id " + userid + " a été supprimé.";
    }

    @Override
    public Utilisateur userProfileUpdate(Utilisateur utilisateur, String userlogin) {

        Optional<Utilisateur> userToUpdate = utilisateurRepository.findUserWithLogin(userlogin);
        if(userToUpdate.isEmpty()){
            log.error("L'utilisateur {} a tenté de modifier son profil alors qu'il semble ne pas exister.", userlogin);
            return null; //return erreur
        }

        boolean isAlreadyExisting = isUserExisting(utilisateur.getUserLogin());
        if(isAlreadyExisting && !utilisateur.getUserLogin().equals(userToUpdate.get().getUserLogin())){
            log.error("Impossible d'assigner le login {} à l'utilisateur {}, il est déja utilisé.", utilisateur.getUserLogin(), userToUpdate.get().getUserLogin());
            return null; //cela veut dire que le login renseigné par l'utilisateur lors de la maj de son profil est déja pris
        } else if(utilisateur.getUserLogin() == null){
            utilisateur.setUserLogin(userToUpdate.get().getUserLogin());
        }

        if(utilisateur.getRole() != null){
            log.warn("L'utilisateur {} a tenté de modifier son rôle. Vérifier s'il s'agit d'une manipulation intentionnelle ou non.", utilisateur.getUserLogin());
            return null; //le candidat ne peut évidemment pas modifier son propre rôle
        } else {
            utilisateur.setRole(userToUpdate.get().getRole());
        }

        if(utilisateur.getUserPassword() != null){
            log.info("L'utilisateur {} a changé son mot de passe.", utilisateur.getUserLogin());
            utilisateur.setUserPassword(passwordEncoder.encode(utilisateur.getUserPassword()));
        } else if(utilisateur.getUserPassword() == null){
            utilisateur.setUserPassword(userToUpdate.get().getUserPassword());
        }

        boolean mailInUse = mailAddressInUse(utilisateur.getMailAddress());
        if(mailInUse && !utilisateur.getMailAddress().equals(userToUpdate.get().getMailAddress())){
            log.error("Erreur lors de la création de l'utilisateur {}. L'adresse mail {} est déja utilisée.", utilisateur.getUserLogin(), utilisateur.getMailAddress());
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

        log.info("L'utilisateur {} a modifié son profil.", utilisateur.getUserLogin());
        return utilisateurRepository.saveAndFlush(utilisateur);

    }

    @Override
    public Utilisateur userSelfRetrieve(String userlogin) {
        Optional<Utilisateur> retrievedUser = utilisateurRepository.findUserWithLogin(userlogin);
        if(retrievedUser.isEmpty()){
            log.error("Impossible pour l'utilisateur {} de récupérer son profil.", userlogin);
            return null; //return erreur
        }
        return retrievedUser.get();
    }

    @Override
    public void deleteCandidat(UUID candidatId) {
        Optional<Utilisateur> candidatToDelete = utilisateurRepository.findById(candidatId);
        if(candidatToDelete.isPresent()){
            if(candidatToDelete.get().getRole().getId() == 3){
                utilisateurRepository.deleteById(candidatId);
            }
        }
    }
}
