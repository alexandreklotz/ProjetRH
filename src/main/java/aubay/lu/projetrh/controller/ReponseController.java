package aubay.lu.projetrh.controller;

import aubay.lu.projetrh.model.Reponse;
import aubay.lu.projetrh.service.ReponseService;
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
public class ReponseController {

    private ReponseService reponseService;

    @Autowired
    ReponseController(ReponseService reponseService){
        this.reponseService = reponseService;
    }

    @JsonView(CustomJsonView.ReponseView.class)
    @GetMapping("moderateur/reponse/all")
    public List<Reponse> getAllReponse(){
        return reponseService.getAllReponses();
    }

    @JsonView(CustomJsonView.ReponseView.class)
    @GetMapping("moderateur/reponse/id/{repId}")
    public Optional<Reponse> getReponseById(@PathVariable UUID repId){
        return reponseService.getReponseById(repId);
    }

    @JsonView(CustomJsonView.ReponseView.class)
    @GetMapping("moderateur/reponse/question/{questionId}")
    public List<Reponse> getReponseByQuestion(@PathVariable UUID questionId){
        return reponseService.getReponsesByQuestion(questionId);
    }

    @JsonView(CustomJsonView.ReponseView.class)
    @PostMapping("moderateur/reponse/create/{questionId}")
    public Reponse createReponse(@RequestBody Reponse reponse, @PathVariable UUID questionId){
        return reponseService.createReponse(questionId, reponse);
    }

    @JsonView(CustomJsonView.ReponseView.class)
    @PutMapping("moderateur/reponse/update")
    public Reponse updateReponse(@RequestBody Reponse reponse){
        return reponseService.updateReponse(reponse);
    }

    @JsonView(CustomJsonView.ReponseView.class)
    @DeleteMapping("moderateur/reponse/delete/{reponseId}")
    public String deleteReponse(@PathVariable UUID reponseId){
        return reponseService.deleteReponse(reponseId);
    }
}
