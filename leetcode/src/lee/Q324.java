package lee;

import java.util.Arrays;
import java.util.Random;

public class Q324 {

    public static void main(String[] args) {
        Q324 q = new Q324();
        int[] nums = new int[]{4,5,5,6};
        q.wiggleSort(nums);

        Arrays.stream(nums).forEach(n-> System.out.print(n + ","));
    }

    public void wiggleSort(int[] nums) {
        if(nums.length == 1) return;
        // 找中位数
        int half = (nums.length - 1)/2;

        int mid = quickSel(nums, 0, nums.length - 1, half);
        int[] tmp = new int[nums.length];

        int st = 0;
        for(int i = half; i >= st;) {
            if(nums[i] == mid) swap(nums, i, st++);
            if(nums[i] != mid) i--;
        }

        st = nums.length - 1;
        for(int i = half + 1; i <= st;) {
            if(nums[i] == mid) swap(nums, i, st--);
            if(nums[i] != mid) i++;
        }

        for(int i = 0; i <= half; i++) {
            tmp[2*i] = nums[i];
            if(half + i + 1< nums.length)
                tmp[2*i+1] = nums[half + i + 1];
        }

        for (int i = 0; i < nums.length; i++) {
            nums[i] = tmp[i];
        }
    }

    Random r = new Random();
    int quickSel(int[] nums, int left, int right, int index) {
        if(left >= right) return nums[left];
        int idx = left + r.nextInt(right - left + 1);

        swap(nums, idx, left);
        int l = left + 1;
        int r = right;

        while(l < r) {
            while(l < r && nums[l] <= nums[left]) l++;
            while(l < r && nums[r] > nums[left]) r--;

            swap(nums, l, r);
        }

        if(nums[l] > nums[left]) l--;
        swap(nums, l, left);

        if(l == index) return nums[l];
        else if(l < index) return quickSel(nums, l + 1, right, index);
        else return quickSel(nums, left, l - 1, index);
    }

    void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
