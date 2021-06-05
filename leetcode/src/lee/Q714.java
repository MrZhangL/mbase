package lee;

public class Q714 {

    public static void main(String[] args) {
        Q714 q = new Q714();
        System.out.println(q.maxProfit(new int[]{1, 3, 2, 8, 4, 9}, 2));
    }

    public int maxProfit(int[] prices, int fee) {
        int[] dp = new int[4];  // 0：当天买入，1：当天持有，2：当天卖出，3：当天不持有
        int[] bk = new int[4];
        for(int i = 1; i < dp.length; i++) {
            dp[0] = Math.max(bk[2], bk[3]);
            dp[1] = Math.max(bk[0], bk[1]) + prices[i] - prices[i-1];
            dp[2] = Math.max(bk[0], bk[1]) + prices[i] - prices[i-1] - fee;
            dp[3] = dp[0];

            for(int j = 0; j < dp.length; j++) {
                bk[j] = dp[j];
            }
        }

        return Math.max(dp[2], dp[3]);
    }
}
