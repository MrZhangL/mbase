package lee;

public class Q410 {

    public static void main(String[] args) {
        Q410 q = new Q410();
        System.out.println(q.splitArray(new int[]{7, 2, 5, 10, 8}, 3));
    }

    /*public int splitArray(int[] nums, int m) {
        int[] presum = new int[nums.length + 1];
        int[][] dp = new int[nums.length][m];



        for(int i = 1; i < presum.length; i++) {
            presum[i] = presum[i-1] + nums[i-1];
            dp[i-1][0] = presum[i];
        }
        dp[0][0] = nums[0];

        for(int j = 1; j < m; j++) {
            for(int i = j; i < nums.length; i++) {
                dp[i][j] = Integer.MAX_VALUE;
                for(int k = i - 1; k >= j-1; k--) {
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[k][j-1], presum[i+1] - presum[k]));
                }
            }
        }

        return dp[nums.length-1][m-1];
    }*/

    public int splitArray(int[] nums, int m) {
        int l = 0, r = 0;
        for(int i = 0; i < nums.length; i++) {
            r += nums[i];
        }

        int mid;
        while(l < r) {
            mid = (l+r) / 2;
            if(check(nums, mid, m)) {
                r = mid;
            }
            else{
                l = mid + 1;
            }
        }

        return l;
    }

    private boolean check(int[] nums, int up, int m) {
        int cnt = 1;
        int sum = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] > up) return false;

            if(sum + nums[i] > up) {
                cnt++;
                if(cnt > m) {
                    return false;
                }
                sum = nums[i];
            } else {
                sum += nums[i];
            }

        }


        return true;
    }
}
