package lee;

public class Q837 {

    public static void main(String[] args) {
        Q837 q = new Q837();
        System.out.println(q.new21Game(6, 1, 10));
    }

    public double new21Game(int N, int K, int W) {
        int[] dp = new int[W+K];
        int sum = 0;
        if(N >= K+W-1) return 1;

        for(int i = N + 1; i < K+W; i++) dp[i] = 0;
        for(int i = K; i <= N; i++) {
            dp[i] = 1;
            if(i - K < W) sum += 1;
        }

        for(int i = K-1; i >= 0; i--) {
            if(N+K-1-i <= W) {
                dp[i] = sum/W;
            } else {
                dp[i] = (sum + dp[i+1] - dp[i+W+1])/W;
            }
        }

        return dp[0];
    }

}
