package lee;

public class Q673 {

    public static void main(String[] args) {
        Q673 q = new Q673();
        System.out.println(q.findNumberOfLIS(new int[]{1, 2, 5, 4, 7, 6, 7}));
    }

    public int findNumberOfLIS(int[] nums) {
        if(nums.length == 0) return 0;
        int[][] dp = new int[nums.length][2];

        dp[0][0] = 1;
        dp[0][1] = 1;
        int max = 1;

        for(int i = 1; i < dp.length; i++) {
            dp[i][0] = 1;
            dp[i][1] = 1;
            for(int j = i - 1; j >= 0; j--) {
                if(nums[i] > nums[j]) {
                    if(dp[i][0] == dp[j][0] + 1) {
                        dp[i][1] += dp[j][1];
                    } else if(dp[i][0] <= dp[j][0]) {
                        dp[i][0] = dp[j][0] + 1;
                        dp[i][1] = dp[j][1];
                    }
                }
            }

            max = Math.max(dp[i][0], max);
        }

        int ans = 0;
        for(int i = 0; i < dp.length; i++) {
            ans += dp[i][0] == max? dp[i][1] : 0;
        }

        return ans;

    }
}
