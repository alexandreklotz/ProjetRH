package aubay.lu.projetrh.service;

import aubay.lu.projetrh.model.Question;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface QuestionService {

    public List<Question> getAllQuestions();

    public Optional<Question> getQuestionById(UUID questionId);

    public Set<Question> getQuestionByQcmId(UUID qcmId);

    public Question createQuestion(Question question);

    public Question updateQuestion(Question question);

    public String deleteQuestion(UUID questionId);
}
