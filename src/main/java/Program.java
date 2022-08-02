import java.io.IOException;
import java.net.MalformedURLException;

public class Program {
    private static final String FILE_NAME = "file.java";
    private static final String CLASS_NAME = "Formulas";

    public static void main(String[] args) throws InterruptedException {
        Compiler compiler = new Compiler(CLASS_NAME,FILE_NAME);
        Calculator calculator = new Calculator(compiler);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    calculator.compile();
                } catch (InterruptedException | MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    calculator.calc();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
