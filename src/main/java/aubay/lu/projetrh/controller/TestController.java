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
    @GetMapping("admin/test/all")
    public List<Test> getAllTest(){
        return testService.getAllTest();
    }

    @JsonView(CustomJsonView.TestView.class)
    @GetMapping("admin/test/{testId}")
    public Optional<Test> getTestById(@PathVariable UUID testId){
        return testService.getTestById(testId);
    }

    @JsonView(CustomJsonView.TestView.class)
    @GetMapping("admin/test/candidat/{candidatId}")
    public List<Test> getTestByCandidat(@PathVariable UUID candidatId){
        return testService.getTestByCandidat(candidatId);
    }

    @JsonView(CustomJsonView.TestView.class)
    @PostMapping("admin/test/create")
    public Test createTest(@RequestBody Test test){
        return testService.createTest(test);
    }

    @JsonView(CustomJsonView.TestView.class)
    @PutMapping("admin/test/update")
    public Test updateTest(@RequestBody Test test){
        return testService.updateTest(test);
    }


    @JsonView(CustomJsonView.TestView.class)
    @DeleteMapping("admin/test/delete/{testId}")
    public void deleteTest(@PathVariable UUID testId){
        testService.deleteTest(testId);
    }

}