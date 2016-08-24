package java8.stream;

import java.util.List;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;

public class Test {
    
    public static void main(String[] args) {
        Test t = new Test();
        List<Dish> dishes = t.getFirstTwoMeat();
//        System.out.println(dishes);
        
//        System.out.println(t.getNameLength());
        
        for (String[] s : t.getWordChar()) {
            for (String string : s) {
                System.out.print(string);
            }
            System.out.println();
        }
    }
    
    public List<String[]> getWordChar() {
        String[] word = {"Hello","World"};
        
        return Arrays.asList(word).stream()
            .map(w -> w.split(""))
            .distinct()
            .collect(toList());
    }
    
    public List<Integer> getNameLength() {
        return Dish.menu.stream()
            .map(d -> { 
//                System.out.println(d.getName());
                return d.getName().length();
            })
            .collect(toList());
    }
    
    /**
     * 5-1
     * @return
     */
    public List<Dish> getFirstTwoMeat() {
        return Dish.menu.stream()
                .filter(d -> d.getType() == Dish.Type.MEAT)
                .limit(2)
                .collect(toList());
    }
}
