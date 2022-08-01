import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

public class Compiler {
    public static void main(String[] args) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        JavaFileObject sfo = fileToJavaFileObject("Test","file.txt");

        Iterable<? extends JavaFileObject> compilationUnits = Collections.singletonList(sfo);
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);

        boolean success = task.call();
        System.out.println("Success: " + success);

        if (success) {
            try {
                URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File("").toURI().toURL()});
                Class<?> loaded = Class.forName("Test", true, classLoader);
                Method sum = loaded.getMethod("sum", Integer.class, Integer.class);
                Method multiply = loaded.getMethod("multiply", Integer.class, Integer.class);
                System.out.println("Result sum: " + sum.invoke(null, 4, 2));
                System.out.println("Result multiply: " + multiply.invoke(null, 4, 2));

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
     private static String readFormulas(String fileName) throws URISyntaxException, IOException {
         return new String(Files.readAllBytes(FileHelper.getFilePath(fileName)));
     }

     private static JavaFileObject fileToJavaFileObject(String className,String fileName){
         try {
             return new JavaCodeMapper(className,readFormulas(fileName));
         } catch (URISyntaxException
                 | IOException e) {
             throw new RuntimeException("unable to read file", e);
         }
     }
}
