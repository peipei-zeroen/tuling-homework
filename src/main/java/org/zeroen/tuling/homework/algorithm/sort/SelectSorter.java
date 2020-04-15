package org.zeroen.tuling.homework.algorithm.sort;

import static org.zeroen.tuling.homework.algorithm.sort.ArrayUtils.*;
/**
 * 选择排序
 */
public class SelectSorter implements Sorter {

    @Override
    public void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            if (i != minIndex) {
                swap(arr, i, minIndex);
            }
        }
    }
}
