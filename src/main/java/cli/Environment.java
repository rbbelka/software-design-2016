package cli;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds map of defined variables
 */
public class Environment {
    private static final Environment INSTANCE = new Environment();
    private final Map<String, String> varMap;

    public static Environment getInstance() {
        return INSTANCE;
    }

    private Environment() {
        varMap = new HashMap<>();
    }

    public String getValue(String varName) {
        return varMap.get(varName);
    }

    public String putValue(String varName, String value) {
        return varMap.put(varName, value);
    }
}
