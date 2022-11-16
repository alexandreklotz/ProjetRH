package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.Qcm;
import aubay.lu.projetrh.model.Question;
import aubay.lu.projetrh.model.Reponse;
import aubay.lu.projetrh.repository.QcmRepository;
import aubay.lu.projetrh.repository.QuestionRepository;
import aubay.lu.projetrh.repository.ReponseRepository;
import aubay.lu.projetrh.service.QuestionService;
import aubay.lu.projetrh.service.ReponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

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

        log.info("Début de création d'une question.");
        if(question.getTexte() == null){
            log.error("Impossible de créer la question, un intitulé doit être spécifié !");
            return null; //return une erreur
        }

        if(question.getQcm() != null){
            Optional<Qcm> qcm = qcmRepository.findById(question.getQcm().getId());
            if(qcm.isEmpty()){
                log.error("Le qcm {} assigné à la question n'existe pas. Assignation impossible.", qcm.get().getId());
                return null; //return une erreur
            }
            question.setQcm(qcm.get());
        }

        log.info("Vérification du nombre de réponses de chaque question (2 minimum).");
        if(question.getReponses() != null){
            if(question.getReponses().size() < 2){
                log.error("Impossible de créer la question. Le nombre de réponses doit être de 2 au MINIMUM.");
                return null; //return qu'il faut au minimum 2 réponses. //TODO : return erreur
            } else {
                log.info("Sauvegarde de la question dans la base de données.");
                questionRepository.saveAndFlush(question);
            }
            for(Reponse reponse : question.getReponses()){
                log.info("Cette réponse n'existe pas. Création de la réponse.");
                reponseService.createReponse(question.getId(), reponse);
            }
        } else {
            log.error("Une question doit OBLIGATOIREMENT avoir au minimum 2 réponses. Création impossible.");
            return null; //return une erreur
        }

        return question;
    }


    @Override
    public Question updateQuestion(Question question) {
        log.info("Modification de la question {}.", question.getId());
        Optional<Question> questionToUpdate = questionRepository.findById(question.getId());
        if(questionToUpdate.isEmpty()){
            log.error("La question {} n'existe pas. Modification impossible.", question.getId());
            return null; //return une erreur
        }

        if(question.getTexte() == null){
            question.setTexte(questionToUpdate.get().getTexte());
        }

        if(question.getReponses() != null){
            log.info("Vérification du nombre de réponses de chaque question (2 minimum).");
            if(question.getReponses().size() < 2){
                log.error("Impossible de créer la question. Le nombre de réponses doit être de 2 au MINIMUM.");
                return null; //return qu'il faut au minimum 2 réponses. //TODO : return erreur
            }
            for(Reponse reponse : question.getReponses()){
                Optional<Reponse> currentReponse = reponseRepository.findById(reponse.getId());
                if(currentReponse.isEmpty()){
                    log.info("Cette réponse n'existe pas. Création de la réponse.");
                    reponseService.createReponse(questionToUpdate.get().getId(), reponse);
                } else if(currentReponse.isPresent()){
                    log.info("La réponse {} existe déja. Mise à jour.", reponse.getId());
                    reponseService.updateReponse(reponse);
                }
            }
        } else {
            log.error("Une question doit OBLIGATOIREMENT avoir au minimum 2 réponses. Création impossible.");
            return null;  //return erreur
        }

        if(question.getQcm() != null){
            Optional<Qcm> qcm = qcmRepository.findById(question.getQcm().getId());
            if(qcm.isEmpty()){
                log.error("Le qcm {} n'existe pas. Assignation impossible.", question.getQcm().getId());
                return null; //return une erreur
            }
            question.setQcm(qcm.get());
        }

        log.info("La question {} a été modifiée avec succès.", question.getId());
        return questionRepository.saveAndFlush(question);
    }

    @Override
    public String deleteQuestion(UUID questionId) {
        Optional<Question> questionToDelete = questionRepository.findById(questionId);
        if(questionToDelete.isEmpty()){
            log.error("La question {} n'existe pas. Suppression impossible.", questionId);
            return null; //question déja supprimée ou autre
        }
        for(Reponse reponse : questionToDelete.get().getReponses()){
            Optional<Reponse> repToDelete = reponseRepository.findById(reponse.getId());
            if(repToDelete.isEmpty()){
                log.error("La réponse {} n'existe pas. Suppression impossible.", reponse.getId());
                return null; //reponse déja supprimée ou autre
            }
            reponseService.deleteReponse(repToDelete.get().getId());
        }

        questionRepository.deleteById(questionId);
        log.info("La question {} a été supprimée.", questionId);
        return "La question avec l'id " + questionId + " a été supprimée ainsi que les réponses liées.";
    }
}
