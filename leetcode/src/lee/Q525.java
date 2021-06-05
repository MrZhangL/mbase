package lee;

import java.util.Arrays;

public class Q525 {

    public static void main(String[] args) {
        Q525 q = new Q525();
        System.out.println(q.findMaxLength(new int[]{0,1,0,1,1,1,0,0,1,1,0,1,1,1,1,1,1,0,1,1,0,1,1,0,0,0,1,0,1,0,0,1,0,1,1,1,1,1,1,0,0,0,0,1,0,0,0,1,1,1,0,1,0,0,1,1,1,1,1,0,0,1,1,1,1,0,0,1,0,1,1,0,0,0,0,0,0,1,0,1,0,1,1,0,0,1,1,0,1,1,1,1,0,1,1,0,0,0,1,1}));
    }

   /* public int findMaxLength(int[] nums) {
        int[] arr = new int[2 * nums.length + 1];
        Arrays.fill(arr, -2);
        arr[nums.length] = -1;
        int maxlen = 0, count = 0;

        int l = 0, r = 0;

        for (int i = 0; i < nums.length; i++) {
            count = count + (nums[i] == 0 ? -1 : 1);
            if (arr[count + nums.length] >= -1) {
                int cntmx = i - arr[count + nums.length];
                if(maxlen < cntmx) {
                    maxlen = cntmx;
                    l = arr[count + nums.length];
                    r = i;
                }
            } else {
                arr[count + nums.length] = i;
            }

        }
        return maxlen;
    }*/


    public int findMaxLength(int[] nums) {
        int[] pre0 = new int[nums.length + 1];

        for(int i = 0; i < nums.length; i++) {
            pre0[i + 1] = pre0[i] + (nums[i] == 0 ? 1 : 0);
        }

        int l = 0, r = 0;
        int max = 0;

        while(r < nums.length) {
            int z_num = pre0[r + 1] - pre0[l];  // 区间内0的数量
            int o_num = r - l + 1 - z_num;      // 区间内1的数量

            int z_remain = pre0[nums.length] - pre0[r + 1];     // 后面剩余0的数量
            int o_remain = nums.length - 1 - r - z_remain;          // 后面剩余1的数量

            int z_b = z_num - o_num;
            int o_b = z_remain - o_remain;

            if(z_b == 0) max = Math.max(r - l + 1, max);

            if(z_b == 0 && o_b == 0) {
                max = Math.max(nums.length - l, max);
                break;
            }
            else if(z_b > o_remain || (-z_b) > z_remain) { // 多出来的0比后面剩余的1还多 或 多出来的1比后面剩余的0还多
                l++;
            } else {
                r++;
            }
        }

        return max;
    }
}
