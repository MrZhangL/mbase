package compete;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int k = scanner.nextInt();

        int[][] dp = new int[n+2][k+2];

        dp[1][1] = 1;

        for (int i = 2; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                dp[i][j] = (dp[i-1][j]*j + dp[i-1][j-1]*(i-j))%2017;
            }
        }

        System.out.println(dp[n + 1][k + 1]);
    }
}
