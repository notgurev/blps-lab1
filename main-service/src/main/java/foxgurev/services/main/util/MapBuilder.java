package foxgurev.services.main.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapBuilder {
    Map<String, Object> map = new LinkedHashMap<>();

    public static MapBuilder map() {
        return new MapBuilder();
    }

    public static MapBuilder map(String key, Object value) {
        return new MapBuilder().put(key, value);
    }

    public MapBuilder put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return map;
    }
}
