package aubay.lu.projetrh.controller;

import aubay.lu.projetrh.model.Test;
import aubay.lu.projetrh.service.TestService;
import aubay.lu.projetrh.view.CustomJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
public class TestController {

    private TestService testService;

    @Autowired
    TestController(TestService testService){
        this.testService = testService;
    }


    @JsonView(CustomJsonView.TestView.class)
    @GetMapping("moderateur/test/all")
    public List<Test> getAllTest(){
        return testService.getAllTest();
    }

    @JsonView(CustomJsonView.TestView.class)
    @GetMapping("moderateur/test/id/{testId}")
    public Optional<Test> getTestById(@PathVariable UUID testId){
        return testService.getTestById(testId);
    }

    @JsonView(CustomJsonView.TestView.class)
    @GetMapping("moderateur/test/candidat/{candidatId}")
    public List<Test> getTestByCandidat(@PathVariable UUID candidatId){
        return testService.getTestByCandidat(candidatId);
    }

    @JsonView(CustomJsonView.TestView.class)
    @PostMapping("moderateur/test/create")
    public Test createTest(@RequestBody Test test){
        return testService.createTest(test);
    }

    @JsonView(CustomJsonView.TestView.class)
    @PutMapping("moderateur/test/update")
    public Test updateTest(@RequestBody Test test){
        return testService.updateTest(test);
    }


    @JsonView(CustomJsonView.TestView.class)
    @DeleteMapping("moderateur/test/delete/{testId}")
    public void deleteTest(@PathVariable UUID testId){
        testService.deleteTest(testId);
    }

}