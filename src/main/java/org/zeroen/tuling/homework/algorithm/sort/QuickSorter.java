package org.zeroen.tuling.homework.algorithm.sort;

import java.util.Arrays;

import static org.zeroen.tuling.homework.algorithm.sort.ArrayUtils.*;

/**
 * @Author
 * @Description
 * @Date Created in 0:52 2018/12/14
 * @Modified By：
 */
public class QuickSorter implements Sorter {

    @Override
    public void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        //sortHelperOfBothLeft(arr, 0, arr.length - 1);
        //sortHelperOfLeftRight(arr, 0, arr.length - 1);
        sortHelperOfThreePointer(arr, 0, arr.length - 1);
    }

    /**
     * 选择第一个元素作为中轴数(pivot)，维护两个指针i,j
     * i表示start~(i-1)之间所有的元素都是小于中轴数(pivot)的 此为递归的不变性
     * 而遇到>=中轴数(pivot)的数的时候，i与j交换，i向右移 此时也保证了不变性
     * j就是遍历start~end的指针
     * @param arr
     * @param start
     * @param end
     */
    private void sortHelperOfBothLeft(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }
        int index = selectFirst(arr, start, end);
        int pivot = arr[index];

        int i, j;
        i = j = index + 1;

        while (j <= end) {
           if (arr[j] <= pivot) {
               swap(arr, i++, j);
           }
           j++;
        }
        swap(arr, index, i - 1);

        sortHelperOfBothLeft(arr, start, i - 2);
        sortHelperOfBothLeft(arr, i, end);
    }

    /**
     * 维护两个指针，一个left从左边开始，一个right从右边开始
     * 首先从右边开始，遇到比中轴数(pivot)大的前向左边前进1
     * 然后从右边开始，遇到比中轴数(pivot)小的前向右边前进1
     * 直到两个指针相遇
     * @param arr
     * @param start
     * @param end
     */
    private void sortHelperOfLeftRight(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }
        int index = selectFirst(arr, start, end);
        int pivot = arr[index];

        int left = index;
        int right = end;
        while (left < right) {
            while (left < right && arr[right] > pivot)
                right--;
            arr[left] = arr[right];
            while (left < right && arr[left] <= pivot)
                left++;
            arr[right] = arr[left];
        }
        arr[left] = pivot;
        //System.out.println(Arrays.toString(arr));
        sortHelperOfLeftRight(arr, start, left - 1);
        sortHelperOfLeftRight(arr, left + 1, end);
    }

    /**
     * 三指针
     * @param arr
     * @param start
     * @param end
     */
    private void sortHelperOfThreePointer(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }
        int index = selectFirst(arr, start, end);
        int pivot = arr[index];

        int lt = index;
        int gt = end;
        int i = index + 1;

        while (i <= gt) {
            if (arr[i] < pivot) {
                swap(arr, lt++, i++);
            } else if (arr[i] > pivot) {
                swap(arr, i, gt--);
            } else {
                i++;
            }
        }

        sortHelperOfThreePointer(arr, start, lt - 1);
        sortHelperOfThreePointer(arr, gt + 1, end);
    }

    public static void rotate(int[] nums, int k) {
        final int len;
        if (nums == null || (len = nums.length) <= 1) {
            return;
        }

        System.out.println(Arrays.toString(nums));
        System.out.println("==================================");
        for (int c = 1; c <= k; c++) {
            int last = nums[len - 1];
            for (int i = len - 1;i < 0;i--) {
                nums[i] = nums[i - 1];
            }
            nums[0] = last;
            System.out.println(Arrays.toString(nums));
        }
    }

    public static void main(String[] args) {
        rotate(new int[]{1,2,3,4,5,6,7},3);
    }

    private int selectFirst(int[] arr, int start, int end) {
        return start;
    }
}
