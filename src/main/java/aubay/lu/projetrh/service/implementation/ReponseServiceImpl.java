package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.Question;
import aubay.lu.projetrh.model.Reponse;
import aubay.lu.projetrh.repository.QuestionRepository;
import aubay.lu.projetrh.repository.ReponseRepository;
import aubay.lu.projetrh.service.ReponseService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReponseServiceImpl implements ReponseService {

    private ReponseRepository reponseRepository;
    private QuestionRepository questionRepository;
    private Logger log;

    @Autowired
    ReponseServiceImpl(ReponseRepository reponseRepository, QuestionRepository questionRepository, Logger log){
        this.reponseRepository = reponseRepository;
        this.questionRepository = questionRepository;
        this.log = log;
    }


    @Override
    public List<Reponse> getAllReponses() {
        return reponseRepository.findAll();
    }

    @Override
    public Optional<Reponse> getReponseById(UUID repId) {
        return reponseRepository.findById(repId);
    }

    @Override
    public List<Reponse> getReponsesByQuestion(UUID questionId) {
        return reponseRepository.findReponsesByQuestion(questionId);
    }


    @Override
    public Reponse createReponse(UUID questionId, Reponse reponse) {
        log.info("Création d'une réponse pour la question {}.", questionId);
        Optional<Question> repQuestion = questionRepository.findById(questionId);
        if(repQuestion.isEmpty()){
            log.error("La question {} n'existe pas. Création impossible.", questionId);
            return null;
        }
        reponse.setQuestion(repQuestion.get());
        log.info("Réponse créée avec succès.");
        return reponseRepository.saveAndFlush(reponse);
    }

    @Override
    public Reponse updateReponse(Reponse reponse) {
        Optional<Reponse> reponseToUpdate = reponseRepository.findById(reponse.getId());
        if(reponseToUpdate.isEmpty()){
            log.error("La réponse {} n'existe pas. Modification impossible.", reponse.getId());
            return null;
        }

        if(reponse.getTexte().isEmpty()){
            log.error("Le texte de la réponse ({}) ne peut pas être vide !", reponse.getId());
            return null;
        }

        if(reponse.getQuestion() == null){
            log.error("La réponse ({}) doit être assignée à une question !", reponse.getId());
            return null;
        }

        log.info("La réponse {} a été modifiée avec succès.", reponse.getId());
        return reponseRepository.saveAndFlush(reponse);
    }

    @Override
    public String deleteReponse(UUID reponseId) {
        reponseRepository.deleteById(reponseId);
        log.info("La réponse {} a été supprimée.", reponseId);
        return "La réponse avec l'id " + reponseId + " a été supprimée";
    }
}
