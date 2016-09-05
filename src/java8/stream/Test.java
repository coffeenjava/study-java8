package java8.stream;

import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args) {
        Test t = new Test();
        t.test();
    }
    
    void test() {
        int a = 4;
        IntStream.rangeClosed(1, 100)
                .filter(b-> Math.sqrt(a*a+b*b)%1 == 0)
                .boxed()
                .map(b -> new int[] {a,b, (int)Math.sqrt(a*a+b*b)});
        
        IntStream.rangeClosed(1, 100)
                .filter(b-> Math.sqrt(a*a+b*b)%1 == 0)
                .mapToObj(b -> new int[] {a,b, (int)Math.sqrt(a*a+b*b)});
    }
}
