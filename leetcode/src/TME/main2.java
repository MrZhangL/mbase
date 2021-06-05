package TME;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class main2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt();

        for(int j = 0; j < t; j++) {
            int n = sc.nextInt();
            Map<Integer,Integer> map = new HashMap<>();

            for(int i = 0; i < n; i++){
                int k = sc.nextInt();
                map.put(k,map.getOrDefault(k,0) + 1);
            }

            int res = Integer.MAX_VALUE;
            for(Map.Entry entry : map.entrySet()) {
                if((int)entry.getValue() <= 1) {
                    res = Math.min(res,(int)entry.getKey());
                }
            }
            if(res == Integer.MAX_VALUE) {
                System.out.println(-1);
            } else {
                System.out.println(res);
            }

        }
    }
}
