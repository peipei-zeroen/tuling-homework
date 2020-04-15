package org.zeroen.tuling.homework.algorithm.sort;

import java.util.Random;

/**
 * @Author
 * @Description
 * @Date Created in 0:14 2018/12/12
 * @Modified By：
 */
public class ArrayUtils {

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static int[] buildRandArray(int len) {
        return buildRandArray(len, 0, 1000);
    }

    public static int[] buildRandArray(int len, int max) {
        return buildRandArray(len, 0, max);
    }

    public static int[] buildRandArray(int len, int min, int max) {
        int[] arr = new int[len];
        final Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = min + random.nextInt(max + 1);
        }
        return arr;
    }
}
