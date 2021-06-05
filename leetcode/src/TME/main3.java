package TME;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class main3 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for(int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }

        int[] b = new int[n];
        for(int i = 0; i < n; i++) {
            b[i] = sc.nextInt();
        }

        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();

        int max = 0;
        for(int i = 0; i < n; i++) {
            max = Math.max(max,a[i]);
            if (map.containsKey(a[i])) {
                map.get(a[i]).add(b[i]);
            }else {
                PriorityQueue<Integer> queue = new PriorityQueue<>();
                queue.add(b[i]);
                map.put(a[i],queue);
            }
        }

        int cost = 0;
        for(int i = 1; i <= max; i++) {
            //包含且数目大于2，更新代价。否则跳过
            if(map.containsKey(i) && map.get(i).size() >= 2){
                int size = map.get(i).size() - 1;
                for(int j = 0; j < size; j++) {
                    int k = map.get(i).poll();
                    cost += k;

                    if (map.containsKey(i + 1)) {
                        map.get(i + 1).add(k);
                    }else {
                        PriorityQueue<Integer> queue = new PriorityQueue<>();
                        queue.add(k);
                        map.put(i + 1,queue);
                    }
                }
            }
        }
        System.out.println(cost);


    }
}
