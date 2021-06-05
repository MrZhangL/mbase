package compete;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class q3 {

    public static void main(String[] args) {
        q3 q = new q3();
        System.out.println(q.minCost("bbbaaa", new int[]{4,9,3,8,8,9}));    //23
        System.out.println(q.minCost("aabaa", new int[]{1,2,3,4,1}));    //2
    }

    public int minCost(String s, int[] cost) {
        StringBuilder sb = new StringBuilder();
        boolean cf = false;
        int maxlst = 0;
        LinkedList<Integer> ls = new LinkedList<>();

        int sumcost = 0;
        for (int i = 1; i < s.length(); i++) {
            if(s.charAt(i) == s.charAt(i-1)) {
                cf = true;
                if(cost[maxlst] < cost[i]) {
                    maxlst = i;
                }
            }
            else {
                if(cf) {
                    ls.addLast(maxlst);
                } else {
                    ls.addLast(i-1);
                }
                cf = false;
                maxlst = i;
            }
        }

        if(cf) {
            ls.addLast(maxlst);
        } else {
            ls.addLast(s.length()-1);
        }

        for (int i = 0; i < cost.length; i++) {
            sumcost += cost[i];
        }
        for (int cs : ls) {
            sumcost -= cost[cs];
        }

        return sumcost;
    }


}


