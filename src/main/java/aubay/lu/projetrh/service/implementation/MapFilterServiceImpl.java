package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.model.Reponse;
import aubay.lu.projetrh.service.MapFilterService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class MapFilterServiceImpl implements MapFilterService {

    @Override
    public <K, V> Stream<Key> keys(Map<K, V> map, V value) {
        return (Stream<Key>) map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }
}
