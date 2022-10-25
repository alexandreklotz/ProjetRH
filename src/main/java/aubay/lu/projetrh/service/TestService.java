package aubay.lu.projetrh.service;

import aubay.lu.projetrh.model.Qcm;
import aubay.lu.projetrh.model.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestService {

    public List<Test> getAllTest();

    public Optional<Test> getTestById(UUID testId);

    public List<Test> getTestByCandidat(UUID userId);

    public Test createTest(Qcm qcm, String title, UUID utilisateurId);

    public Test updateTest(Test test);

    public String deleteTest(UUID testId);

    //TODO: Implémenter une méthode "submit" pour que le candidat puisse soumettre son test une fois fini

}
