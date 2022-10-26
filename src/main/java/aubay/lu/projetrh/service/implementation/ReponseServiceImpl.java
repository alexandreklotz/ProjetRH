package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.Question;
import aubay.lu.projetrh.model.Reponse;
import aubay.lu.projetrh.repository.QuestionRepository;
import aubay.lu.projetrh.repository.ReponseRepository;
import aubay.lu.projetrh.service.ReponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReponseServiceImpl implements ReponseService {

    private ReponseRepository reponseRepository;
    private QuestionRepository questionRepository;

    @Autowired
    ReponseServiceImpl(ReponseRepository reponseRepository, QuestionRepository questionRepository){
        this.reponseRepository = reponseRepository;
        this.questionRepository = questionRepository;
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
    public Reponse createReponse(Reponse reponse) {

        Optional<Question> question = questionRepository.findById(reponse.getQuestion().getId());

        if(question.isEmpty()){
            return null; //return une erreur
        }

        if(reponse.getQuestion() == null){
            return null; //return une erreur
        } else if (reponse.getQuestion() != null){
            Optional<Question> repQuestion = questionRepository.findById(reponse.getQuestion().getId());
            if(repQuestion.isEmpty()){
                return null; //return erreur
            }
            reponse.setQuestion(repQuestion.get());
        }

        reponse.setSelectedAnswer(false);

        return reponseRepository.saveAndFlush(reponse);
    }

    @Override
    public Reponse updateReponse(Reponse reponse) {
        Optional<Reponse> reponseToUpdate = reponseRepository.findById(reponse.getId());
        if(reponseToUpdate.isEmpty()){
            return null; //Return une erreur
        }
        if(reponse.getQuestion() == null){
            return null; //return une erreur
        } else if (reponse.getQuestion() != null){
            Optional<Question> question = questionRepository.findById(reponse.getQuestion().getId());
            if(question.isEmpty()){
                return null; //return erreur
            }
            reponse.setQuestion(question.get());
        }
        if(reponse.getTexte().isEmpty()){
            return null; //return erreur
        }

        return reponseRepository.saveAndFlush(reponse);
    }

    @Override
    public String deleteReponse(UUID reponseId) {
        reponseRepository.deleteById(reponseId);
        return "La réponse id " + reponseId + " a été supprimée.";
    }
}
