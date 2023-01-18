package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.Question;
import aubay.lu.projetrh.model.Test;
import aubay.lu.projetrh.model.Utilisateur;
import aubay.lu.projetrh.repository.QuestionRepository;
import aubay.lu.projetrh.repository.TestRepository;
import aubay.lu.projetrh.repository.UtilisateurRepository;
import aubay.lu.projetrh.service.TestScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class TestScoreServiceImpl implements TestScoreService {


    private UtilisateurRepository utilisateurRepository;
    private TestRepository testRepository;
    private QuestionRepository questionRepository;

    //TODO : implémenter logging

    @Autowired
    TestScoreServiceImpl(UtilisateurRepository utilisateurRepository, TestRepository testRepository, QuestionRepository questionRepository){
        this.utilisateurRepository = utilisateurRepository;
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public void setUtilisateurGlobalScore(Utilisateur testUtilisateur) {

        Optional<Utilisateur> candidat = utilisateurRepository.findById(testUtilisateur.getId());
        if(candidat.isPresent()){
            Double testMaxScore = 0.0;
            Double testPercentage = 0.0;
            Double tempGlobalScore = 0.0;
            candidat.get().setGlobalScore(0.0);
            for(Test test : candidat.get().getTests()){
                Optional<Test> candTest = testRepository.findById(test.getId());
                if(candTest.isEmpty()){
                    return;
                }
                if(candTest.get().isAlreadySubmitted()){
                    for(Question question : candTest.get().getQuestions()){
                        Optional<Question> testQuestion = questionRepository.findById(question.getId());
                        if(testQuestion.isEmpty()){
                            return;
                        }
                        testMaxScore += testQuestion.get().getPoints();
                        testPercentage = (candTest.get().getScore() / testMaxScore) * 100;
                    }
                    tempGlobalScore += testPercentage;
                }
            }
            Double newGlobalScore = tempGlobalScore / candidat.get().getTests().size();
            candidat.get().setGlobalScore(newGlobalScore);
            utilisateurRepository.saveAndFlush(candidat.get());
        }
    }

}