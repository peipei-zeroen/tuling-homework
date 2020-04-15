package org.zeroen.homework;

import org.junit.Assert;
import org.junit.Test;
import org.zeroen.tuling.homework.algorithm.sort.*;

import java.util.Arrays;

import static org.zeroen.tuling.homework.algorithm.sort.ArrayUtils.*;

/**
 * @Author
 * @Description
 * @Date Created in 0:21 2018/12/12
 * @Modified By：
 */
public class SortAlgorithmTest {

    @Test
    public void test() {
        int[] arr = buildRandArray(100);
        //int[] arr = new int[]{32, 95, 58, 57, 87, 94, 0, 71, 65, 57};
        System.out.println(Arrays.toString(arr));
        //System.out.println("------------------------------------------------");
        int[] tmp = Arrays.copyOf(arr, arr.length);
        Arrays.sort(tmp);

        Sorter sorter = new QuickSorter();
        sorter.sort(arr);
        //System.out.println("------------------------------------------------");
        System.out.println(Arrays.toString(arr));
        Assert.assertArrayEquals(arr, tmp);
    }

    @Test
    public void test2() {
        int[] arr = new int[]{1,2,3,4,5,6,7,8,9};
        System.out.println(Arrays.toString(arr));
        int i = 0;
        arr[i++] = arr[i+7];
        System.out.println(Arrays.toString(arr));
    }
}
