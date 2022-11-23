package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.*;
import aubay.lu.projetrh.repository.*;
import aubay.lu.projetrh.service.MapFilterService;
import aubay.lu.projetrh.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.stream.Stream;

@Service
public class TestServiceImpl implements TestService {

    private TestRepository testRepository;
    private UtilisateurRepository utilisateurRepository;
    private QcmRepository qcmRepository;
    private ReponseRepository reponseRepository;
    private QuestionRepository questionRepository;
    private MapFilterService mapFilterService;
    private static Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    TestServiceImpl(TestRepository testRepository,
                    UtilisateurRepository utilisateurRepository,
                    QcmRepository qcmRepository,
                    ReponseRepository reponseRepository,
                    QuestionRepository questionRepository,
                    MapFilterService mapFilterService) {
        this.testRepository = testRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.qcmRepository = qcmRepository;
        this.reponseRepository = reponseRepository;
        this.questionRepository = questionRepository;
        this.mapFilterService = mapFilterService;
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
        if (qcmTest.isEmpty()) {
            log.error("Le qcm avec l'id {} n'existe pas. Impossible de créer le test.", test.getQcm().getId());
            return null;
        }

        if (test.getTitre() == null) {
            test.setTitre(qcmTest.get().getTitre());
        }

        if (test.getUtilisateur() != null) {
            Optional<Utilisateur> testUser = utilisateurRepository.findById(test.getUtilisateur().getId());
            if (testUser.isEmpty()) {
                log.error("L'utilisateur avec l'id {} n'existe pas. Impossible de lui assigner ce test.", test.getUtilisateur().getId());
                return null;
            } else if (test.getUtilisateur() == null) {
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
        if (updatedTest.isEmpty()) {
            log.error("Le test {} n'existe pas. Impossible d'effectuer la modification.", test.getId());
            return null;
        }

        if (test.getTitre() == null) {
            return null;
        }

        if (test.getUtilisateur() != null) {
            Optional<Utilisateur> testUser = utilisateurRepository.findById(test.getUtilisateur().getId());
            if (testUser.isEmpty()) {
                log.error("L'utilisateur {} n'existe pas. Impossible de lui assigner le test.", test.getUtilisateur().getId());
                return null;
            } else if (test.getUtilisateur() == null) {
                test.setUtilisateur(null);
            }
            test.setUtilisateur(testUser.get());
        }

        log.info("Le test {} avec l'id {} a été mis à jour.", test.getTitre(), test.getId());
        return testRepository.saveAndFlush(test);
    }

    @Override
    public Test submitTest(Test test) {

        log.info("Soumission d'un test par l'utilisateur {}.", test.getUtilisateur().getFirstName() + " " + test.getUtilisateur().getLastName());

        Optional<Test> currentTest = testRepository.findById(test.getId());
        if (currentTest.isEmpty()) {
            log.error("Impossible de soumettre le test {}, il n'existe pas.", test.getId());
            return null;
        }

        if (currentTest.get().isAlreadySubmitted() == true) {
            log.info("Le test {} a déja été soumis.", test.getId());
            return null; //le test a déja été submit
        }

        Optional<Qcm> qcmTest = qcmRepository.findById(currentTest.get().getQcm().getId());
        if (qcmTest.isEmpty()) {
            log.error("Le qcm {} n'existe plus, impossible de soumettre le test {}.", currentTest.get().getQcm().getId(), test.getId());
            return null;
        }

        Optional<Utilisateur> utilisateurTest = utilisateurRepository.findById(currentTest.get().getUtilisateur().getId());
        if (utilisateurTest.isEmpty()) {
            log.error("L'utilisateur {} qui a soumis le test {} n'existe pas.", currentTest.get().getUtilisateur().getId(), test.getId());
            return null;
        }


        Map<Reponse, Question> qcmQuestionsReponsesMap = new HashMap<>();

        log.info("Début du check des combinaisons question/réponses.");
        log.info("Vérification et récupération des questions du qcm.");
        for (Question question : qcmTest.get().getQuestions()) {
            Optional<Question> qcmQuestion = questionRepository.findById(question.getId());
            if (qcmQuestion.isEmpty()) {
                log.error("La question {} n'existe pas.", question.getId());
            }
            log.info("Récupération des réponses de la question {} et vérification des réponses.", question.getId());
            for (Reponse reponse : qcmQuestion.get().getReponses()) {
                Optional<Reponse> questionReponse = reponseRepository.findById(reponse.getId());
                if (questionReponse.isEmpty()) {
                    log.error("La réponse {} n'existe pas.", reponse.getId());
                }
                log.info("Ajout de la réponse et de la question dans la map.");
                qcmQuestionsReponsesMap.put(reponse, question);
            }
        }


        Map<Reponse, Question> testQuestionsReponsesMap = new HashMap<>();

        log.info("Vérification et récupération des réponses renvoyées dans le test.");
        for (Reponse reponse : test.getReponses()) {
            Optional<Reponse> testReponse = reponseRepository.findById(reponse.getId());
            if (testReponse.isEmpty()) {
                log.error("La réponse {} renvoyée dans le test {} n'existe pas.", reponse.getId(), test.getId());
            }
            Optional<Question> testRepQuestion = questionRepository.findById(testReponse.get().getQuestion().getId());
            if (testRepQuestion.isEmpty()) {
                log.error("La question {} liée à la réponse {} n'existe pas.", testReponse.get().getQuestion().getId(), testReponse.get().getId());
            }
            log.info("Vérification que la question {} est dans la map des questions/réponses du qcm", testRepQuestion.get().getId());
            if (qcmQuestionsReponsesMap.containsValue(testRepQuestion)) {
                for (Reponse testRepQuestionReponse : testRepQuestion.get().getReponses()) {
                    if (!testReponse.get().getQuestion().equals(testRepQuestion)) {
                        log.error("La question liée à la réponse {} ne correspond pas à la question liée côté qcm", testReponse.get().getId());
                    }
                    if (test.getReponses().contains(testRepQuestionReponse) && !testQuestionsReponsesMap.containsKey(testRepQuestionReponse)) {
                        log.info("Ajout de la réponse {} avec comme question liée {}.", testReponse.get().getId(), testRepQuestion.get().getId());
                        testQuestionsReponsesMap.put(testReponse.get(), testRepQuestion.get());
                    }
                }
            }
        }

        Double testScore = 0.0;

        log.info("Vérifications de base pour chaque réponse.");
        for (Question qcmQuestionValue : qcmQuestionsReponsesMap.values()) {
            int numberOfReponses = qcmQuestionValue.getReponses().size();

            log.info("Stream des réponses du test pour comparer le nombre de réponses liées afin de vérifier les retours de l'utilisateur.");
            Stream<Key> streamTestQuestReponses = mapFilterService.keys(testQuestionsReponsesMap, qcmQuestionValue);
            Set<Reponse> tempTestReponses = (Set<Reponse>) streamTestQuestReponses.findAny().get();

            if (tempTestReponses.size() == 0) {
                log.error("Il n'y a aucune réponse liée à la question {}.", qcmQuestionValue.getId());
            }
            if (tempTestReponses.size() > numberOfReponses) {
                log.error("La question côté test contient plus de réponses que la question côté qcm. Question ID : {}", qcmQuestionValue.getId());
            }

            log.info("Vérification si les réponses du test sont justes et calcul du score.");
            for (Reponse linkedReponse : tempTestReponses) {
                if (linkedReponse.isCorrectAnswer()) {
                    testScore += linkedReponse.getPoints();
                } else if (!linkedReponse.isCorrectAnswer()) {
                    testScore -= linkedReponse.getPoints();
                }
                log.info("Sauvegarde de la réponse liée au test.");
                reponseRepository.saveAndFlush(linkedReponse);
                tempTestReponses.remove(linkedReponse); //Nous permet d'éviter les doubles une nouvelle fois, petit extra de sécu
            }
            tempTestReponses.clear();
        }


        log.info("Enregistrement du score du test");
        test.setScore(testScore);
        test.setTitre(currentTest.get().getTitre());
        test.setAlreadySubmitted(true);
        log.info("Le test {} ne pourra désormais plus être soumis", test.getId());
        testRepository.saveAndFlush(test);

        log.info("Enregistrement du nouveau score de l'utilisateur.");
        int numberOfTests = utilisateurTest.get().getTests().size();
        Double newUserGlobalScore = (testScore + utilisateurTest.get().getGlobalScore()) / numberOfTests;
        utilisateurTest.get().setGlobalScore(newUserGlobalScore);
        utilisateurRepository.saveAndFlush(utilisateurTest.get());

        log.info("Le test {} a été soumis avec succès.", test.getId());
        return test;
    }


    @Override
    public Test retrieveSingleCandidatTest (String userLogin, UUID testId){

        Optional<Utilisateur> loggedUser = utilisateurRepository.findUserWithLogin(userLogin);
        if (loggedUser.isEmpty()) {
            log.error("Impossible de récupérer le test {}. L'utilisateur {} n'existe pas.", testId, userLogin);
            return null;
        }

        Optional<Test> retrievedTest = testRepository.findById(testId);
        if (retrievedTest.isEmpty()) {
            log.error("Impossible de récupérer le test {}. Il n'existe pas.", testId);
            return null;
        }

        Utilisateur testAssignedUser = retrievedTest.get().getUtilisateur();

        if (!testAssignedUser.equals(loggedUser)) {
            log.error("Le test {} n'est pas assigné à l'utilisateur ({}) qui tente d'y accéder.", testId, userLogin);
            return null;
        }

        log.info("Test {} récupéré avec succès par l'utilisateur {}.", testId, userLogin);
        return retrievedTest.get();

    }

    @Override
    public List<Test> retrieveAllCandidatTest (String userLogin){

        Optional<Utilisateur> loggedUser = utilisateurRepository.findUserWithLogin(userLogin);
        if (loggedUser.isEmpty()) {
            log.error("Impossible de récupérer les tests de l'utilisateur {}. Il n'existe pas.", userLogin);
            return null;
        }

        log.info("Tests de l'utilisateur {} récupérés avec succès.", userLogin);
        return testRepository.findTestByCandidat(loggedUser.get().getId());
    }

    @Override
    public Test utilisateurTestSelfAssign (String userLogin, UUID testId){
        //TODO : tester si ok
        Optional<Utilisateur> loggedUser = utilisateurRepository.findUserWithLogin(userLogin);
        if (loggedUser.isEmpty()) {
            log.error("Impossible d'assigner le test {} à l'utilisateur {}. L'utilisateur n'existe pas.", testId, userLogin);
            return null;
        }

        Optional<Test> currentTest = testRepository.findById(testId);
        if (currentTest.isEmpty()) {
            log.error("Impossible d'assigner le test {} à l'utilisateur {}. Ce test n'existe pas.", testId, userLogin);
            return null;
        }

        if (currentTest.get().getUtilisateur() != null) {
            log.info("Impossible d'assigner le test {} à l'utilisateur {}. Il est déja assigné.", testId, userLogin);
            return null;
        }

        currentTest.get().setUtilisateur(loggedUser.get());
        log.info("Le test {} a été assigné à l'utilisateur {} avec succès.", testId, userLogin);
        return testRepository.saveAndFlush(currentTest.get());
    }

    @Override
    public String deleteTest (UUID testId){
        testRepository.deleteById(testId);
        log.info("Le test avec l'id {} a été supprimé.", testId);
        return "Le test avec l'id " + testId + " a été supprimé";
    }

}
