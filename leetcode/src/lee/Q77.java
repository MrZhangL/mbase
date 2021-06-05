package lee;

import java.util.LinkedList;
import java.util.List;

public class Q77 {

    public static void main(String[] args) {
        Q77 q = new Q77();
        List<List<Integer>> combine = q.combine(4, 2);
        for(List<Integer> l : combine) {
            l.forEach(System.out::print);
            System.out.println();
        }
    }

    boolean[] flag;
    List<List<Integer>> rt = new LinkedList<>();
    LinkedList<Integer> buf = new LinkedList<>();

    public List<List<Integer>> combine(int n, int k) {
        flag = new boolean[n];

        dfs(0, k - 1);

        return rt;
    }

    private void dfs(int index, int k) {
        if(index > k) {
            rt.add((List<Integer>) buf.clone());
            return;
        }
        for(int i = 0; i < flag.length; i++) {
            if(flag[i]) continue;

            flag[i] = true;
            buf.add(i+1);
            dfs(index+1, k);
            flag[i] = false;
            buf.removeLast();
        }
    }
}
