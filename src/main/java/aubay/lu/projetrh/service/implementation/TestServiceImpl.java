package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.Test;
import aubay.lu.projetrh.repository.QcmRepository;
import aubay.lu.projetrh.repository.TestRepository;
import aubay.lu.projetrh.repository.UtilisateurRepository;
import aubay.lu.projetrh.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TestServiceImpl implements TestService {

    private TestRepository testRepository;
    private UtilisateurRepository utilisateurRepository;
    private QcmRepository qcmRepository;

    @Autowired
    TestServiceImpl(TestRepository testRepository, UtilisateurRepository utilisateurRepository, QcmRepository qcmRepository){
        this.testRepository = testRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.qcmRepository = qcmRepository;
    }

    @Override
    public List<Test> getAllTest() {
        return testRepository.findAll();
    }

    @Override
    public Optional<Test> getTestById(UUID testId) {
        return testRepository.findById(testId);
    }

    @Override
    public List<Test> getTestByCandidat(UUID userId) {
        return testRepository.findTestByCandidat(userId);
    }

    @Override
    public Test createTest(Test test) {
        return null; //TODO
    }

    @Override
    public Test updateTest(Test test) {
        return null; //TODO
    }

    @Override
    public String deleteTest(UUID testId) {
        testRepository.deleteById(testId);
        return "Le test " + testId + " a été supprimé.";
    }
}
