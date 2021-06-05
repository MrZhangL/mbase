package lee;

public class Q115 {

    public static void main(String[] args) {
        Q115 q = new Q115();
        q.numDistinct("rabbbit", "rabbit");
    }

    public int numDistinct(String s, String t) {
        int[][] dp = new int[s.length()][t.length() + 1];

        for(int i = 0; i < dp.length; i++) {
            dp[i][0] = 1;
        }
        dp[0][1] = s.charAt(0) == s.charAt(0)? 1 : 0;

        for(int i = 1; i < dp.length; i++) {
            for(int j = 1; j < dp[0].length; j++) {
                if(s.charAt(i) == t.charAt(j-1)) dp[i][j] = dp[i-1][j-1] + dp[i-1][j];
                else dp[i][j] = dp[i-1][j];
            }
        }

        return dp[dp.length-1][dp[0].length - 1];
    }
}
