package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.*;
import aubay.lu.projetrh.repository.*;
import aubay.lu.projetrh.service.*;
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
    private ReponseService reponseService;
    private MapFilterService mapFilterService;

    private QuestionService questionService;
    private static Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    TestServiceImpl(TestRepository testRepository,
                    TestScoreService testScoreService,
                    UtilisateurRepository utilisateurRepository,
                    QcmRepository qcmRepository,
                    ReponseRepository reponseRepository,
                    QuestionRepository questionRepository,
                    MapFilterService mapFilterService,
                    QuestionService questionService,
                    ReponseService reponseService) {
        this.testRepository = testRepository;
        this.testScoreService = testScoreService;
        this.utilisateurRepository = utilisateurRepository;
        this.qcmRepository = qcmRepository;
        this.reponseRepository = reponseRepository;
        this.questionRepository = questionRepository;
        this.mapFilterService = mapFilterService;
        this.questionService = questionService;
        this.reponseService = reponseService;
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

        Optional<Qcm> qcmTest = qcmRepository.findById(test.getQcmId());
        if (qcmTest.isEmpty()) {
            log.error("Le qcm avec l'id {} n'existe pas. Impossible de créer le test.", test.getQcmId());
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
        testRepository.saveAndFlush(test);

        log.info("Création des duplicata des questions");
        questionService.duplicateQuestionsFromQcm(qcmTest.get(), test);

        return test;
        //TODO : Implémenter dates ?
    }

    @Override
    public Test updateTest(Test test) {

        Optional<Test> updatedTest = testRepository.findById(test.getId());
        if (updatedTest.isEmpty()) {
            log.error("Le test {} n'existe pas. Impossible d'effectuer la modification.", test.getId());
            return null;
        }

        if(updatedTest.get().isAlreadySubmitted() && !test.isAlreadySubmitted()){
            log.error("Le test {} a déja été soumis, il est impossible de modifier ce paramètre !", updatedTest.get().getId());
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

        Optional<Test> submittedTest = testRepository.findById(test.getId());
        if(submittedTest.isEmpty()){
            log.error("Le test {} n'existe pas/plus. Le test ne pourra pas être soumis.", test.getId());
            return null;
        }

        if(submittedTest.get().isAlreadySubmitted()){
            log.info("Le test {} a déja été soumis.", submittedTest.get().getTitre());
            return null;
        }

        Optional<Utilisateur> candidat = utilisateurRepository.findById(test.getUtilisateur().getId());
        if(candidat.isEmpty()){
            log.error("Le candidat {} n'existe pas/plus.", test.getUtilisateur().getId());
            return null;
        }

        if(!test.getUtilisateur().getId().equals(candidat.get().getId())){
            log.error("Le candidat {} qui a soumis le test {} n'est pas assigné à ce dernier. Test non soumis.", test.getUtilisateur().getId(), test.getId());
            return null;
        }

        //Set<Reponse> testReponses = new HashSet<>();
        Map<Reponse, Question> mapOriginalQuestionReponses = new HashMap<>();
        log.info("Vérification des questions du test avant soumission afin de vérifier qu'elles existent toujours ainsi que leurs réponses.");
        for(Question question : submittedTest.get().getQuestions()){
            Optional<Question> questionBdd = questionRepository.findById(question.getId());
            if(questionBdd.isEmpty()){
                log.error("La question {} n'existe pas/plus. Impossible de soumettre le test {}.", question.getId(), submittedTest.get().getId());
                return null;
            }
            log.info("Vérification des réponses liées à la question {}.", questionBdd.get().getId());
            for(Reponse reponse : questionBdd.get().getReponses()){
                Optional<Reponse> reponseBdd = reponseRepository.findById(reponse.getId());
                if(reponseBdd.isEmpty()){
                    log.error("La réponse {} liée à la question {} n'existe pas/plus. Impossible de soumettre le test.", reponse.getId(), questionBdd.get().getId());
                    return null;
                }
                //testReponses.add(reponseBdd.get());
                mapOriginalQuestionReponses.put(reponseBdd.get(), questionBdd.get());
            }
        }

        log.info("Vérification des réponses soumises afin de vérifier qu'elles existent.");
        Map<Reponse, Question> mapTestQuestionReponses = new HashMap<>();
        for(Question question : test.getQuestions()){
            Optional<Question> testQuestion = questionRepository.findById(question.getId());
            if(testQuestion.isEmpty()){
                log.error("La question {} n'existe pas/plus", question.getId());
                return null;
            }
            for(Reponse reponse : question.getReponses()){
                Optional<Reponse> submittedReponse = reponseRepository.findById(reponse.getId());
                if(submittedReponse.isEmpty()){
                    log.error("La réponse {} renvoyée dans le test {} n'existe pas/plus. Impossible de soumettre le test.", reponse.getId(), test.getId());
                    return null;
                } else {
                    mapTestQuestionReponses.put(reponse, testQuestion.get());
                }
            }
        }

        log.info("Vérification de la présence de la réponse soumise dans la liste des réponses des questions du test.");
        for(Reponse reponse : mapTestQuestionReponses.keySet()){
            Optional<Reponse> mapReponse = reponseRepository.findById(reponse.getId());
            if(mapReponse.isEmpty()){
                log.error("La réponse {} n'existe pas/plus.", reponse.getId());
                return null;
            }
            if(!mapOriginalQuestionReponses.containsKey(mapReponse.get())){
                log.error("La réponse {} ne correspond à aucune réponse du test {}.", reponse.getId(), test.getId());
                return null;
            }
        }


        Double pointsParReponse = 0.0;
        Integer numberOfCorrectReponses = 0;
        log.info("Création d'une map qui servira à lister le nombre de points par réponse juste de chaque question.");
        Map<Question, Double> mapPointsParReponse = new HashMap<>();
        for(Question question : submittedTest.get().getQuestions()){
            Optional<Question> questionBdd = questionRepository.findById(question.getId());
            if(questionBdd.isEmpty()){
                log.error("La question {} n'existe pas/plus.", question.getId());
                return null;
            }

            numberOfCorrectReponses = reponseService.getNumberOfCorrectReponses(questionBdd.get());
            pointsParReponse = questionBdd.get().getPoints() / numberOfCorrectReponses;

            mapPointsParReponse.put(questionBdd.get(), pointsParReponse);
        }

        log.info("Calcul du score");
        Double testScore = 0.0;
        Set<Question> finalQuestionListe = mapOriginalQuestionReponses.values().stream().collect(Collectors.toSet());

        for(Question question : finalQuestionListe){

            Set<Reponse> tempTestReponses = mapFilterService.keys(mapTestQuestionReponses, question).collect(Collectors.toSet());

            if(tempTestReponses.size() > question.getReponses().size()){
                log.error("La question dans le test renvoyée par le candidat contient plus de réponses que dans le test de base. Test ID : {}", test.getId());
                return null;
            }

            for(Reponse linkedReponse : tempTestReponses){
                Optional<Reponse> finalReponse = reponseRepository.findById(linkedReponse.getId());
                if(finalReponse.isEmpty()){
                    log.error("La réponse {} n'existe pas/plus.", linkedReponse.getId());
                    return null;
                }

                if(linkedReponse.isSelectedAnswer() && finalReponse.get().isCorrectAnswer()){
                    testScore += pointsParReponse;
                }

                if(linkedReponse.isSelectedAnswer() && !finalReponse.get().isCorrectAnswer()){
                    testScore -= pointsParReponse;
                }

                finalReponse.get().setSelectedAnswer(linkedReponse.isSelectedAnswer());
                reponseRepository.saveAndFlush(finalReponse.get());
            }
        }

        log.info("Enregistrement du test et du score", test.getId());
        log.info("Le test {} ne pourra désormais plus être soumis.", test.getId());
        test.setAlreadySubmitted(true);
        test.setScore(testScore);
        test.setQcmId(submittedTest.get().getQcmId());
        test.setTitre(submittedTest.get().getTitre());
        testRepository.saveAndFlush(test);

        log.info("Calcul du nouveau score du candidat {}", candidat.get().getId());
        testScoreService.setUtilisateurGlobalScore(candidat.get());

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
    public void deleteTest(UUID testId){

        Optional<Test> deletedTest = testRepository.findById(testId);
        if(deletedTest.isEmpty()){
            log.error("Le test {} a déja été supprimé ou n'existe pas.", testId);
        }

        for(Question question : deletedTest.get().getQuestions()){
            Optional<Question> deletedQuestion = questionRepository.findById(question.getId());
            if(deletedTest.isEmpty()){
                log.info("La question {} n'existe pas/plus.", question.getId());
            }
            questionRepository.deleteById(deletedQuestion.get().getId());
        }

        testRepository.deleteById(deletedTest.get().getId());

    }

}
