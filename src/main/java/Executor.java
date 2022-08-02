import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * запускается 2 потока, один проверяет, нужна ли компиляция, если да, то выполняет ее
 * второй выполняет вычисление значений по формулам
 * работа потоков синхронизирована
 */
public class Executor {
    private final Object lock = new Object();
    private final Compiler compiler;
    private Class<?> compiledClass;
    private final Flow flow;
    private final Map<String, List<String>> formulasMap;

    Executor(Compiler compiler) {
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
                Calculator.doCalc(compiledClass, argumentsFlow,formulasMap);
                lock.wait();
            }
            Thread.sleep(3000);
        }
    }
}
