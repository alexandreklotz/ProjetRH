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
            log.error("Le qcm avec l'id {} n'existe pas. Impossible de cr??er le test.", test.getQcm().getId());
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

        log.info("Le test {} a ??t?? cr????.", test.getTitre());
        return testRepository.saveAndFlush(test);
        //TODO : Impl??menter dates ?
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

        log.info("Le test {} avec l'id {} a ??t?? mis ?? jour.", test.getTitre(), test.getId());
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
            log.info("Le test {} a d??ja ??t?? soumis.", test.getId());
            return null; //le test a d??ja ??t?? submit
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

        log.info("D??but du check des combinaisons question/r??ponses.");
        log.info("V??rification et r??cup??ration des questions du qcm.");
        for (Question question : qcmTest.get().getQuestions()) {
            Optional<Question> qcmQuestion = questionRepository.findById(question.getId());
            if (qcmQuestion.isEmpty()) {
                log.error("La question {} n'existe pas.", question.getId());
                return null;
            }
            log.info("R??cup??ration des r??ponses de la question {} et v??rification des r??ponses.", question.getId());
            for (Reponse reponse : qcmQuestion.get().getReponses()) {
                Optional<Reponse> questionReponse = reponseRepository.findById(reponse.getId());
                if (questionReponse.isEmpty()) {
                    log.error("La r??ponse {} n'existe pas.", reponse.getId());
                    return null;
                }
                log.info("V??rification que la question n'est pas d??ja pr??sente en tant que valeur dans la map plus de fois qu'elle n'a de questions");
                if(qcmQuestionsReponsesMap.containsValue(qcmQuestion.get())){
                    Stream<Reponse> numberOfMaximumValuesMap = mapFilterService.keys(qcmQuestionsReponsesMap, qcmQuestion.get());
                    Set<Reponse> setOfKeysLinkedToQuestion = numberOfMaximumValuesMap.collect(Collectors.toSet());
                    if(setOfKeysLinkedToQuestion.size() > qcmQuestion.get().getReponses().size()){
                        log.error("La question {} est d??ja pr??sente dans la map.", qcmQuestion.get().getId());
                        return null;
                    }
                }
                log.info("Ajout de la r??ponse {} dans la map en tant que cl?? de la question {}", questionReponse.get().getId(), qcmQuestion.get().getId());
                qcmQuestionsReponsesMap.put(questionReponse.get(), qcmQuestion.get());
            }
        }


        Map<Reponse, Question> testQuestionsReponsesMap = new HashMap<>();

        log.info("V??rification et r??cup??ration des r??ponses renvoy??es dans le test.");
        for (Reponse reponse : test.getReponses()) {
            Optional<Reponse> testReponse = reponseRepository.findById(reponse.getId());
            if (testReponse.isEmpty()) {
                log.error("La r??ponse {} renvoy??e dans le test {} n'existe pas.", reponse.getId(), test.getId());
                return null;
            }
            Optional<Question> testRepQuestion = questionRepository.findById(testReponse.get().getQuestion().getId());
            if (testRepQuestion.isEmpty()) {
                log.error("La question {} li??e ?? la r??ponse {} n'existe pas.", testReponse.get().getQuestion().getId(), testReponse.get().getId());
                return null;
            }
            log.info("V??rification que la question {} est dans la map des questions/r??ponses du qcm", testRepQuestion.get().getId());
            if (qcmQuestionsReponsesMap.containsValue(testRepQuestion.get())) {
                for (Reponse testRepQuestionReponse : testRepQuestion.get().getReponses()) {
                    if (!testReponse.get().getQuestion().getId().equals(testRepQuestion.get().getId())) {
                        log.error("La question li??e ?? la r??ponse {} ne correspond pas ?? la question li??e c??t?? qcm", testReponse.get().getId());
                        return null;
                    }
                    Set<Reponse> streamReponse = testReponse.stream().filter(r -> r.getId().equals(testRepQuestionReponse.getId())).collect(Collectors.toSet());
                    if(!streamReponse.isEmpty() && !testQuestionsReponsesMap.containsKey(testRepQuestionReponse)){
                        log.info("Ajout de la r??ponse {} avec comme question li??e {}.", testReponse.get().getId(), testRepQuestion.get().getId());
                        testQuestionsReponsesMap.put(testReponse.get(), testRepQuestion.get());
                    } else if (!streamReponse.isEmpty() && testQuestionsReponsesMap.containsKey(testRepQuestionReponse)){
                        log.error("La r??ponse {} a d??ja ??t?? renvoy??e dans ce test.", testRepQuestionReponse.getId());
                        return null;
                    }
                }
            }
        }

        Double testScore = 0.0;
        Set<Reponse> finalReponseListe = new HashSet<>();

        log.info("V??rifications de base pour chaque r??ponse.");
        Set<Question> finalQuestionListe = qcmQuestionsReponsesMap.values().stream().collect(Collectors.toSet());

        for (Question qcmQuestionValue : finalQuestionListe) {

            log.info("Stream des r??ponses du test pour comparer le nombre de r??ponses li??es afin de v??rifier les retours de l'utilisateur.");
            Set<Reponse> tempTestReponses = mapFilterService.keys(testQuestionsReponsesMap, qcmQuestionValue).collect(Collectors.toSet());


            if (tempTestReponses.size() > qcmQuestionValue.getReponses().size()) {
                log.error("La question c??t?? test contient plus de r??ponses que la question c??t?? qcm. Question ID : {}", qcmQuestionValue.getId());
                return null;
            }

            Integer numberOfCorrectRep = 0;

            log.info("Compte du nombre de r??ponses correctes pour cette question pour calculer le nombre de points de chaque r??ponse.");
            for(Reponse questReponse : qcmQuestionValue.getReponses()){
                Optional<Reponse> rep = reponseRepository.findById(questReponse.getId());
                if(rep.isEmpty()){
                    return null;
                }
                if(questReponse.isCorrectAnswer()){
                    numberOfCorrectRep++;
                }
            }


            log.info("V??rification si les r??ponses du test sont justes et calcul du score.");
            for (Reponse linkedReponse : tempTestReponses) {

                Double pointsParReponse = qcmQuestionValue.getPoints() / numberOfCorrectRep;

                Optional<Reponse> finalReponse = reponseRepository.findById(linkedReponse.getId());
                if(finalReponse.isEmpty()){
                    log.error("La r??ponse {} n'existe pas/plus.", linkedReponse.getId());
                }

                if (finalReponse.get().isCorrectAnswer()) {
                    testScore += pointsParReponse;
                } else if (!finalReponse.get().isCorrectAnswer()) {
                    testScore -= pointsParReponse;
                }

                log.info("Sauvegarde de la r??ponse li??e au test et sauvegarde du test.");
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
        log.info("Le test {} ne pourra d??sormais plus ??tre soumis", test.getId());
        testRepository.saveAndFlush(test);

        log.info("Enregistrement du nouveau score de l'utilisateur.");
        testScoreService.setUtilisateurGlobalScore(utilisateurTest.get());

        log.info("Le test {} a ??t?? soumis avec succ??s.", test.getId());
        return test;
    }


    @Override
    public Test retrieveSingleCandidatTest (String userLogin, UUID testId){

        Optional<Utilisateur> loggedUser = utilisateurRepository.findUserWithLogin(userLogin);
        if (loggedUser.isEmpty()) {
            log.error("Impossible de r??cup??rer le test {}. L'utilisateur {} n'existe pas.", testId, userLogin);
            return null;
        }

        Optional<Test> retrievedTest = testRepository.findById(testId);
        if (retrievedTest.isEmpty()) {
            log.error("Impossible de r??cup??rer le test {}. Il n'existe pas.", testId);
            return null;
        }

        Utilisateur testAssignedUser = retrievedTest.get().getUtilisateur();

        if (!testAssignedUser.equals(loggedUser)) {
            log.error("Le test {} n'est pas assign?? ?? l'utilisateur ({}) qui tente d'y acc??der.", testId, userLogin);
            return null;
        }

        log.info("Test {} r??cup??r?? avec succ??s par l'utilisateur {}.", testId, userLogin);
        return retrievedTest.get();

    }

    @Override
    public List<Test> retrieveAllCandidatTest (String userLogin){

        Optional<Utilisateur> loggedUser = utilisateurRepository.findUserWithLogin(userLogin);
        if (loggedUser.isEmpty()) {
            log.error("Impossible de r??cup??rer les tests de l'utilisateur {}. Il n'existe pas.", userLogin);
            return null;
        }

        log.info("Tests de l'utilisateur {} r??cup??r??s avec succ??s.", userLogin);
        return testRepository.findTestByCandidat(loggedUser.get().getId());
    }

    @Override
    public Test utilisateurTestSelfAssign (String userLogin, UUID testId){
        //TODO : tester si ok
        Optional<Utilisateur> loggedUser = utilisateurRepository.findUserWithLogin(userLogin);
        if (loggedUser.isEmpty()) {
            log.error("Impossible d'assigner le test {} ?? l'utilisateur {}. L'utilisateur n'existe pas.", testId, userLogin);
            return null;
        }

        Optional<Test> currentTest = testRepository.findById(testId);
        if (currentTest.isEmpty()) {
            log.error("Impossible d'assigner le test {} ?? l'utilisateur {}. Ce test n'existe pas.", testId, userLogin);
            return null;
        }

        if (currentTest.get().getUtilisateur() != null) {
            log.info("Impossible d'assigner le test {} ?? l'utilisateur {}. Il est d??ja assign??.", testId, userLogin);
            return null;
        }

        currentTest.get().setUtilisateur(loggedUser.get());
        log.info("Le test {} a ??t?? assign?? ?? l'utilisateur {} avec succ??s.", testId, userLogin);
        return testRepository.saveAndFlush(currentTest.get());
    }

    @Override
    public void deleteTest(UUID testId){

        Optional<Test> deletedTest = testRepository.findById(testId);
        if(deletedTest.isEmpty()){
            log.error("Le test {} a d??ja ??t?? supprim?? ou n'existe pas.", testId);
        }

        Optional<Utilisateur> candidat = utilisateurRepository.findById(deletedTest.get().getUtilisateur().getId());
        if (candidat.isPresent()){
            log.info("Le candidat {} et le test {} existent, suppression du test et mise ?? jour du score du candidat.", testId, candidat.get().getId());
            for(Reponse reponse : deletedTest.get().getReponses()){
                log.info("Suppression du test de la liste de tests de chaque r??ponse li??e ?? ce dernier.");
                Optional<Reponse> currentReponse = reponseRepository.findById(reponse.getId());
                if(currentReponse.isEmpty()){
                    log.info("La r??ponse {} n'existe pas/plus !", reponse.getId());
                }
                Set<Test> reponseTests = reponse.getTests();
                reponseTests.remove(deletedTest.get());
                currentReponse.get().setTests(reponseTests);
                reponseRepository.saveAndFlush(reponse);
                log.info("Test supprim?? de la liste de tests de la r??ponse {} avec succ??s.", currentReponse.get().getId());
            }
            log.info("Suppression du test {}.", testId);
            testRepository.deleteById(testId);
            testScoreService.setUtilisateurGlobalScore(candidat.get());
            log.info("Le test {} a ??t?? supprim??.", testId);
        }

        log.info("Le candidat {} n'existe plus", deletedTest.get().getUtilisateur().getId());
        testRepository.deleteById(testId);
    }

}
