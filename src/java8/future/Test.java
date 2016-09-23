package java8.future;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test {
    public static void main(String[] args) {
//        Test.Code c = Test.Code.values()[0];
//        System.out.println(c);
//        Arrays.stream(Test.Code.values()).forEach(System.out::println);
        
        Test t = new Test();
        t.test();
    }
    
    public Future<Double> getPriceAsync(String p) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(p));
    }
    
    private double calculatePrice(String p) {
        return 2.0d;
    }
    
    void test() {
        System.out.println(Code.valueOf("SILVER"));
//        Executors.newFixedThreadPool(nThreads, threadFactory)
    }
    
    public enum Code {
        SILVER(5), GOLD(10), DIAMOND(20);
        
        private final int percentage;
        
        Code(int percentage) {
            this.percentage = percentage;
        }
    }
}
