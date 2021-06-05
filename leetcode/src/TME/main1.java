package TME;

import java.util.*;

public class main1 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }

        PriorityQueue<int[]> queue = new PriorityQueue<>((p1,p2)->{
            if(p1[0] == p2[0]) return p2[1] - p1[1];
            return p1[0] - p2[0];
        });

        for(int i = 0; i < n; i++){
            queue.offer(new int[]{a[i],sc.nextInt()});
        }

        int cost = 0;
        Set<Integer> set = new HashSet<>();

        while(!queue.isEmpty()) {
            int[] poll = queue.poll();
            if(set.contains(poll[0])) {
                poll[0] += 1;
                cost += poll[1];
                queue.offer(poll);
            }else {
                set.add(poll[0]);
            }
        }

        System.out.println(cost);



    }
}
