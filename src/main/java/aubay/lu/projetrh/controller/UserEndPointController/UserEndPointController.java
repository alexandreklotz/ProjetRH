package aubay.lu.projetrh.controller.UserEndPointController;

import aubay.lu.projetrh.model.Test;
import aubay.lu.projetrh.service.TestService;
import aubay.lu.projetrh.service.UtilisateurService;
import aubay.lu.projetrh.view.CustomJsonView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class UserEndPointController {

    private TestService testService;
    private UtilisateurService utilisateurService;

    @Autowired
    UserEndPointController(TestService testService, UtilisateurService utilisateurService){
        this.testService = testService;
        this.utilisateurService = utilisateurService;
    }

    @JsonView(CustomJsonView.TestView.class)
    @PutMapping("user/test/submit")
    public Test submitTest(@RequestBody Test test){
        return testService.submitTest(test);
    }

    @JsonView(CustomJsonView.TestView.class)
    @GetMapping("user/test/{testId}")
    public Test retrieveSingleCandidatTest(@PathVariable UUID testId, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String userLogin = principal.getName();
        return testService.retrieveSingleCandidatTest(userLogin, testId);
    }

    @JsonView(CustomJsonView.TestView.class)
    @GetMapping("user/test/ownedtests")
    public List<Test> retrieveAllCandidatTest(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String userLogin = principal.getName();
        return testService.retriveAllCandidatTest(userLogin);
    }

    @JsonView(CustomJsonView.TestView.class)
    @PutMapping("user/test/selfassign/{testId}")
    public Test utilisateurTestSelfAssign(@PathVariable UUID testId, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String userLogin = principal.getName();
        return testService.utilisateurTestSelfAssign(userLogin, testId);
    }

}
