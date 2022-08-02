import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.Collections;

public class Compiler {
    private final String className;
    private final String fileName;

    public Compiler(String className, String fileName){
        this.className = className;
        this.fileName = fileName;
    }
    public Class<?> doCompile() throws MalformedURLException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        JavaFileObject sfo = fileToJavaFileObject(className, fileName);
        Iterable<? extends JavaFileObject> compilationUnits = Collections.singletonList(sfo);
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);
        boolean status = task.call();
        System.out.println("Compilation status: " + status);
        if(status){
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File("").toURI().toURL()});
            try {
                return Class.forName(className, true, classLoader);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("No such class in file");
            }
        } else {
            throw new RuntimeException("compilation failed");
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
