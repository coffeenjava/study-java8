public class BinaryTest {
    public static void main(String[] args) {
        int num = 999;
        System.out.println(Integer.toBinaryString(num));
        
        BinaryTest t = new BinaryTest();
        
        int oneCnt = t.getOneCount(num);
        System.out.println(oneCnt);
        
        int distance = t.getDistanceBetweenOnes(num);
        System.out.println(distance);
    }
    
    
    int getOneCount(int num) {
        int cnt = 0;
        
        do {
           if ((num&1) == 1) {
               cnt++;
           }
        } while ((num >>>= 1) > 0);
        
        return cnt;
    }
    
    
    int getDistanceBetweenOnes(int num) {
        int max = 0;
        int current = -1;
        
        do {
            if ((num&1) == 1) {
                if (current > max) {
                    max = current;
                }
                current = 0;
            } else if (current != -1) {
                current++;
            }
        } while ((num >>>= 1) > 0);
        
        return max;
    }
}