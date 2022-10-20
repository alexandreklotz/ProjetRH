package aubay.lu.projetrh.service;

import aubay.lu.projetrh.model.Question;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionService {

    public List<Question> getAllQuestions();

    public Optional<Question> getQuestionById(UUID questionId);

    public Optional<Question> getQuestionByQcm(UUID qcmId);

    public Question createQuestion();

    public Question updateQuestion();

    public String deleteQuestion(UUID questionId);
}
