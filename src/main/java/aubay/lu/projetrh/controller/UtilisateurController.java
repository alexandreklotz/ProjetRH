package aubay.lu.projetrh.controller;

import aubay.lu.projetrh.model.Utilisateur;
import aubay.lu.projetrh.service.implementation.UtilisateurServiceImpl;
import aubay.lu.projetrh.view.CustomJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
public class UtilisateurController {

    private UtilisateurServiceImpl utilisateurServiceImpl;

    @Autowired
    UtilisateurController(UtilisateurServiceImpl utilisateurServiceImpl){
        this.utilisateurServiceImpl = utilisateurServiceImpl;
    }


    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("admin/utilisateur/all")
    public List<Utilisateur> getAllUtilisateurs(){
        return utilisateurServiceImpl.getAllUtilisateurs();
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("admin/utilisateur/id/{userid}")
    public Optional<Utilisateur> getUtilisateurById(@PathVariable UUID userid){
        return utilisateurServiceImpl.getUtilisateurById(userid);
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("admin/utilisateur/login/{userlogin}")
    public Optional<Utilisateur> getUtilisateurByLogin(@PathVariable String userlogin){
        return utilisateurServiceImpl.getUtilisateurByLogin(userlogin);
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @PostMapping("admin/utilisateur/create")
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur){
        return utilisateurServiceImpl.createUtilisateur(utilisateur);
    }

    @JsonView(CustomJsonView.UtilisateurView.class)
    @PutMapping("admin/utilisateur/update")
    public Utilisateur updateUtilisateur(@RequestBody Utilisateur utilisateur){
        return utilisateurServiceImpl.updateUtilisateur(utilisateur);
    }

    @DeleteMapping("admin/utilisateur/delete/{userid}")
    public String deleteUtilisateur(@PathVariable UUID userid){
        return utilisateurServiceImpl.deleteUtilisateur(userid);
    }

}
