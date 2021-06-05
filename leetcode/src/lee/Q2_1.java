package lee;

import java.util.HashSet;

public class Q2_1 {

    public static void main(String[] args) {
        Q2_1 Q = new Q2_1();
        ListNode l = new ListNode(1);
        l.next = new ListNode(2);
        l.next.next = new ListNode(3);
        l.next.next.next = new ListNode(3);
        l.next.next.next.next = new ListNode(2);
        l.next.next.next.next.next = new ListNode(1);
        ListNode listNode = Q.removeDuplicateNodes(l);

        while(listNode != null) {
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

    public ListNode removeDuplicateNodes(ListNode head) {
        HashSet<Integer> set = new HashSet<>();
        ListNode node = head;
        set.add(head.val);
        while(node.next != null) {
            if(!set.contains(node.next)) {
                set.add(node.next.val);
                node = node.next;
            } else {
                node.next = node.next.next;
            }
        }

        return head;
    }
}
