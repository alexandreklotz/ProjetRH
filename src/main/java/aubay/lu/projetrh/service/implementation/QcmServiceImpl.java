package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.Qcm;
import aubay.lu.projetrh.model.Question;
import aubay.lu.projetrh.repository.QcmRepository;
import aubay.lu.projetrh.repository.QuestionRepository;
import aubay.lu.projetrh.service.QcmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QcmServiceImpl implements QcmService {

    private QcmRepository qcmRepository;
    private QuestionRepository questionRepository;

    @Autowired
    QcmServiceImpl(QcmRepository qcmRepository, QuestionRepository questionRepository){
        this.qcmRepository = qcmRepository;
        this.questionRepository = questionRepository;
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
        if(qcm.getQuestions() != null){
            for(Question question : qcm.getQuestions()){
                Optional<Question> qcmQuestion = questionRepository.findById(question.getId());
                if(qcmQuestion.isEmpty()){
                    return null; //return erreur
                }
                qcmQuestion.get().setQcm(qcm);
                //Cette manière de vérifier si les questions assignées au qcm existent peut être lente.
            }
        }
        return qcmRepository.saveAndFlush(qcm);
    }

    @Override
    public Qcm updateQcm(Qcm qcm) {
        Optional<Qcm> qcmToUpdate = qcmRepository.findById(qcm.getId());
        if(qcmToUpdate.isEmpty()){
            return null; //Return erreur
        }

        if(qcm.getTitre() == null){
            qcm.setTitre(qcmToUpdate.get().getTitre());
        }

        if(qcm.getQuestions() == null){
            qcm.setQuestions(qcmToUpdate.get().getQuestions());
        } else {
            for(Question question : qcm.getQuestions()){
                Optional<Question> qcmQuestion = questionRepository.findById(question.getId());
                if(qcmQuestion.isEmpty()){
                    return null; //return erreur
                }
                qcmQuestion.get().setQcm(qcm);
                //Cette manière de vérifier si les questions assignées au qcm existent peut être lente.
            }
        }

        return qcmRepository.saveAndFlush(qcm);
    }

    @Override
    public String deleteQcm(UUID qcmId) {
        qcmRepository.deleteById(qcmId);
        return "Le QCM avec l'id " + qcmId + " a été supprimé";
    }
}
