package java8.stream.parallel;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class WordCounterTest {
    public static void main(String[] args) {
        String SENTENCE = "this method has to return an object of that type";
        
//        Stream<Character> stream = IntStream.range(0, SENTENCE.length())
//                                            .mapToObj(SENTENCE::charAt);
        Stream<Character> stream = IntStream.range(0, SENTENCE.length())
                .mapToObj(SENTENCE::charAt);
        stream.forEach(System.out::println);
    }
}
