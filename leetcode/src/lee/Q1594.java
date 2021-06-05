package lee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q1594 {

    public static void main(String[] args) {
        Q1594 q = new Q1594();
        //List<Integer> ls = Arrays.asList(2,4,5,1);

        //System.out.println(ls.stream().max((i1, i2) -> i1 - i2).get());
        //System.out.println(ls.stream().min((i1, i2) -> i1 - i2).get());
        System.out.println(q.maxProductPath(new int[][]{{-1,3,0},{3,-2,3},{-1,1,-4}}));
    }

    public int maxProductPath(int[][] grid) {
        long[][] dp1 = new long[grid.length][grid[0].length];    // 乘积的最大值
        long[][] dp2 = new long[dp1.length][dp1[0].length];          // 乘积的最小值

        dp1[0][0] = dp2[0][0] = grid[0][0];
        for(int i = 1; i < dp1.length; i++) {
            dp1[i][0] = dp2[i][0] = dp1[i-1][0]*grid[i][0];
        }
        for(int j = 1; j < dp1[0].length; j++) {
            dp1[0][j] = dp2[0][j] = dp1[0][j-1]*grid[0][j];
        }

        long m1, m2, m3, m4;
        for(int i = 1; i < dp1.length; i++) {
            for(int j = 1; j < dp1[0].length; j++) {
                m1 = grid[i][j] * dp1[i-1][j];
                m2 = grid[i][j] * dp2[i-1][j];
                m3 = grid[i][j] * dp1[i][j-1];
                m4 = grid[i][j] * dp2[i][j-1];
                dp1[i][j] = Math.max(m1, Math.max(m2, Math.max(m3, m4)));
                dp2[i][j] = Math.min(m1, Math.min(m2, Math.min(m3, m4)));
            }
        }
        int rt = (int) (dp1[grid.length-1][grid[0].length-1] % 1000000007);

        return rt < 0? -1 : rt;
    }
}
