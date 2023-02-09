package aubay.lu.projetrh.controller;

import aubay.lu.projetrh.model.Utilisateur;
import aubay.lu.projetrh.repository.UtilisateurRepository;
import aubay.lu.projetrh.service.UtilisateurService;
import aubay.lu.projetrh.view.CustomJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
public class UtilisateurController {

    private UtilisateurService utilisateurService;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    UtilisateurController(UtilisateurService utilisateurService){
        this.utilisateurService = utilisateurService;
    }


    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("admin/utilisateur/all")
    public List<Utilisateur> getAllUtilisateurs(){
        return utilisateurService.getAllUtilisateurs();
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("admin/utilisateur/id/{userid}")
    public Optional<Utilisateur> getUtilisateurById(@PathVariable UUID userid){
        return utilisateurService.getUtilisateurById(userid);
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("admin/utilisateur/login/{userlogin}")
    public Optional<Utilisateur> getUtilisateurByLogin(@PathVariable String userlogin){
        return utilisateurService.getUtilisateurByLogin(userlogin);
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("moderateur/utilisateur/candidats")
    public List<Utilisateur> getAllCandidats(){
        return utilisateurService.getAllCandidats();
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @PostMapping("admin/utilisateur/create")
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur){
        return utilisateurService.createUtilisateur(utilisateur);
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @PostMapping("moderateur/utilisateur/candidat/create")
    public Utilisateur createCandidat(@RequestBody Utilisateur utilisateur){
        return utilisateurService.createCandidat(utilisateur);
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("moderateur/utilisateur/candidat/id/{candidatId}")
    public Optional<Utilisateur> getCandidatById(@PathVariable UUID candidatId){
        return utilisateurService.getCandidatById(candidatId);
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @PutMapping("admin/utilisateur/update")
    public Utilisateur updateUtilisateur(@RequestBody Utilisateur utilisateur){
        return utilisateurService.updateUtilisateur(utilisateur);
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @PutMapping("moderateur/utilisateur/candidat/update")
    public Utilisateur updateCandidat(@RequestBody Utilisateur utilisateur){
        return utilisateurService.updateCandidat(utilisateur);
    }

    @DeleteMapping("admin/utilisateur/delete/{userid}")
    public String deleteUtilisateur(@PathVariable UUID userid){
        return utilisateurService.deleteUtilisateur(userid);
    }

    @DeleteMapping("moderateur/utilisateur/candidat/delete/{candidatid}")
    public void deleteCandidat(@PathVariable UUID candidatid) {
        utilisateurService.deleteCandidat(candidatid);
    }

}
