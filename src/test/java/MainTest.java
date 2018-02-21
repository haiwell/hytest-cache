import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Administrator on 2018/2/11.
 */
public class MainTest {

    public static final Random rn = new Random();

    @Test
    public void test1() {
        List<String> list = new ArrayList<>();
        for (String s : list) {
            System.out.println(s);
        }
    }

    static int random(int n) {
        return Math.abs(rn.nextInt()) % n;
    }

    @Test
    public void test2() {
        int n = 2 * (Integer.MAX_VALUE/3);
        int low = 0;
        for (int i = 0 ; i < 1000000 ; i++) {
            if (random(n) <= n/2) {
                low ++;
            }
        }
        System.out.println(low);
    }

    @Test
    public void test3() {
        /**
         * Integer.MIN_VALUE取绝对值还是本身
         * 原因：int范围是-2147483648 : 2147483647
         * 2147483648超出了int表示的范围了
         *
         */
        int n = Math.abs(Integer.MIN_VALUE);
        System.out.println(n);
        System.out.println(Integer.MIN_VALUE < 0);
        System.out.println(-1 * Integer.MIN_VALUE);
        System.out.println(Integer.MIN_VALUE + " : " + Integer.MAX_VALUE);
        Long l =Long.parseLong(String.valueOf(-1 * Integer.MIN_VALUE));
        System.out.println(Long.MIN_VALUE + " : " + Long.MAX_VALUE);

    }


    @Test
    public void test4() {
       int i = 0;
       int k = 0;
       long startTime = System.currentTimeMillis();
       while (true) {
           i++;
           long endTime = System.currentTimeMillis();
           if (endTime - startTime == 1000) {
               k++;
               System.out.println("结果 : " + i + " 次数 : "+k);
               break;
           }
       }
    }


    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        String s = sc.next();
        long l = Long.parseLong(s);
        long k = l / 2;
        long startTime = System.currentTimeMillis();
        for (long i=0;i < l;i ++ ) {
            if (i == k) {
                long endTime = System.currentTimeMillis();
                System.out.println("结果 ： " + i + "时间 ：" + (endTime - startTime));
            }
        }
    }

}
