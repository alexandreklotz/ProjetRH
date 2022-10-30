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

        if(question.getQcm() != null){
            Optional<Qcm> qcm = qcmRepository.findById(question.getQcm().getId());
            if(qcm.isEmpty()){
                return null; //return une erreur
            }
            question.setQcm(qcm.get());
        }


        if(question.getTexte() == null){
            return null; //return une erreur
        }

        if(question.getReponses() != null){
            if(question.getReponses().size() < 2){
                return null; //return qu'il faut au minimum 2 réponses. //TODO : tester
            }
            for(Reponse reponse : question.getReponses()){
                reponseService.createReponse(reponse);
            }
        } else {
            return null; //return une erreur
        }

        return questionRepository.saveAndFlush(question);
    }


    @Override
    public Question updateQuestion(Question question) {
        Optional<Question> questionToUpdate = questionRepository.findById(question.getId());
        if(questionToUpdate.isEmpty()){
            return null; //return une erreur
        }

        if(question.getQcm() != null){
            Optional<Qcm> qcm = qcmRepository.findById(question.getQcm().getId());
            if(qcm.isEmpty()){
                return null; //return une erreur
            }
            question.setQcm(qcm.get());
        }

        if(question.getTexte() == null){
            question.setTexte(questionToUpdate.get().getTexte());
        }

        if(question.getReponses() != null){
            if(question.getReponses().size() < 2){
                return null; //return qu'il faut au minimum 2 réponses. //TODO : tester/modifier, pas correct
            }
            for(Reponse reponse : question.getReponses()){
                Optional<Reponse> currentReponse = reponseRepository.findById(reponse.getId());
                if(currentReponse.isEmpty()){
                    reponseService.createReponse(reponse);
                } else if(currentReponse.isPresent()){
                    reponseService.updateReponse(reponse);
                }
            }
        } else {
            return null;  //return erreur
        }

        return questionRepository.saveAndFlush(question);
    }

    @Override
    public String deleteQuestion(UUID questionId) {
        questionRepository.deleteById(questionId);
        return "La question avec l'id " + questionId + " a été supprimée.";
    }
}
