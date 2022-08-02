import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class Calculator {
    public static void doCalc(Class<?> loaded, Map<String, Integer> argumentsMap,Map<String, List<String>> formulasMap) {
        for (Map.Entry<String, List<String>> entry : formulasMap.entrySet()) {
            try {
                String formulaName = entry.getKey();
                Method method = loaded.getMethod(formulaName, Integer.class, Integer.class);
                String firstArgName = entry.getValue().get(0);
                Integer firstArgVal = argumentsMap.get(firstArgName);
                String secondArgName = entry.getValue().get(1);
                Integer secondArgVal = argumentsMap.get(secondArgName);
                System.out.println("val1: " + firstArgVal + " " +
                                "val2: " + secondArgVal + " " +
                        "Result of method : " + formulaName +
                        ": " + method.invoke(null, firstArgVal, secondArgVal));
            } catch (NoSuchMethodException e) {
                System.err.println("No such method: " + e);
            } catch (IllegalAccessException e) {
                System.err.println("Illegal access: " + e);
            } catch (InvocationTargetException e) {
                System.err.println("Invocation target: " + e);
            }
        }
    }
}
