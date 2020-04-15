package org.zeroen.tuling.homework.algorithm.sort;

import java.util.Arrays;

import static org.zeroen.tuling.homework.algorithm.sort.ArrayUtils.*;

/**
 * 冒泡排序
 */
public class BubbleSorter implements Sorter {

    @Override
    public void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        //bubbleLeft(arr);
        bubbleRight(arr);
    }

    private void bubbleLeft(int[] arr) {
        boolean flag;
        for (int i = 0; i < arr.length; i++) {
            flag = false;
            //从后往前迭代，总能使得将最小的数移至左边
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[j] < arr[j-1]) {
                    swap(arr, j-1, j);
                    flag = true;
                }
            }

            if (!flag)
                break;
        }
    }

    private void bubbleRight(int[] arr) {
        boolean flag;
        for (int i = arr.length - 1; i > -1; i--) {
            flag = false;
            //从前往后迭代，总能使得将最大的数移至右边
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j+1]) {
                    swap(arr, j, j+1);
                    flag = true;
                }
            }

            if (!flag)
                break;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5,6,7};
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, arr.length - 3, arr.length)));
    }
}
