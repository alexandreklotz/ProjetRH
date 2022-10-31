package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.Qcm;
import aubay.lu.projetrh.model.Question;
import aubay.lu.projetrh.model.Reponse;
import aubay.lu.projetrh.repository.QcmRepository;
import aubay.lu.projetrh.repository.QuestionRepository;
import aubay.lu.projetrh.repository.ReponseRepository;
import aubay.lu.projetrh.service.QuestionService;
import aubay.lu.projetrh.service.ReponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;
    private QcmRepository qcmRepository;
    private ReponseRepository reponseRepository;
    private ReponseService reponseService;

    @Autowired
    QuestionServiceImpl(QuestionRepository questionRepository, QcmRepository qcmRepository, ReponseRepository reponseRepository, ReponseService reponseService){
        this.questionRepository = questionRepository;
        this.qcmRepository = qcmRepository;
        this.reponseRepository = reponseRepository;
        this.reponseService = reponseService;
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
    public List<Question> getQuestionByQcmId(UUID qcmId) {
        return questionRepository.findQuestionByQcmId(qcmId);
    }

    @Override
    public Question createQuestion(Question question) {

        if(question.getTexte() == null){
            return null; //return une erreur
        }

        if(question.getQcm() != null){
            Optional<Qcm> qcm = qcmRepository.findById(question.getQcm().getId());
            if(qcm.isEmpty()){
                return null; //return une erreur
            }
            question.setQcm(qcm.get());
        }

        questionRepository.saveAndFlush(question);

        if(question.getReponses() != null){
            if(question.getReponses().size() < 2){
                return null; //return qu'il faut au minimum 2 réponses. //TODO : return erreur
            }
            for(Reponse reponse : question.getReponses()){
                reponseService.createReponse(question.getId(), reponse);
            }
        } else {
            return null; //return une erreur
        }

        return question;
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

        if(question.getReponses() != null){
            if(question.getReponses().size() < 2){
                return null; //return qu'il faut au minimum 2 réponses. //TODO : return erreur
            }
            for(Reponse reponse : question.getReponses()){
                Optional<Reponse> currentReponse = reponseRepository.findById(reponse.getId());
                if(currentReponse.isEmpty()){
                    reponseService.createReponse(questionToUpdate.get().getId(), reponse);
                } else if(currentReponse.isPresent()){
                    reponseService.updateReponse(reponse);
                }
            }
        } else {
            return null;  //return erreur
        }

        if(question.getQcm() != null){
            Optional<Qcm> qcm = qcmRepository.findById(question.getQcm().getId());
            if(qcm.isEmpty()){
                return null; //return une erreur
            }
            question.setQcm(qcm.get());
        }

        return questionRepository.saveAndFlush(question);
    }

    @Override
    public String deleteQuestion(UUID questionId) {
        Optional<Question> questionToDelete = questionRepository.findById(questionId);
        if(questionToDelete.isEmpty()){
            return null; //question déja supprimée ou autre
        }
        for(Reponse reponse : questionToDelete.get().getReponses()){
            Optional<Reponse> repToDelete = reponseRepository.findById(reponse.getId());
            if(repToDelete.isEmpty()){
                return null; //reponse déja supprimée ou autre
            }
            reponseService.deleteReponse(repToDelete.get().getId());
        }

        questionRepository.deleteById(questionId);
        return "La question avec l'id " + questionId + " a été supprimée ainsi que les réponses liées.";
    }
}
