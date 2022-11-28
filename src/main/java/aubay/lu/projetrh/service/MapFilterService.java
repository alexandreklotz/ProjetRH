package aubay.lu.projetrh.service;

import aubay.lu.projetrh.model.Reponse;

import java.security.Key;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public interface MapFilterService {

    public <Reponse, Question> Stream<Reponse> keys(Map<Reponse, Question> map, Question question);

}
