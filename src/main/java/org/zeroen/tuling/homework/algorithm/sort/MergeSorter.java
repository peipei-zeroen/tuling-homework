package org.zeroen.tuling.homework.algorithm.sort;

import static org.zeroen.tuling.homework.algorithm.sort.ArrayUtils.*;
/**
 * 归并排序
 */
public class MergeSorter implements Sorter {

    @Override
    public void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        sort(arr, 0, arr.length - 1);
    }

    private void sort(int[] arr, int start, int end) {
        if (end - start <= 0) { //1个元素
            return;
        }

        if (end - start == 1) { //2个元素
            if (arr[end] < arr[start]) {
                swap(arr, start, end);
            }
            return;
        }

        int mid = (start + end) / 2;
        sort(arr, start, mid);
        sort(arr, mid + 1, end);
        merge(arr, start, mid, end);
    }

    private void merge(int[] arr, int start, int mid, int end) {
        int rightIndex = mid + 1;
        while (start <= mid && rightIndex <= end) {
            if (arr[start] > arr[rightIndex]) {
                int tmp = arr[rightIndex];
                for (int i = mid; i >= start; i--)
                    arr[i + 1] = arr[i];
                arr[start] = tmp;
                mid++;
                rightIndex++;
            }
            start++;
        }
    }
}
