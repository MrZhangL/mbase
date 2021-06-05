package huawei;

import java.util.Scanner;

public class main3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] params = new int[n];

        for(int i = 0; i < n; i ++) {
            params[i] = sc.nextInt();
        }

        int maxPos = 0;
        int end = 0;
        int res = 0;

        int i = 0;
        while(i < n - 1) {
            if(maxPos >= i) {
                maxPos = Math.max(maxPos,i + params[i]);
                if(maxPos >= n - 1) {
                    res++;
                    break;
                }
                if(i == end) {
                    end = maxPos;
                    res++;
                }

            }
        }

        System.out.println(res);

    }

}
