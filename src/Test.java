import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<String,Object> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        
        Map<String,Object> m2 = new HashMap<>();
        m2.put("a", 4);
        m2.put("c", 3);
        
//        m.putAll(m2);
        
        List l = new ArrayList();
        
        l.add(m);
        l.add(m2);
        
        Collections.reverse(l);
        
        System.out.println(l);
    }
}
