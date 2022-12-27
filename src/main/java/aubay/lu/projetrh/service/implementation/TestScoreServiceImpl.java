package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.Test;
import aubay.lu.projetrh.model.Utilisateur;
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

    @Autowired
    TestScoreServiceImpl(UtilisateurRepository utilisateurRepository, TestRepository testRepository){
        this.utilisateurRepository = utilisateurRepository;
        this.testRepository = testRepository;
    }

    @Override
    public void setUtilisateurGlobalScore(Utilisateur testUtilisateur) {
        Optional<Utilisateur> candidat = utilisateurRepository.findById(testUtilisateur.getId());
        if(candidat.isPresent()){
            Double tempGlobalScore = 0.0;
            Set<Test> candidatTests = candidat.get().getTests();
            candidat.get().setGlobalScore(0.0);
            for(Test test : candidatTests){
                if(test.isAlreadySubmitted()){
                    tempGlobalScore += test.getScore();
                }
            }
            Double newGlobalScore = tempGlobalScore / candidatTests.size();
            candidat.get().setGlobalScore(newGlobalScore);
            utilisateurRepository.saveAndFlush(candidat.get());
        }
    }

}