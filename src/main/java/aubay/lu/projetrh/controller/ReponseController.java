package aubay.lu.projetrh.controller;

import aubay.lu.projetrh.model.Reponse;
import aubay.lu.projetrh.service.ReponseService;
import aubay.lu.projetrh.view.CustomJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("admin/reponse/all")
    public List<Reponse> getAllReponse(){
        return reponseService.getAllReponses();
    }

    @JsonView(CustomJsonView.ReponseView.class)
    @GetMapping("admin/reponse/id/{repId}")
    public Optional<Reponse> getReponseById(@PathVariable UUID repId){
        return reponseService.getReponseById(repId);
    }

    @JsonView(CustomJsonView.ReponseView.class)
    @GetMapping("admin/reponse/question/{questionId}")
    public List<Reponse> getReponseByQuestion(@PathVariable UUID questionId){
        return reponseService.getReponsesByQuestion(questionId);
    }

    @JsonView(CustomJsonView.ReponseView.class)
    @PostMapping("admin/reponse/create")
    public Reponse createReponse(@RequestBody Reponse reponse){
        return reponseService.createReponse(reponse);
    }

    @JsonView(CustomJsonView.ReponseView.class)
    @PutMapping("admin/reponse/update")
    public Reponse updateReponse(@RequestBody Reponse reponse){
        return reponseService.updateReponse(reponse);
    }

    @JsonView(CustomJsonView.ReponseView.class)
    @DeleteMapping("admin/reponse/delete/{reponseId}")
    public String deleteReponse(@PathVariable UUID reponseId){
        return reponseService.deleteReponse(reponseId);
    }
}
