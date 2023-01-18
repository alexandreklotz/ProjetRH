package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.Question;
import aubay.lu.projetrh.model.Reponse;
import aubay.lu.projetrh.service.MapFilterService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class MapFilterServiceImpl implements MapFilterService {

    @Override
    public <Reponse, Question> Stream<Reponse> keys(Map<Reponse, Question> map, Question question) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> question.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }

}
