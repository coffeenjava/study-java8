package java8.stream;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DishTest {
    
    public static void main(String[] args) {
        DishTest t = new DishTest();
//        List<Dish> dishes = t.getFirstTwoMeat();
//        System.out.println(dishes);
        
        t.countDish();
    }
    
    /**
     * 5-3
     */
    public void countDish() {
//        int cnt = Dish.menu.stream().count();
        
        int cnt = Dish.menu.parallelStream()//stream()
                .map(x->1)
                .reduce(0, (a,b) -> a+b);
        System.out.println(cnt);
    }
    
    public void storeData() {
        List<Integer> l = new ArrayList<>();
        Arrays.stream(new int[]{1,2,3})
            .forEach(i-> {
                if (i>2) l.add(i);
            });
        
        System.out.println(l);
    }
    
    public static void printNumArr(int[] nums) {
        Arrays.stream(nums)
            .forEach(System.out::print);
        System.out.println();
    }
    
    /**
     * 5-2-3
     */
    public void pairNumFilter() {
        List<Integer> list1 = Arrays.asList(1,2,3);
        List<Integer> list2 = Arrays.asList(4,5);
        
//        list1.stream()
//            .flatMap(i->list2.stream().map(j->new int[]{i,j}))
//            .filter(n->(n[0]+n[1])%3==0)
//            .forEach(DishTest::printNumArr);
        list1.stream()
        .flatMap(i->
                    list2.stream()
                        .filter(j->(i+j)%3==0)
                        .map(j->new int[]{i,j})
                )
        .forEach(DishTest::printNumArr);
    }
    
    /**
     * 5-2-2
     */
    public void pairNum() {
        List<Integer> list1 = Arrays.asList(1,2,3);
        List<Integer> list2 = Arrays.asList(4,5);
        
//        list1.stream()
//             .flatMap(n1 -> list2.stream().map(n2-> new int[]{n1,n2}))
//             .forEach(DishTest::printNumArr);
        
        /**
         * map 을 사용하면 Stream([1,4][1,5]), Stream([2,4][2,5]), Stream([3,4][3,5])
         * 이렇게 세개의 스트림이 나온다.
         * flatMap 으로 각 스트림을 하나로 합쳐 int[] 스트림으로 만든다. 
         */
        list1.stream()
            .flatMap(n1 -> list2.stream().map(n2-> new int[]{n1,n2})) // 중간연산인데 map 의 결과(?)가 저장?
            .forEach(s-> {
                System.out.println(s);
            });
        
//        int[] arr = {1,2,3};
//        String[] sarr = {"a","b","c"};
//        
//        Arrays.asList(sarr).stream().forEach(i -> {
//            System.out.println(i);
//        });
    }
    
    public void flatStr() {
        List<String> list = Arrays.asList("hello","world");
        
        // map
//        List<String[]> result = list.stream()
//            .map(w->w.split(""))
//            .distinct()
//            .collect(toList());
        
//        result.stream().forEach(s -> System.out.println(Arrays.asList(s)));
        
        // flatMap
//        List<String> result = list.stream()
//                                    .map(w->w.split(""))
//                                    .flatMap(Arrays::stream)
//                                    .distinct()
//                                    .collect(toList());
//        result.stream().forEach(System.out::println);
        
        String[] array = {"h","e","l","l","o"};
        
        Arrays.stream(array).distinct().forEach(System.out::println);
    }
    
    /**
     * 5-2-1
     * @return
     */
    public List<Integer> getDoubleInt() {
        List<Integer> nums = Arrays.asList(1,2,3,4,5);
        return nums.stream()
            .map(n->n*n)
            .collect(toList());
    }
    
    public List<String> getWordChar() {
        String[] word = {"Hello","World"};
        
        return Arrays.asList(word).stream()
            .map(w -> w.split(""))
            .flatMap(Arrays::stream)
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
