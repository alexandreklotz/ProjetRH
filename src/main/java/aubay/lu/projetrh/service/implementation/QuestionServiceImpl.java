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
    public Optional<Question> getQuestionByQcm(UUID qcmId) {
        return null; //Créer une query dans le repository et créer une deuxième méthode identique. Les séparer en getQuestionByQcmId et getQuestionByQcmName
    }

    @Override
    public Question createQuestion() {
        return null;
    }

    @Override
    public Question updateQuestion() {
        return null;
    }

    @Override
    public String deleteQuestion(UUID questionId) {
        return null;
    }
}
