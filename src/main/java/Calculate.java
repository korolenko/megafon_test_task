import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Calculate {

    public static void main(String[] args) {
        System.out.println(generateFlow());
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

    public enum Arguments {
     A,B,C,D,E,F
    }
}
