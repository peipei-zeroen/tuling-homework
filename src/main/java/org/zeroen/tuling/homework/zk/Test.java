package org.zeroen.tuling.homework.zk;

import java.util.HashMap;
import java.util.Stack;

/**
 * @Author
 * @Description
 * @Date Created in 0:14 2018/11/30
 * @Modified By：
 */
public class Test {

    public static void main(String[] args) {
        /*HashMap<Integer, Integer> map = new HashMap<>(13);
        map.put(1, 1);
        for (int i = -1024; i <= 2048; i++) {
            if (isPowerOfTwo(i)) {
                System.out.println(String.valueOf(i));
            }
        }*/

        int m = 5;
        Num c = new Num(0), i = new Num(0), curr = new Num(0), last = new Num(0);

        findLessOfTarget(new int[]{1,2,4,5,6,7,8,9}, i,  c, last, curr, m,3);

        System.out.println("c = " + c.val + ", i = " + i.val + ", curr = " + curr.val + ", last = " + last.val);
    }

    static void findLessOfTarget(int[] nums, Num i, Num c, Num last, Num curr, int m, int target) {
        for (;c.val < m && i.val < nums.length && nums[i.val] <= target;i.val++) {
            c.val++;
            last.val = curr.val;
            curr.val = nums[i.val];
        }
        Stack<Character> stack = new Stack<>();
        System.out.println("c = " + c.val + ", i = " + i.val + ", curr = " + curr.val + ", last = " + last.val);
        System.out.println();
    }

    static class Num {
        int val;
        Num(int val) {
            this.val = val;
        }
    }

    static boolean isPowerOfTwo(int i) {
        return (i & -i) == i;
    }

    static boolean isJishu(int i) {
        return (i & 1) == 1;
    }
}
