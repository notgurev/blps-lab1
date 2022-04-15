package foxgurev.services.main.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapBuilder {
    Map<String, Object> map = new LinkedHashMap<>();

    public static foxgurev.greeting.util.MapBuilder map() {
        return new foxgurev.greeting.util.MapBuilder();
    }

    public static foxgurev.greeting.util.MapBuilder map(String key, Object value) {
        return new foxgurev.greeting.util.MapBuilder().put(key, value);
    }

    public foxgurev.greeting.util.MapBuilder put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return map;
    }
}
