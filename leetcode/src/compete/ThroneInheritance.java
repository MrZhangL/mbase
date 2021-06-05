package compete;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ThroneInheritance {

    private static TrNode root;
    private HashMap<String,  TrNode> mp = new HashMap<>();

    public ThroneInheritance(String kingName) {
        root = new TrNode(kingName);
        mp.put(kingName, root);
    }

    public void birth(String parentName, String childName) {
        TrNode trNode = mp.get(parentName);
        TrNode trNode1 = new TrNode(childName);
        trNode.sons.addLast(trNode1);
        mp.put(childName, trNode1);
    }

    public void death(String name) {
        mp.get(name).death = true;
    }

    public List<String> getInheritanceOrder() {
        List<String> l = new LinkedList<>();
        preOrder(root,l);
        return l;
    }

    private void preOrder(TrNode node, List<String> l) {
        if(!node.death) l.add(node.name);
        for(TrNode son : node.sons) {
            preOrder(son, l);
        }
    }

    class TrNode {
        String name;
        boolean death = false;
        LinkedList<TrNode> sons = new LinkedList<>();

        public TrNode(String name) {
            this.name = name;
        }
    }
}
