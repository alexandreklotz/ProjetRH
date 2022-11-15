package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.Qcm;
import aubay.lu.projetrh.model.Question;
import aubay.lu.projetrh.repository.QcmRepository;
import aubay.lu.projetrh.repository.QuestionRepository;
import aubay.lu.projetrh.service.QcmService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QcmServiceImpl implements QcmService {

    private QcmRepository qcmRepository;
    private QuestionRepository questionRepository;
    private Logger log;

    @Autowired
    QcmServiceImpl(QcmRepository qcmRepository, QuestionRepository questionRepository, Logger log){
        this.qcmRepository = qcmRepository;
        this.questionRepository = questionRepository;
        this.log = log;
    }

    @Override
    public List<Qcm> getAllQcm() {
        return qcmRepository.findAll();
    }

    @Override
    public Optional<Qcm> getQcmById(UUID qcmId) {
        return qcmRepository.findById(qcmId);
    }

    @Override
    public List<Qcm> getQcmByTitle(String title) {
        return qcmRepository.findQcmByName(title);
    }

    @Override
    public Qcm createQcm(Qcm qcm) {
        log.info("Début de la création du qcm {}", qcm.getId());
        if(qcm.getQuestions() != null){
            log.info("Des questions sont renseignées dans le formulaire, création des questions inexistantes.");
            for(Question question : qcm.getQuestions()){
                Optional<Question> qcmQuestion = questionRepository.findById(question.getId());
                if(qcmQuestion.isEmpty()){
                    log.error("La question {} n'existe pas.", question.getId());
                    return null; //return erreur
                }
                log.info("Question {} assignée au qcm {}", question.getId(), qcm.getId());
                qcmQuestion.get().setQcm(qcm);
                //Cette manière de vérifier si les questions assignées au qcm existent peut être lente.
            }
        } else if(qcm.getQuestions() == null){
            log.info("Aucune question renseignée dans le formulaire du qcm {}.", qcm.getId());
            qcm.setQuestions(null);
        }

        log.info("Le qcm {} a été créé avec succès.", qcm.getId());
        return qcmRepository.saveAndFlush(qcm);
    }

    @Override
    public Qcm updateQcm(Qcm qcm) {
        log.info("Requête de modification du qcm {}", qcm.getId());
        Optional<Qcm> qcmToUpdate = qcmRepository.findById(qcm.getId());
        if(qcmToUpdate.isEmpty()){
            log.error("Le qcm {} n'existe pas. Impossible de le modifier.", qcm.getId());
            return null; //Return erreur
        }

        if(qcm.getTitre() == null){
            qcm.setTitre(qcmToUpdate.get().getTitre());
        }

        if(qcm.getQuestions() == null){
            qcm.setQuestions(null); //si on ne renvoie pas de liste de questions, alors on part du principe qu'elles ont été retirées
        } else {
            log.info("Vérification et assignation des questions spécifiées dans le formulaire pour le qcm {}.", qcm.getId());
            for(Question question : qcm.getQuestions()){
                Optional<Question> qcmQuestion = questionRepository.findById(question.getId());
                if(qcmQuestion.isEmpty()){
                    log.error("La question {} n'existe pas. Impossible de l'assigner au qcm {}.", question.getId(), qcm.getId());
                    return null; //return erreur
                }
                qcmQuestion.get().setQcm(qcm);
                //Cette manière de vérifier si les questions assignées au qcm existent peut être lente.
            }
        }

        log.info("Le qcm {} a été modifié avec succès.", qcm.getId());
        return qcmRepository.saveAndFlush(qcm);
    }

    @Override
    public String deleteQcm(UUID qcmId) {
        qcmRepository.deleteById(qcmId);
        log.info("Le qcm {} a été supprimé.", qcmId);
        return "Le QCM avec l'id " + qcmId + " a été supprimé";
    }
}
