package lee;

import java.util.Arrays;
import java.util.HashMap;

public class Q646 {

    public static void main(String[] args) {
        Q646 q = new Q646();
        q.findLongestChain(new int[][]{{1,2}, {4,5}, {2,3}, {3,5}, {3,4}, {5,6}});
    }

    public int findLongestChain(int[][] pairs) {
        int[] dp = new int[pairs.length];
        dp[0] = pairs[0][1];
        int index = 0;

        Arrays.sort(pairs, (a,b)->a[1] - b[1]);
        HashMap<Integer, Integer> map = new HashMap<>();


        for(int i = 1; i < pairs.length; i++){
            if(pairs[i][0] > dp[index]){
                dp[++index] = pairs[i][1];
            }
            else if(pairs[i][1] < dp[index]){
                System.out.println(dp[findInsert(dp, pairs[i][1], 0, index)] = pairs[i][1]);
            }
        }

        return index + 1;
    }

    private int findInsert(int[] dp, int num, int start, int end){
        if(start >= end){
            return start;
        }

        int mid = (start + end)/2;
        if(dp[mid] > num){
            if(mid == 0 || dp[mid-1] <= num){
                return mid;
            }
            return findInsert(dp, num, start, mid - 1);
        } else if(dp[mid] < num) {
            return findInsert(dp, num, mid+1, end);
        } else {
            return mid+1;
        }
    }
}
