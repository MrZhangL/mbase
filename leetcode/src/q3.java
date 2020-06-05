import java.util.HashMap;

public class q3 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(3);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(1);
        root.right.right = new TreeNode(1);

        q3 q = new q3();
        System.out.println(q.pseudoPalindromicPaths(root));
    }
    private int count = 0;

    public int pseudoPalindromicPaths (TreeNode root) {
        midOrder(root, new StringBuilder());
        return count;
    }

    private void midOrder(TreeNode node, StringBuilder s){
        s.append(String.valueOf(node.val));
        if(node.left != null){
            midOrder(node.left, s);
        }
        if(node.right != null){
            midOrder(node.right, s);
        }

        if(node.left == null && node.right == null){
            if(judgePali(s)){
                count++;
            }
        }

        s.delete(s.length() - 1, s.length());
    }

    private boolean judgePali(StringBuilder s){
        HashMap<Character, Integer> map = new HashMap<>();
        for(int i = 0; i < s.length(); i++){
            if(map.containsKey(s.charAt(i))){
                map.put(s.charAt(i), map.get(s.charAt(i)) + 1);
            }
            else{
                map.put(s.charAt(i), 0);
            }
        }

        int jc = 0;
        boolean g = (s.length() % 2 == 0);

        for(Character key : map.keySet()){
            if(map.get(key)%2 != 0){
                if(g){
                    return false;
                }
                else{
                    jc++;
                    if(jc > 1){
                        return false;
                    }
                }
            }
        }

        return true;
    }
}

class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
  }
}
