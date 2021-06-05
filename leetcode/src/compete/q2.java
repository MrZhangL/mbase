package compete;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class q2 {

    public static void main(String[] args) {
        List<Integer> l = new ArrayList<>();
        for(int i : l) {
            System.out.println(i);
        }
    }

    int cnt = 0;

    public int countPairs(TreeNode root, int distance) {
        dfs(root, distance);
        return cnt;
    }

    private List<Integer> dfs(TreeNode n, int distance) {
        List<Integer> l = n.left == null? new LinkedList<>() : dfs(n.left, distance);
        List<Integer> r = n.right == null? new LinkedList<>() : dfs(n.right, distance);
        List<Integer> rt = new LinkedList<>();

        if(l.size() == 0 && r.size() == 0) {
            rt.add(1);
            return rt;
        }

        for(int ll : l) {
            for(int rr : r) {
                if(ll + rr <= distance) {
                    cnt++;
                }
            }
            rt.add(ll+1);
        }

        for (int rr : r) {
            rt.add(rr+1);
        }



        return rt;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
