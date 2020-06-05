package lee;

import java.util.Arrays;

public class Q1139 {

    public static void main(String[] args) {
        Q1139 q = new Q1139();

        System.out.println(q.largest1BorderedSquare(new int[][]{{0, 1, 1, 1, 1, 0}, {1, 1, 0, 1, 1, 0}, {1, 1, 0, 1, 0, 1}, {1, 1, 0, 1, 1, 1}, {1, 1, 0, 1, 1, 1}, {1, 1, 1, 1, 1, 1}, {1, 0, 1, 1, 1, 1}, {0, 0, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1}}));
    }

    public int largest1BorderedSquare(int[][] grid) {
        int[][][] dp = new int[grid.length+1][grid[0].length+1][2];
        int max = 0;
        int s = 0;
        boolean flag;

        for(int i = 1; i < dp.length; i++) {
            for(int j = 1; j < dp[0].length; j++) {
                if(grid[i-1][j-1] == 1) {
                    dp[i][j][0] = dp[i-1][j][0] + 1;
                    dp[i][j][1] = dp[i][j-1][1] + 1;


                    s = Math.min(dp[i][j][0], dp[i][j][1]);
                    while(dp[i-s+1][j][1] < s || dp[i][j-s+1][0] < s) {
                        s--;
                    }
                    max = Math.max(max, s*s);
                } else {
                    dp[i][j][0] = 0;
                    dp[i][j][1] = 0;
                }
            }
        }

        return max;

    }
}
