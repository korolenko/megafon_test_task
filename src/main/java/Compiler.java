import javax.tools.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;

public class Compiler {
    public static void main(String[] args) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        JavaFileObject sfo = new JavaSourceFromString("Test",
                "public class Test { public static int calc(Integer var1, Integer var2) { return var1 + var2; } }");

        Iterable<? extends JavaFileObject> compilationUnits = Collections.singletonList(sfo);
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);

        boolean success = task.call();
        System.out.println("Success: " + success);

        if (success) {
            try {
                URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File("").toURI().toURL()});
                Method method = Class.forName("Test", true, classLoader)
                        .getMethod("calc", Integer.class, Integer.class);
                System.out.println("Result: " + method.invoke(null, 4, 2));

            } catch (ClassNotFoundException e) {
                System.err.println("Class not found: " + e);
            } catch (NoSuchMethodException e) {
                System.err.println("No such method: " + e);
            } catch (IllegalAccessException e) {
                System.err.println("Illegal access: " + e);
            } catch (InvocationTargetException e) {
                System.err.println("Invocation target: " + e);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
