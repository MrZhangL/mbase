package lee;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Q174 {

    public static void main(String[] args) {
        Q174 q = new Q174();
        System.out.println(q.calculateMinimumHP(new int[][]{{-2, -3, 3}, {-5, -10, 1}, {10, 30, -5}}));
    }

    public int calculateMinimumHP(int[][] dungeon) {
        int[][] dp = new int[dungeon.length][dungeon[0].length];
        for(int i = 0; i < dp.length; i++)
            Arrays.fill(dp[i], Integer.MAX_VALUE);

        int M = dungeon.length-1, N = dungeon[0].length-1;
        dp[M][N] = dungeon[M][N] > 0? 0 : -dungeon[M][N];
        for(int i = M;i>=0;i--) {
            for(int j = N; j>=0 ; j--) {
                if(i + 1 <= M) {
                    dp[i][j] = dp[i+1][j] - dungeon[i][j];
                }
                if(dp[i][j] < 0) dp[i][j] = 0;
                if(j + 1 <= N ) dp[i][j] = Math.min(dp[i][j], dp[i][j+1] - dungeon[i][j]);

                if(dp[i][j] < 0) dp[i][j] = 0;
            }
        }

        Scanner sc = new Scanner(System.in);


        return dp[0][0]+1;

    }
}
