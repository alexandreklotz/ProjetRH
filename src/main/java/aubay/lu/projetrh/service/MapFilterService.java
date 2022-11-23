package aubay.lu.projetrh.service;

import java.security.Key;
import java.util.Map;
import java.util.stream.Stream;

public interface MapFilterService {

    public <K, V> Stream<Key> keys(Map<K, V> map, V value);

}
