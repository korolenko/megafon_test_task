import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class Compiler {
    private final String className;
    private final String fileName;
    private long timeStamp = -1;
    private Class<?> compiledClass;

    public Compiler(String className, String fileName){
        this.className = className;
        this.fileName = fileName;
    }

    public Class<?> doCompile() throws IOException {
        File file = new File(getFilePath(fileName).toString());
        long last = this.timeStamp;
        System.out.println("last " + last);
        this.timeStamp = file.lastModified();
        if(last != -1){
            if(last == timeStamp){
                System.out.println("do not need to compile, return last compiled class");
                return this.compiledClass;
            } else {
                System.out.println("need to compile");
            }
        }
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
                this.compiledClass =  Class.forName(className, true, classLoader);
                return this.compiledClass;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("No such class in file");
            }
        } else {
            throw new RuntimeException("compilation failed");
        }
    }

    private JavaFileObject fileToJavaFileObject(String className,String fileName) throws IOException {
        String fileValue = new String(Files.readAllBytes(getFilePath(fileName)));
        return new JavaCodeMapper(className,fileValue);
    }

    private static Path getFilePath(String fileName) {
        return Paths.get(System.getProperty("user.dir") + "/" +fileName);
    }
}
