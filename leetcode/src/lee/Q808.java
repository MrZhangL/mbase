package lee;

public class Q808 {

    public static void main(String[] args) {
        Q808 q = new Q808();
        q.soupServings(50);
    }

    int[][] dirs = new int[][]{{4,0},{3,1},{2,2},{1,3}};

    public double soupServings(int N) {
        N = N/25 + N%25 > 0? 1 : 0;
        double[][][] dp = new double[N][N][2];

        dp[0][0][1] = 1;
        for(int i = 1; i < N; i++) {
            dp[0][i][0] = 1;
        }
        for(int i = 1; i < N; i++) {
            for(int j = 1; j < N; j++) {
                for(int[] dir : dirs) {
                    int nr = i - dir[0];
                    int nc = j - dir[1];
                    nr = nr >= 0? nr : 0;
                    nc = nc >= 0? nc : 0;

                    dp[i][j][0] += dp[nr][nc][0];
                    dp[i][j][1] += dp[nr][nc][1];
                }

                dp[i][j][0] /= 4;
                dp[i][j][1] /= 4;
            }
        }

        return dp[N-1][N-1][0] + dp[N-1][N-1][1]/2;
    }
}
