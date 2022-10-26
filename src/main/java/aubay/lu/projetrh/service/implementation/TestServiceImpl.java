package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.Qcm;
import aubay.lu.projetrh.model.Test;
import aubay.lu.projetrh.model.Utilisateur;
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
    public Test createTest(Qcm qcm, UUID utilisateurId) {

        Optional<Qcm> qcmTest = qcmRepository.findById(qcm.getId());
        if(qcmTest.isEmpty()){
            return null; //return une erreur
        }

        Test test = new Test();
        //test.setQuestions(qcmTest.get().getQuestions()); //TODO : Recréer les questions et les supprimer lors du submit ? (même si mauvaise idée, trop de charge). Y réfléchir
        test.setTitre(qcmTest.get().getTitre());

        Optional<Utilisateur> testUser = utilisateurRepository.findById(utilisateurId);
        if(testUser.isEmpty()){
            return null; //return une erreur
        } else if (test.getUtilisateur() == null){
            test.setUtilisateur(null);
        }
        test.setUtilisateur(testUser.get());

        return testRepository.saveAndFlush(test);
        //TODO : tester cette méthode et mettre en place le calcul du score. Implémenter dates ?
    }


    @Override
    public Test updateTest(Test test) {
        return null; //TODO
    }


    @Override
    public Test submitTest(Test test) {
        return null;
    }


    @Override
    public String deleteTest(UUID testId) {
        testRepository.deleteById(testId);
        return "Le test " + testId + " a été supprimé.";
    }
}
