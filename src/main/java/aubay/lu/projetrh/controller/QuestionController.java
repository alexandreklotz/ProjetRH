package aubay.lu.projetrh.controller;

import aubay.lu.projetrh.model.Question;
import aubay.lu.projetrh.service.QuestionService;
import aubay.lu.projetrh.view.CustomJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
public class QuestionController {

    private QuestionService questionService;

    @Autowired
    QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    @JsonView(CustomJsonView.QuestionView.class)
    @GetMapping("admin/question/all")
    public List<Question> getAllQuestion(){
        return questionService.getAllQuestions();
    }

    @JsonView(CustomJsonView.QuestionView.class)
    @GetMapping("admin/question/id/{questionId}")
    public Optional<Question> getQuestionById(@PathVariable UUID questionId){
        return questionService.getQuestionById(questionId);
    }

    @JsonView(CustomJsonView.QuestionView.class)
    @GetMapping("admin/question/qcm/{qcmId}")
    public List<Question> getQuestionByQcmId(@PathVariable UUID qcmId){
        return questionService.getQuestionByQcmId(qcmId);
    }

    @JsonView(CustomJsonView.QuestionView.class)
    @PostMapping("admin/question/create")
    public Question createQuestion(@RequestBody Question question){
        return questionService.createQuestion(question);
    }

    @JsonView(CustomJsonView.QuestionView.class)
    @PutMapping("admin/question/update")
    public Question updateQuestion(@RequestBody Question question){
        return questionService.updateQuestion(question);
    }

    @JsonView(CustomJsonView.QuestionView.class)
    @DeleteMapping("admin/question/delete/{questionId}")
    public String deleteQuestion (@PathVariable UUID questionId){
        return questionService.deleteQuestion(questionId);
    }
}
