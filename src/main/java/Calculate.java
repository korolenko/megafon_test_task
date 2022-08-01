import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Calculate {

    public static void main(String[] args) {
        try {
            FileHelper.isFileChanged("file.txt");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static Map<String,Integer> generateFlow(){
        Map<String,Integer> flow = new HashMap<String, Integer>(Arguments.values().length);
        Random r = new Random();
        int low = -100;
        int high = 100;
        for(Arguments arg:Arguments.values()){
            int argValue = r.nextInt(high-low) + low;
            flow.put(arg.name(),argValue);
        }
        return flow;
    }

    private enum Arguments {
     A,B,C,D,E,F
    }
}
