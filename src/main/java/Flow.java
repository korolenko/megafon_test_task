import java.util.*;

/**
 * Класс генерации входного потока параметров
 */
public class Flow {
    public Map<String, Integer> generateFlow() {
        Map<String, Integer> argumentsFlow = new HashMap<>();
        Random r = new Random();
        int low = -100;
        int high = 100;
        for (Arguments arg : Arguments.values()) {
            int argValue = r.nextInt(high - low) + low;
            argumentsFlow.put(arg.name(), argValue);
        }
        return argumentsFlow;
    }

    private enum Arguments {
        A, B, C, D, E, F
    }
}
