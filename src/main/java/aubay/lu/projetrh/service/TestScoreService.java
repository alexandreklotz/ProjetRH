package aubay.lu.projetrh.service;

import aubay.lu.projetrh.model.Utilisateur;

import java.util.UUID;

public interface TestScoreService {

    public void setUtilisateurGlobalScore(Utilisateur testUtilisateur, Double testScore);

    public void updateUtilisateurGlobalScoreAfterDelete(UUID testId, UUID candidatId);

}
