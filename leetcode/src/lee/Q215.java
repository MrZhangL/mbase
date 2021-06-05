package lee;

import java.util.Random;

public class Q215 {
    public static void main(String[] args) {
        Q215 q = new Q215();
        System.out.println(q.findKthLargest(new int[]{3,2,3,1,2,4,5,5,6}, 1));
    }

    public int findKthLargest(int[] nums, int k) {
        return findK(nums, nums.length - k, 0, nums.length - 1);
    }

    int findK(int[] nums, int k, int left, int right) {
        if(left == right) return nums[left];
        int l = left + 1;
        int r = right;
        new Random().nextInt();
        while(l < r) {
            while(l < r && nums[l] <= nums[left]) l++;
            while(l < r && nums[r] > nums[left]) r--;

            swap(nums, l ,r);
        }

        if(nums[l] > nums[left]) l--;
        swap(nums, left, l);

        if(l == k) return nums[l];
        else if(l < k) return findK(nums, k, l + 1, right);
        else return findK(nums, k, left, l - 1);
    }

    void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
