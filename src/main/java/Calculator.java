import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

public class Calculator {
    private final Object lock = new Object();
    private final Compiler compiler;
    private Class<?> compiledClass;
    private final Flow flow;
    private final Map<String, List<String>> formulasMap;

    Calculator(Compiler compiler) {
        this.compiler = compiler;
        flow = new Flow();
        formulasMap = UtilHelper.initFormulasMap();
    }

    public void compile() throws InterruptedException, IOException {
        while (true) {
            synchronized (lock) {
                System.out.println("inside compile");
                compiledClass = compiler.doCompile();
                lock.notify();
                while (compiledClass == null) {
                    lock.wait();
                }
            }
            Thread.sleep(3000);
        }
    }

    public void calc() throws InterruptedException {
        while (true) {
            synchronized (lock) {
                System.out.println("inside calc");
                Map<String, Integer> argumentsFlow = flow.generateFlow();
                doCalc(compiledClass, argumentsFlow);
                lock.wait();
            }
            Thread.sleep(3000);
        }
    }

    private void doCalc(Class<?> loaded, Map<String, Integer> argumentsMap) {
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
