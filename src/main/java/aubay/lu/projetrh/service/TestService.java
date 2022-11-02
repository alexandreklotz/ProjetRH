package aubay.lu.projetrh.service;

import aubay.lu.projetrh.model.Qcm;
import aubay.lu.projetrh.model.Test;
import aubay.lu.projetrh.model.Utilisateur;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestService {

    public List<Test> getAllTest();

    public Optional<Test> getTestById(UUID testId);

    public List<Test> getTestByCandidat(UUID userId);

    public Test createTest(Test test);

    public Test updateTest(Test test);

    public String deleteTest(UUID testId);

    public Test submitTest(Test test);

    public Test retrieveSingleCandidatTest(String userLogin, UUID testId);

    public List<Test> retriveAllCandidatTest(String userLogin);

    public Test utilisateurTestSelfAssign(String userLogin, UUID testId);

}
