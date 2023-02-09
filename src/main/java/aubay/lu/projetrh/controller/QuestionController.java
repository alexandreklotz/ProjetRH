package aubay.lu.projetrh.controller;

import aubay.lu.projetrh.model.Question;
import aubay.lu.projetrh.repository.QuestionRepository;
import aubay.lu.projetrh.service.QuestionService;
import aubay.lu.projetrh.view.CustomJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@CrossOrigin
public class QuestionController {

    private QuestionService questionService;
    private QuestionRepository questionRepository;

    @Autowired
    QuestionController(QuestionService questionService, QuestionRepository questionRepository){
        this.questionService = questionService;
        this.questionRepository = questionRepository;
    }

    @JsonView(CustomJsonView.QuestionView.class)
    @GetMapping("moderateur/question/all")
    public List<Question> getAllQuestion(){
        return questionService.getAllQuestions();
    }

    @JsonView(CustomJsonView.QuestionView.class)
    @GetMapping("moderateur/question/id/{questionId}")
    public Optional<Question> getQuestionById(@PathVariable UUID questionId){
        return questionService.getQuestionById(questionId);
    }

    @JsonView(CustomJsonView.QuestionView.class)
    @GetMapping("moderateur/question/qcm/{qcmId}")
    public List<Question> getQuestionByQcmId(@PathVariable UUID qcmId){
        return questionService.getQuestionByQcmId(qcmId);
    }

    @JsonView(CustomJsonView.QuestionView.class)
    @GetMapping("moderateur/question/{testId}/all")
    public Set<Question> getQuestionByTestId(@PathVariable UUID testId){
        return questionService.getQuestionByTestId(testId);
    }

    @JsonView(CustomJsonView.QuestionView.class)
    @GetMapping("moderateur/question/unassigned")
    public List<Question> getUnassignedQuestions(){
        return questionRepository.getUnassignedQuestion();
    }

    @JsonView(CustomJsonView.QuestionView.class)
    @GetMapping("moderateur/question/qcmquestions")
    public List<Question> getQcmQuestions(){
        return questionRepository.getNoTestQuestions();
    }

    @JsonView(CustomJsonView.QuestionView.class)
    @PostMapping("moderateur/question/create")
    public Question createQuestion(@RequestBody Question question){
        return questionService.createQuestion(question);
    }

    @JsonView(CustomJsonView.QuestionView.class)
    @PutMapping("moderateur/question/update")
    public Question updateQuestion(@RequestBody Question question){
        return questionService.updateQuestion(question);
    }

    @JsonView(CustomJsonView.QuestionView.class)
    @DeleteMapping("moderateur/question/delete/{questionId}")
    public String deleteQuestion (@PathVariable UUID questionId){
        return questionService.deleteQuestion(questionId);
    }
}
