package org.zeroen.tuling.homework.algorithm.sort;

import static org.zeroen.tuling.homework.algorithm.sort.ArrayUtils.*;
/**
 * 插入排序
 */
public class InsertSorter implements Sorter {

    @Override
    public void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            int tmp = arr[i];
            int j = i; //表示插入的位置
            while (j > 0 && tmp < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }

            if (j != i) {
                arr[j] = tmp;
            }
        }
    }
}
