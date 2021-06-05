package lee;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Q40 {

    List<List<Integer>> rt;
    LinkedList<Integer> buf = new LinkedList<>();

    public static void main(String[] args) {
        Q40 q = new Q40();
        System.out.println(q.combinationSum2(new int[]{3, 1, 3, 5, 1, 1}, 8));
        LinkedList<Object[]> s = new LinkedList<>();
        s.addLast(new Object[]{1,2});

    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        rt = new LinkedList<>();
        Arrays.sort(candidates);
        dfs(candidates, 0, target);
        return rt;
    }

    private void dfs(int[] candidates, int index, int target) {
        for(int i = index; i < candidates.length; i++){
            if(candidates[i] > target || (i != index && candidates[index] == candidates[i])) continue;
            buf.add(candidates[i]);

            if(candidates[i] == target) rt.add((LinkedList<Integer>)buf.clone());
            else dfs(candidates, i + 1, target - candidates[i]);

            buf.removeLast();
        }
    }

}
