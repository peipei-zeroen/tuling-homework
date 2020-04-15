package org.zeroen.tuling.homework.zk;

import java.util.*;

/**
 * @Author
 * @Description
 * @Date Created in 2:15 2018/12/2
 * @Modified By：
 */
public class Solution {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        final int len = nums1.length + nums2.length;
        boolean odd = (len & 1) == 1;
        int m = len / 2 + 1;
        boolean nf1 = nums1.length == 0;
        boolean nf2 = nums2.length == 0;
        Num c = new Num(0), i1 = new Num(0), i2 = new Num(0), curr = new Num(0), last = new Num(0);
        while (c.val < m) {
            if (nf1) {
                findMedianSortedOfSingleArray(nums2, i2.val, c.val, m, last, curr);
                break;
            }

            if (nf2) {
                findMedianSortedOfSingleArray(nums1, i1.val, c.val, m, last, curr);
                break;
            }

            if (nums1[i1.val] < nums2[i2.val]) {
                findLessOfTarget(nums1, i1, c, last, curr, m, nums2[i2.val]);
                if (i1.val > nums1.length - 1) {
                    nf1 = true;
                }
            } else {
                findLessOfTarget(nums2, i2, c, last, curr, m, nums1[i1.val]);
                if (i2.val > nums2.length - 1) {
                    nf2 = true;
                }
            }
        }
        return odd ? curr.val : (last.val + curr.val) / 2.0;
    }

    private void findMedianSortedOfSingleArray(int[] nums, int i, int c, int m, Num last, Num curr) {
        int t = m - c;
        int j = i - 1 + t;
        if (t > 1) {
            last.val = nums[j - 1];
        } else {
            last.val = curr.val;
        }
        curr.val = nums[j];
    }

    private void findLessOfTarget(int[] nums, Num i, Num c, Num last, Num curr, int m, int target) {
        for (;c.val < m && i.val < nums.length && nums[i.val] <= target;i.val++) {
            c.val++;
            last.val = curr.val;
            curr.val = nums[i.val];
        }
    }

    static class Num {
        int val;
        Num(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        //Solution s = new Solution();
        //System.out.println(s.findMedianSortedArrays(new int[]{1,2}, new int[]{3,4}));
        System.out.println(lengthOfLongestSubstring("aab"));
    }

    public static int lengthOfLongestSubstring(String s) {
        int start = 0;
        StringBuilder sb = new StringBuilder();
        int last = 0;
        for (int i = 0;i < s.length();i++) {
            char c = s.charAt(i);
            //int len = Math.max(map.size(), last);
            /*if (list.contains(c)) {

            }
            if (map.containsKey(c)) {
                if (len >= s.length() - i) {
                    break;
                }
                map.clear();
                last = len;
            }
            map.put(c, i);*/
        }
        //return Math.max(set.size(), last);
        return 0;
    }
}
