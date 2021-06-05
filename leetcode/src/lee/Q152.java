package lee;

public class Q152 {
    public static void main(String[] args) {
        Q152 q =new Q152();
        System.out.println(q.maxProduct(new int[]{2, 3, -2, 4}));
    }

    public int maxProduct(int[] nums) {
        int[][] dp = new int[nums.length][2];
        dp[0][0] = nums[0];
        dp[0][1] = nums[0];

        int max = Integer.MIN_VALUE;
        for(int i = 1; i < nums.length; i++) {
            dp[i][0] = Math.max(nums[i], Math.max(dp[i-1][0]*nums[i], dp[0][1]*nums[i]));
            dp[i][1] = Math.min(nums[i], Math.min(dp[i-1][0]*nums[i], dp[0][1]*nums[i]));
            max = Math.max(dp[i][0], max);
        }


        return max;
    }
}
