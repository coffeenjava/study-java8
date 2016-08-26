package java8.stream;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Test {
    
    public static void main(String[] args) {
        Test t = new Test();
        List<Dish> dishes = t.getFirstTwoMeat();
//        System.out.println(dishes);
        
//        System.out.println(t.getNameLength());
        
//        t.getWordChar().stream().forEach(System.out::print);
//        t.getDoubleInt().stream().forEach(System.out::print);
//        t.getPairNum().stream().forEach(nums -> System.out.println(nums[0] + "," + nums[1]));
        t.getPairNum().stream().forEach(Test::printNumArr);
    }
    
    public static void printNumArr(int[] nums) {
        Arrays.asList(nums).stream()
            .forEach(System.out::print);
        System.out.println();
    }
    
    public List<int[]> getPairNum() {
        List<Integer> nums1 = Arrays.asList(1,2,3);
        List<Integer> nums2 = Arrays.asList(1,2,3);
        
        return nums1.stream()
            .flatMap(i -> nums2.stream().map(j -> new int[]{i,j})) // nums2.map() 은 중간연산인데 값이 저장되나?
            .collect(toList());
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
