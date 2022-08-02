import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UtilHelper {
    public static Map<String, Integer> generateFlow() {
        Map<String, Integer> flow = new HashMap<String, Integer>(Arguments.values().length);
        Random r = new Random();
        int low = -100;
        int high = 100;
        for (Arguments arg : Arguments.values()) {
            int argValue = r.nextInt(high - low) + low;
            flow.put(arg.name(), argValue);
        }
        return flow;
    }

    private enum Arguments {
        A, B, C, D, E, F
    }

    private enum Formulas {
        sum, multiply, subtract
    }

    public static Map<String, List<String>> initFormulasMap() {
        Map<String, List<String>> formulasMap = new ConcurrentHashMap<>();
        formulasMap.put(Formulas.sum.name(), Arrays.asList(Arguments.A.name(), Arguments.B.name()));
        formulasMap.put(Formulas.multiply.name(), Arrays.asList(Arguments.C.name(), Arguments.D.name()));
        formulasMap.put(Formulas.subtract.name(), Arrays.asList(Arguments.E.name(), Arguments.F.name()));
        return formulasMap;
    }
}
