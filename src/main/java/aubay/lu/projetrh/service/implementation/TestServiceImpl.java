package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.*;
import aubay.lu.projetrh.repository.QcmRepository;
import aubay.lu.projetrh.repository.ReponseRepository;
import aubay.lu.projetrh.repository.TestRepository;
import aubay.lu.projetrh.repository.UtilisateurRepository;
import aubay.lu.projetrh.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

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
            log.error("Le qcm avec l'id {} n'existe pas. Impossible de créer le test.", test.getQcm().getId());
            return null;
        }

        if(test.getTitre() == null){
            test.setTitre(qcmTest.get().getTitre());
        }

        if(test.getUtilisateur() != null){
            Optional<Utilisateur> testUser = utilisateurRepository.findById(test.getUtilisateur().getId());
            if(testUser.isEmpty()){
                log.error("L'utilisateur avec l'id {} n'existe pas. Impossible de lui assigner ce test.", test.getUtilisateur().getId());
                return null;
            } else if (test.getUtilisateur() == null){
                test.setUtilisateur(null);
            }
            test.setUtilisateur(testUser.get());
        }

        test.setAlreadySubmitted(false);

        log.info("Le test {} a été créé.", test.getTitre());
        return testRepository.saveAndFlush(test);
        //TODO : Implémenter dates ?
    }

    @Override
    public Test updateTest(Test test) {

        Optional<Test> updatedTest = testRepository.findById(test.getId());
        if(updatedTest.isEmpty()){
            log.error("Le test {} n'existe pas. Impossible d'effectuer la modification.", test.getId());
            return null;
        }

        if(test.getTitre() == null){
            return null;
        }

        if(test.getUtilisateur() != null){
            Optional<Utilisateur> testUser = utilisateurRepository.findById(test.getUtilisateur().getId());
            if(testUser.isEmpty()){
                log.error("L'utilisateur {} n'existe pas. Impossible de lui assigner le test.", test.getUtilisateur().getId());
                return null;
            } else if (test.getUtilisateur() == null){
                test.setUtilisateur(null);
            }
            test.setUtilisateur(testUser.get());
        }

        log.info("Le test {} avec l'id {} a été mis à jour.", test.getTitre(), test.getId());
        return testRepository.saveAndFlush(test);
    }

    @Override
    public Test submitTest(Test test) {
        //TODO : mettre en place un submit unique, un test ne peut être submit qu'une fois.
        Double testScore = 0.0;

        log.info("Soumission d'un test par un utilisateur");

        Optional<Test> currentTest = testRepository.findById(test.getId());
        if(currentTest.isEmpty()){
            log.error("Impossible de soumettre le test {}, il n'existe pas.", test.getId());
            return null;
        }

        if(currentTest.get().isAlreadySubmitted() == true){
            log.info("Le test {} a déja été soumis.", test.getId());
            return null; //le test a déja été submit
        }

        Optional<Qcm> qcmTest = qcmRepository.findById(currentTest.get().getQcm().getId());
        if(qcmTest.isEmpty()){
            log.error("Le qcm {} n'existe plus, impossible de soumettre le test {}.", currentTest.get().getQcm().getId(), test.getId());
            return null;
        }

        Optional<Utilisateur> utilisateurTest = utilisateurRepository.findById(currentTest.get().getUtilisateur().getId());
        if(utilisateurTest.isEmpty()){
            log.error("L'utilisateur {} qui a soumis le test {} n'existe pas.", currentTest.get().getUtilisateur().getId(), test.getId());
            return null;
        }

        for(Reponse reponse : test.getReponses()){
            Optional<Reponse> reponseTest = reponseRepository.findById(reponse.getId());
            if(reponseTest.isEmpty()){
                log.error("La réponse avec l'id {} n'existe pas.", reponse.getId());
                return null;
            }
            boolean isGoodAnswer = reponseTest.get().isCorrectAnswer();
            if(isGoodAnswer){
                testScore += reponseTest.get().getPoints();
            }
            Set<Test> repTest = reponseTest.get().getTests();
            log.info("Ajout des questions soumises par le candidat au test.");
            repTest.add(test);
            reponseRepository.saveAndFlush(reponseTest.get());
        }

        test.setScore(testScore);
        test.setTitre(currentTest.get().getTitre());
        test.setAlreadySubmitted(true);
        log.info("Le test {} ne pourra désormais plus être soumis", test.getId());
        testRepository.saveAndFlush(test);

        int numberOfTests = utilisateurTest.get().getTests().size();
        Double newUserGlobalScore = (testScore + utilisateurTest.get().getGlobalScore()) / numberOfTests;
        utilisateurTest.get().setGlobalScore(newUserGlobalScore);
        utilisateurRepository.saveAndFlush(utilisateurTest.get());

        log.info("Le test {} a été soumis avec succès.", test.getId());
        return test;
    }

    @Override
    public Test retrieveSingleCandidatTest(String userLogin, UUID testId) {

        Optional<Utilisateur> loggedUser = utilisateurRepository.findUserWithLogin(userLogin);
        if(loggedUser.isEmpty()){
            log.error("Impossible de récupérer le test {}. L'utilisateur {} n'existe pas.", testId ,userLogin);
            return null;
        }

        Optional<Test> retrievedTest = testRepository.findById(testId);
        if(retrievedTest.isEmpty()){
            log.error("Impossible de récupérer le test {}. Il n'existe pas.", testId);
            return null;
        }

        Utilisateur testAssignedUser = retrievedTest.get().getUtilisateur();

        if(!testAssignedUser.equals(loggedUser)){
            log.error("Le test {} n'est pas assigné à l'utilisateur ({}) qui tente d'y accéder.", testId, userLogin);
            return null;
        }

        log.info("Test {} récupéré avec succès par l'utilisateur {}.", testId, userLogin);
        return retrievedTest.get();

    }

    @Override
    public List<Test> retriveAllCandidatTest(String userLogin) {

        Optional<Utilisateur> loggedUser = utilisateurRepository.findUserWithLogin(userLogin);
        if(loggedUser.isEmpty()){
            log.error("Impossible de récupérer les tests de l'utilisateur {}. Il n'existe pas.", userLogin);
            return null;
        }

        log.info("Tests de l'utilisateur {} récupérés avec succès.", userLogin);
        return testRepository.findTestByCandidat(loggedUser.get().getId());
    }

    @Override
    public Test utilisateurTestSelfAssign(String userLogin, UUID testId) {
        //TODO : tester si ok
        Optional<Utilisateur> loggedUser = utilisateurRepository.findUserWithLogin(userLogin);
        if(loggedUser.isEmpty()){
            log.error("Impossible d'assigner le test {} à l'utilisateur {}. L'utilisateur n'existe pas.", testId, userLogin);
            return null;
        }

        Optional<Test> currentTest = testRepository.findById(testId);
        if(currentTest.isEmpty()){
            log.error("Impossible d'assigner le test {} à l'utilisateur {}. Ce test n'existe pas.", testId, userLogin);
            return null;
        }

        if(currentTest.get().getUtilisateur() != null){
            log.info("Impossible d'assigner le test {} à l'utilisateur {}. Il est déja assigné.", testId ,userLogin);
            return null;
        }

        currentTest.get().setUtilisateur(loggedUser.get());
        log.info("Le test {} a été assigné à l'utilisateur {} avec succès.", testId, userLogin);
        return testRepository.saveAndFlush(currentTest.get());
    }

    @Override
    public String deleteTest(UUID testId) {
        testRepository.deleteById(testId);
        log.info("Le test avec l'id {} a été supprimé.", testId);
        return "Le test avec l'id " + testId + " a été supprimé";
    }
}
