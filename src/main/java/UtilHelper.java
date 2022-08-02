import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class UtilHelper {
    private enum Formulas {
        sum, multiply, subtract
    }

    /**
     * метод выполняет маппинг параметров и формул
     * @return
     */
    public static Map<String, List<String>> initFormulasMap() {
        Map<String, List<String>> formulasMap = new ConcurrentHashMap<>();
        formulasMap.put(Formulas.sum.name(), Arrays.asList(Arguments.A.name(), Arguments.B.name()));
        formulasMap.put(Formulas.multiply.name(), Arrays.asList(Arguments.C.name(), Arguments.D.name()));
        formulasMap.put(Formulas.subtract.name(), Arrays.asList(Arguments.E.name(), Arguments.F.name()));
        return formulasMap;
    }

    public enum Arguments {
        A, B, C, D, E, F
    }
}
