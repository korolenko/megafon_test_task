import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Calculator {

    public static void main(String[] args) throws IOException {
        String className = "Test";
        String fileName = "file.txt";
        Compiler compiler = new Compiler(className,fileName);
        Class<?> compiledClass = compiler.doCompile();
        Map<String, Integer> arguments = UtilHelper.generateFlow();
        Map<String, List<String>> formulas = UtilHelper.initFormulasMap();
        calculate(compiledClass,formulas,arguments);
    }

    public static void calculate(Class<?> loaded, Map<String, List<String>> formulasMap,Map<String, Integer> argumentsMap) {
        for (Map.Entry<String, List<String>> entry : formulasMap.entrySet()) {
            try {
                String formulaName = entry.getKey();
                Method method = loaded.getMethod(formulaName, Integer.class, Integer.class);
                String firstArgName = entry.getValue().get(0);
                Integer firstArgVal = argumentsMap.get(firstArgName);
                String secondArgName = entry.getValue().get(1);
                Integer secondArgVal = argumentsMap.get(secondArgName);
                System.out.println("firstArgVal " + firstArgVal);
                System.out.println("secondArgVal " + secondArgVal);
                System.out.println("Result of method : " +formulaName + ": " + method.invoke(null, firstArgVal, secondArgVal));
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
