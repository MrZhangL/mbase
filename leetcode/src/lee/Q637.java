package lee;

import java.util.LinkedList;
import java.util.List;

public class Q637 {
    public static void main(String[] args) {
        double a = 3;
        int b = 2;
        System.out.println(a/b);
    }

    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> rt = new LinkedList<>();
        LinkedList<TreeNode> q = new LinkedList<>();
        q.addLast(root);
        double sum = 0;
        int curSize = 1, newSize = 1;

        while(!q.isEmpty()) {
            sum = 0;
            curSize = newSize;
            newSize = 0;
            for (int i = 0; i < curSize; i++) {
                TreeNode node = q.removeFirst();
                sum += node.val;
                if(node.left != null) {
                    newSize++;
                    q.addLast(node.left);
                }
                if(node.right != null) {
                    newSize++;
                    q.addLast(node.right);
                }
            }
            rt.add(sum/curSize);
        }
        return rt;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}