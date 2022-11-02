package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.*;
import aubay.lu.projetrh.repository.QcmRepository;
import aubay.lu.projetrh.repository.ReponseRepository;
import aubay.lu.projetrh.repository.TestRepository;
import aubay.lu.projetrh.repository.UtilisateurRepository;
import aubay.lu.projetrh.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TestServiceImpl implements TestService {

    private TestRepository testRepository;
    private UtilisateurRepository utilisateurRepository;
    private QcmRepository qcmRepository;
    private ReponseRepository reponseRepository;

    @Autowired
    TestServiceImpl(TestRepository testRepository, UtilisateurRepository utilisateurRepository, QcmRepository qcmRepository, ReponseRepository reponseRepository){
        this.testRepository = testRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.qcmRepository = qcmRepository;
        this.reponseRepository = reponseRepository;
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

        Optional<Qcm> qcmTest = qcmRepository.findById(test.getQcm().getId());
        if(qcmTest.isEmpty()){
            return null;
        }

        if(test.getTitre() == null){
            test.setTitre(qcmTest.get().getTitre());
        }

        if(test.getUtilisateur() != null){
            Optional<Utilisateur> testUser = utilisateurRepository.findById(test.getUtilisateur().getId());
            if(testUser.isEmpty()){
                return null;
            } else if (test.getUtilisateur() == null){
                test.setUtilisateur(null);
            }
            test.setUtilisateur(testUser.get());
        }

        return testRepository.saveAndFlush(test);
        //TODO : Implémenter dates ?
    }

    @Override
    public Test updateTest(Test test) {

        Optional<Test> updatedTest = testRepository.findById(test.getId());
        if(updatedTest.isEmpty()){
            return null;
        }

        if(test.getTitre() == null){
            return null;
        }

        if(test.getUtilisateur() != null){
            Optional<Utilisateur> testUser = utilisateurRepository.findById(test.getUtilisateur().getId());
            if(testUser.isEmpty()){
                return null;
            } else if (test.getUtilisateur() == null){
                test.setUtilisateur(null);
            }
            test.setUtilisateur(testUser.get());
        }

        return testRepository.saveAndFlush(test);
    }

    @Override
    public Test submitTest(Test test) {

        Double testScore = 0.0;

        Optional<Test> currentTest = testRepository.findById(test.getId());
        if(currentTest.isEmpty()){
            return null;
        }

        Optional<Qcm> qcmTest = qcmRepository.findById(currentTest.get().getQcm().getId());
        if(qcmTest.isEmpty()){
            return null;
        }

        Optional<Utilisateur> utilisateurTest = utilisateurRepository.findById(currentTest.get().getUtilisateur().getId());
        if(utilisateurTest.isEmpty()){
            return null;
        }

        for(Reponse reponse : test.getReponses()){
            Optional<Reponse> reponseTest = reponseRepository.findById(reponse.getId());
            if(reponseTest.isEmpty()){
                return null;
            }
            boolean isGoodAnswer = reponseTest.get().isCorrectAnswer();
            if(isGoodAnswer){
                testScore += reponseTest.get().getPoints();
            }
            reponseRepository.saveAndFlush(reponse);
        }

        test.setScore(testScore);
        test.setTitre(currentTest.get().getTitre());
        testRepository.saveAndFlush(test);

        int numberOfTests = utilisateurTest.get().getTests().size();
        Double newUserGlobalScore = (testScore + utilisateurTest.get().getGlobalScore()) / numberOfTests;
        utilisateurTest.get().setGlobalScore(newUserGlobalScore);
        utilisateurRepository.saveAndFlush(utilisateurTest.get());

        return test;
    }

    @Override
    public Test retrieveSingleCandidatTest(String userLogin, UUID testId) {

        Optional<Utilisateur> loggedUser = utilisateurRepository.findUserWithLogin(userLogin);
        if(loggedUser.isEmpty()){
            return null;
        }

        Optional<Test> retrievedTest = testRepository.findById(testId);
        if(retrievedTest.isEmpty()){
            return null;
        }

        Utilisateur testAssignedUser = retrievedTest.get().getUtilisateur();

        if(!testAssignedUser.equals(loggedUser)){
            return null;
        }

        return retrievedTest.get();

    }

    @Override
    public List<Test> retriveAllCandidatTest(String userLogin) {

        Optional<Utilisateur> loggedUser = utilisateurRepository.findUserWithLogin(userLogin);
        if(loggedUser.isEmpty()){
            return null;
        }

        return testRepository.findTestByCandidat(loggedUser.get().getId());
    }

    @Override
    public Test utilisateurTestSelfAssign(String userLogin, UUID testId) {
        //TODO : tester si ok
        Optional<Utilisateur> loggedUser = utilisateurRepository.findUserWithLogin(userLogin);
        if(loggedUser.isEmpty()){
            return null;
        }

        Optional<Test> currentTest = testRepository.findById(testId);
        if(currentTest.isEmpty()){
            return null;
        }

        if(currentTest.get().getUtilisateur() != null){
            return null;
        }

        currentTest.get().setUtilisateur(loggedUser.get());
        return testRepository.saveAndFlush(currentTest.get());
    }

    @Override
    public String deleteTest(UUID testId) {
        testRepository.deleteById(testId);
        return "Le test avec l'id " + testId + " a été supprimé";
    }
}
