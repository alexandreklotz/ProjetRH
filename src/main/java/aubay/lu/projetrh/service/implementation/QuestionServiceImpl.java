package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.Qcm;
import aubay.lu.projetrh.model.Question;
import aubay.lu.projetrh.repository.QcmRepository;
import aubay.lu.projetrh.repository.QuestionRepository;
import aubay.lu.projetrh.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;
    private QcmRepository qcmRepository;

    @Autowired
    QuestionServiceImpl(QuestionRepository questionRepository, QcmRepository qcmRepository){
        this.questionRepository = questionRepository;
        this.qcmRepository = qcmRepository;
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Optional<Question> getQuestionById(UUID questionId) {
        return questionRepository.findById(questionId);
    }

    @Override
    public Set<Question> getQuestionByQcmId(UUID qcmId) {
        Optional<Qcm> searchedQcm = qcmRepository.findById(qcmId);
        if(searchedQcm.isEmpty()){
            return null; //return error
        }
        return searchedQcm.get().getQuestions();
    }

    @Override
    public Question createQuestion(Question question) {
        if(question.getQcm() == null){
            return null; //return une erreur
        }

        if(question.getTexte() == null){
            return null; //return une erreur
        }

        if(question.getReponses() != null){
            //Comment implémenter cette partie ? Gérer les réponses non existantes en les sauvegardant en live ?
            //Vérifier que les réponses existent à travers le service ou le repository ?
        }

        return questionRepository.saveAndFlush(question);
    }

    @Override
    public Question updateQuestion(Question question) {
        Optional<Question> questionToUpdate = questionRepository.findById(question.getId());
        if(questionToUpdate.isEmpty()){
            return null; //return une erreur
        }

        if(question.getTexte() == null){
            question.setTexte(questionToUpdate.get().getTexte());
        }

        //TODO : gérer les réponses

        return questionRepository.saveAndFlush(question);
    }

    @Override
    public String deleteQuestion(UUID questionId) {
        questionRepository.deleteById(questionId);
        return "La question avec l'id " + questionId + " a été supprimée.";
    }
}
