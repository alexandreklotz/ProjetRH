package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.*;
import aubay.lu.projetrh.repository.*;
import aubay.lu.projetrh.service.MapFilterService;
import aubay.lu.projetrh.service.TestScoreService;
import aubay.lu.projetrh.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TestServiceImpl implements TestService {

    private TestRepository testRepository;

    private TestScoreService testScoreService;
    private UtilisateurRepository utilisateurRepository;
    private QcmRepository qcmRepository;
    private ReponseRepository reponseRepository;
    private QuestionRepository questionRepository;
    private MapFilterService mapFilterService;
    private static Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    TestServiceImpl(TestRepository testRepository,
                    TestScoreService testScoreService,
                    UtilisateurRepository utilisateurRepository,
                    QcmRepository qcmRepository,
                    ReponseRepository reponseRepository,
                    QuestionRepository questionRepository,
                    MapFilterService mapFilterService) {
        this.testRepository = testRepository;
        this.testScoreService = testScoreService;
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

        log.info("Soumission d'un test par l'utilisateur {}.", test.getUtilisateur().getId());

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

        Optional<Utilisateur> utilisateurTest = utilisateurRepository.findById(test.getUtilisateur().getId());
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
                return null;
            }
            log.info("Récupération des réponses de la question {} et vérification des réponses.", question.getId());
            for (Reponse reponse : qcmQuestion.get().getReponses()) {
                Optional<Reponse> questionReponse = reponseRepository.findById(reponse.getId());
                if (questionReponse.isEmpty()) {
                    log.error("La réponse {} n'existe pas.", reponse.getId());
                    return null;
                }
                log.info("Vérification que la question n'est pas déja présente en tant que valeur dans la map plus de fois qu'elle n'a de questions");
                if(qcmQuestionsReponsesMap.containsValue(qcmQuestion.get())){
                    Stream<Reponse> numberOfMaximumValuesMap = mapFilterService.keys(qcmQuestionsReponsesMap, qcmQuestion.get());
                    Set<Reponse> setOfKeysLinkedToQuestion = numberOfMaximumValuesMap.collect(Collectors.toSet());
                    if(setOfKeysLinkedToQuestion.size() > qcmQuestion.get().getReponses().size()){
                        log.error("La question {} est déja présente dans la map.", qcmQuestion.get().getId());
                        return null;
                    }
                }
                log.info("Ajout de la réponse {} dans la map en tant que clé de la question {}", questionReponse.get().getId(), qcmQuestion.get().getId());
                qcmQuestionsReponsesMap.put(questionReponse.get(), qcmQuestion.get());
            }
        }


        Map<Reponse, Question> testQuestionsReponsesMap = new HashMap<>();

        log.info("Vérification et récupération des réponses renvoyées dans le test.");
        for (Reponse reponse : test.getReponses()) {
            Optional<Reponse> testReponse = reponseRepository.findById(reponse.getId());
            if (testReponse.isEmpty()) {
                log.error("La réponse {} renvoyée dans le test {} n'existe pas.", reponse.getId(), test.getId());
                return null;
            }
            Optional<Question> testRepQuestion = questionRepository.findById(testReponse.get().getQuestion().getId());
            if (testRepQuestion.isEmpty()) {
                log.error("La question {} liée à la réponse {} n'existe pas.", testReponse.get().getQuestion().getId(), testReponse.get().getId());
                return null;
            }
            log.info("Vérification que la question {} est dans la map des questions/réponses du qcm", testRepQuestion.get().getId());
            if (qcmQuestionsReponsesMap.containsValue(testRepQuestion.get())) {
                for (Reponse testRepQuestionReponse : testRepQuestion.get().getReponses()) {
                    if (!testReponse.get().getQuestion().getId().equals(testRepQuestion.get().getId())) {
                        log.error("La question liée à la réponse {} ne correspond pas à la question liée côté qcm", testReponse.get().getId());
                        return null;
                    }
                    Set<Reponse> streamReponse = testReponse.stream().filter(r -> r.getId().equals(testRepQuestionReponse.getId())).collect(Collectors.toSet());
                    if(!streamReponse.isEmpty() && !testQuestionsReponsesMap.containsKey(testRepQuestionReponse)){
                        log.info("Ajout de la réponse {} avec comme question liée {}.", testReponse.get().getId(), testRepQuestion.get().getId());
                        testQuestionsReponsesMap.put(testReponse.get(), testRepQuestion.get());
                    } else if (!streamReponse.isEmpty() && testQuestionsReponsesMap.containsKey(testRepQuestionReponse)){
                        log.error("La réponse {} a déja été renvoyée dans ce test.", testRepQuestionReponse.getId());
                        return null;
                    }
                }
            }
        }

        Double testScore = 0.0;
        Set<Reponse> finalReponseListe = new HashSet<>();

        log.info("Vérifications de base pour chaque réponse.");
        Set<Question> finalQuestionListe = qcmQuestionsReponsesMap.values().stream().collect(Collectors.toSet());

        for (Question qcmQuestionValue : finalQuestionListe) {

            log.info("Stream des réponses du test pour comparer le nombre de réponses liées afin de vérifier les retours de l'utilisateur.");
            Set<Reponse> tempTestReponses = mapFilterService.keys(testQuestionsReponsesMap, qcmQuestionValue).collect(Collectors.toSet());


            if (tempTestReponses.size() > qcmQuestionValue.getReponses().size()) {
                log.error("La question côté test contient plus de réponses que la question côté qcm. Question ID : {}", qcmQuestionValue.getId());
                return null;
            }

            log.info("Vérification si les réponses du test sont justes et calcul du score.");
            for (Reponse linkedReponse : tempTestReponses) {

                Optional<Reponse> finalReponse = reponseRepository.findById(linkedReponse.getId());
                if(finalReponse.isEmpty()){
                    log.error("La réponse {} n'existe pas/plus.", linkedReponse.getId());
                }

                if (finalReponse.get().isCorrectAnswer()) {
                    testScore += finalReponse.get().getPoints();
                } else if (!finalReponse.get().isCorrectAnswer()) {
                    testScore -= finalReponse.get().getPoints();
                }

                log.info("Sauvegarde de la réponse liée au test et sauvegarde du test.");
                finalReponseListe.add(finalReponse.get());
                Set<Test> reponseTests = finalReponse.get().getTests();
                reponseTests.add(currentTest.get());
                reponseRepository.saveAndFlush(finalReponse.get());
            }
        }

        test.setReponses(finalReponseListe);

        log.info("Enregistrement du score du test");
        test.setScore(testScore);
        test.setTitre(currentTest.get().getTitre());
        test.setAlreadySubmitted(true);
        log.info("Le test {} ne pourra désormais plus être soumis", test.getId());
        testRepository.saveAndFlush(currentTest.get());

        log.info("Enregistrement du nouveau score de l'utilisateur.");
        /*int numberOfTests = utilisateurTest.get().getTests().size();
        Double newUserGlobalScore = (testScore + utilisateurTest.get().getGlobalScore()) / numberOfTests;
        utilisateurTest.get().setGlobalScore(newUserGlobalScore);
        utilisateurRepository.saveAndFlush(utilisateurTest.get());*/
        testScoreService.setUtilisateurGlobalScore(utilisateurTest.get(), testScore);

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
    public void deleteTest (UUID testId){
        Optional<Test> deletedTest = testRepository.findById(testId);
        if(deletedTest.isPresent()){
            Optional<Utilisateur> testUtilisateur = utilisateurRepository.findById(deletedTest.get().getUtilisateur().getId());
            if(testUtilisateur.isPresent()){
                testScoreService.updateUtilisateurGlobalScoreAfterDelete(testId, testUtilisateur.get().getId());
            }
        }
        testRepository.deleteById(testId);
        log.info("Le test avec l'id {} a été supprimé.", testId);
    }

}
