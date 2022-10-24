package aubay.lu.projetrh.service;

import aubay.lu.projetrh.model.Reponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReponseService {

    public List<Reponse> getAllReponses();

    public Optional<Reponse> getReponseById(UUID repId);

    public List<Reponse> getReponsesByQuestion(UUID questionId);

    public Reponse createReponse(Reponse reponse);

    public Reponse updateReponse(Reponse reponse);

    public String deleteReponse(UUID reponseId);
}
