package lee;

import java.util.LinkedList;
import java.util.List;

public class Q1574 {

    public static void main(String[] args) {
        Q1574 q = new Q1574();
        System.out.println(q.findLengthOfShortestSubarray(new int[]{1, 2, 3, 10, 4, 2, 3, 5}));
    }

    public int findLengthOfShortestSubarray(int[] arr) {
        int l = -1, r = -1;
        for(int i = 0; i < arr.length - 1; i++) {
            if(arr[i] > arr[i+1]) {
                l = i;
                break;
            }
        }

        if(l == -1) {
            return arr.length;
        }

        for(int i = arr.length-1; i > 0; i--) {
            if(arr[i - 1] > arr[i]) {
                r = i;
                break;
            }
        }

        List<Integer> ls = new LinkedList<>();

        // 删中间
        int rr = r;
        int max = 0;
        for(int i = l; i >= 0; i--) {
            for(int j = rr; j < arr.length; j++ ) {
                if(arr[j] >= arr[i]) {
                    rr = j;
                    max = Math.max(l+1+arr.length-rr-1, max);
                    break;
                }
            }
            if(arr[rr] >= arr[i] && rr == r) {
                break;
            }
        }

        return arr.length - max;

    }
}
