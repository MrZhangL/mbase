package lee;

import java.util.Deque;
import java.util.LinkedList;

public class Q678 {

    public static void main(String[] args) {
        Q678 q = new Q678();
        System.out.println(q.checkValidString("((**)))()"));
    }

    public boolean checkValidString(String s) {
        Deque<int[]> dq = new LinkedList<>();

        for(int i = 0; i < s.length(); i++) {
            char c;
            int[] pair;
            if((c = s.charAt(i)) == ')') {
                if(dq.isEmpty()) return false;
                pair = dq.peek();
                if(dq.size() == 1 || pair[0] == 0) {
                    pair[1]--;
                    if(pair[1] == 0) dq.pop();
                }
                else {
                    int[] tmp = dq.pop();
                    pair = dq.pop();

                    pair[1]--;
                    if(pair[1] != 0) {
                        dq.push(pair);
                        dq.push(tmp);
                    } else if(!dq.isEmpty()){        // 合并
                        dq.peek()[1] += tmp[1];
                    } else {
                        dq.push(tmp);
                    }
                }

            } else if(c == '(') {
                if(!dq.isEmpty() && (pair = dq.peek())[0] == 0) pair[1]++;
                else dq.push(new int[]{0, 1});
            } else {
                if(!dq.isEmpty() && (pair = dq.peek())[0] == 1) pair[1]++;
                else dq.push(new int[]{1, 1});
            }
        }

        int left = 0;
        while(!dq.isEmpty()) {
            int[] pair = dq.removeLast();
            if(pair[0] == 0) left += pair[1];
            if(pair[0] == 1) {
                left = Math.max(0, left - pair[1]);
            }
        }

        return left == 0;
    }
}
