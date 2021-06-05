package lee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Q632 {

    public static void main(String[] args) {
        List<List<Integer>> l = new ArrayList<>();
        l.add(Arrays.asList(4,10,15,24,26));
        l.add(Arrays.asList(0,9,12,20));
        l.add(Arrays.asList(5,18,22,30));
        Q632 q = new Q632();
        int[] ints = q.smallestRange(l);

        System.out.println(ints[0] + "," + ints[1]);

    }

    public int[] smallestRange(List<List<Integer>> nums) {
        PriorityQueue<KP> pq = new PriorityQueue<>(nums.size(), (p1, p2) -> {
            return nums.get(p1.lindex).get(p1.iindex) - nums.get(p2.lindex).get(p2.iindex);
        });

        int[] rt = new int[2];
        rt[0] = 0;
        rt[1] = Integer.MAX_VALUE;
        int max = 0;
        for(int i = 0; i < nums.size(); i++) {
            pq.add(new KP(i, 0));
            max = Math.max(max, nums.get(i).get(0));
        }


        while(true) {
            KP kp = pq.poll();


            if(max - nums.get(kp.lindex).get(kp.iindex) < rt[1] - rt[0]
                    || max - nums.get(kp.lindex).get(kp.iindex) == rt[1] - rt[0] && rt[0] < nums.get(kp.lindex).get(kp.iindex)) {
                rt[0] = nums.get(kp.lindex).get(kp.iindex);
                rt[1] = max;
            }

            if(rt[1] - rt[0] == 0) {
                break;
            }

            if(kp.iindex == nums.get(kp.lindex).size()-1) {
                break;
            }

            pq.add(new KP(kp.lindex, kp.iindex+1));
            max = Math.max(max, nums.get(kp.lindex).get(kp.iindex+1));
        }

        return rt;
    }
}

class KP {
    int lindex; // 链表的索引，第一维
    int iindex; // 链表中元素的索引，第二维

    public KP(int l, int i) {
        lindex = l;
        iindex = i;
    }
}