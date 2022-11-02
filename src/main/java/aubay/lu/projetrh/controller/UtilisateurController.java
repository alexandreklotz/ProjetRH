package aubay.lu.projetrh.controller;

import aubay.lu.projetrh.model.Utilisateur;
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
    @PostMapping("admin/utilisateur/create")
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur){
        return utilisateurService.createUtilisateur(utilisateur);
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @PutMapping("admin/utilisateur/update")
    public Utilisateur updateUtilisateur(@RequestBody Utilisateur utilisateur){
        return utilisateurService.updateUtilisateur(utilisateur);
    }

    @DeleteMapping("admin/utilisateur/delete/{userid}")
    public String deleteUtilisateur(@PathVariable UUID userid){
        return utilisateurService.deleteUtilisateur(userid);
    }

}
