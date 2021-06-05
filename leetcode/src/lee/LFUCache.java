package lee;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LFUCache {

    public static void main(String[] args) {
        String[] ops = new String[]{"LFUCache","put","put","put","put","put","get","put","get","get","put","get","put","put","put","get","put","get","get","get","get","put","put","get","get","get","put","put","get","put","get","put","get","get","get","put","put","put","get","put","get","get","put","put","get","put","put","put","put","get","put","put","get","put","put","get","put","put","put","put","put","get","put","put","get","put","get","get","get","put","get","get","put","put","put","put","get","put","put","put","put","get","get","get","put","put","put","get","put","put","put","get","put","put","put","get","get","get","put","put","put","put","get","put","put","put","put","put","put","put"};
        Integer[][] argvs = new Integer[][]{{10},{10,13},{3,17},{6,11},{10,5},{9,10},{13},{2,19},{2},{3},{5,25},{8},{9,22},{5,5},{1,30},{11},{9,12},{7},{5},{8},{9},{4,30},{9,3},{9},{10},{10},{6,14},{3,1},{3},{10,11},{8},{2,14},{1},{5},{4},{11,4},{12,24},{5,18},{13},{7,23},{8},{12},{3,27},{2,12},{5},{2,9},{13,4},{8,18},{1,7},{6},{9,29},{8,21},{5},{6,30},{1,12},{10},{4,15},{7,22},{11,26},{8,17},{9,29},{5},{3,4},{11,30},{12},{4,29},{3},{9},{6},{3,4},{1},{10},{3,29},{10,28},{1,20},{11,13},{3},{3,12},{3,8},{10,9},{3,26},{8},{7},{5},{13,17},{2,27},{11,15},{12},{9,19},{2,15},{3,16},{1},{12,17},{9,1},{6,19},{4},{5},{5},{8,1},{11,7},{5,2},{9,28},{1},{2,2},{7,4},{4,22},{7,24},{9,26},{13,28},{11,26}};

        LFUCache lfuCache = new LFUCache(argvs[0][0]);
        Integer[] ans = new Integer[ops.length];
        ans[0] = null;
        Integer[] correct = new Integer[]{null,null,null,null,null,null,-1,null,19,17,null,-1,null,null,null,-1,null,-1,5,-1,12,null,null,3,5,5,null,null,1,null,-1,null,30,5,30,null,null,null,-1,null,-1,24,null,null,18,null,null,null,null,14,null,null,18,null,null,11,null,null,null,null,null,18,null,null,-1,null,4,29,30,null,12,11,null,null,null,null,29,null,null,null,null,17,-1,18,null,null,null,-1,null,null,null,20,null,null,null,29,18,18,null,null,null,null,20,null,null,null,null,null,null,null};

        for(int i = 1; i < ops.length; i++) {
            if(ops[i].equals("put")) {
                lfuCache.put(argvs[i][0], argvs[i][1]);
                ans[i] = null;
            } else {
                ans[i] = lfuCache.get(argvs[i][0]);
            }
        }

        int idx = 0;
        for(int i = 0; i < ans.length; i++) {
            if(correct[i] == ans[i] || ans[i].equals(correct[i])) continue;
            else {
                idx = i;
                break;
            }
        }

        System.out.println(idx);

        Arrays.stream(ans).forEach(a -> System.out.print(a + ","));
    }

    ListNode head = new ListNode(-1, -1, null, null);
    ListNode tail = new ListNode(-1, -1, null, null);

    Map<Integer, ListNode> store = new HashMap<>();
    Map<Integer, ListNode> freq = new HashMap<>();

    private int capacity;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        ListNode node = store.get(key);
        if(node == null) {
            return -1;
        }

        freqChange(node);
        return node.val;
    }

    public void put(int key, int value) {
        ListNode node = store.get(key);
        if(node != null) {
            // 不会淘汰
            node.val = value;
            freqChange(node);

            return;
        }

        // 需要新增并且满了
        ListNode rhead;
        if(store.size() == capacity) {
            // 满了
            if((rhead = head.next) == tail) return;

            //if(rhead.freq > 1) return;      // 频率大于1，放进来就被淘汰

            store.remove(rhead.key);
            if(rhead.next == null || (rhead.next != tail && rhead.next.freq != rhead.freq)) {   // 后面没有相同频率的，那么删除这一个就没有该频率的节点了，从freq中删除
                freq.remove(rhead.freq);
            }

            rhead.key = key;
            rhead.val = value;
            rhead.freq = 0;
        } else {
            rhead = new ListNode(key, value, head, head.next);
            head.next = rhead;
            rhead.next.pre = rhead;
        }

        freqChange(rhead);
        store.put(key, rhead);
        if(getLength() != store.size()) {
            System.out.println("s");
        }
    }

    void freqChange(ListNode node) {
        ListNode nd = freq.get(node.freq);
        if(nd != null && nd == node) {
            // 是同一个，需要考虑remove，不是同一个说明还有一个其他的节点频率相同且位于更后面，不需要remove
            freq.remove(node.freq);
            // 前面是否还有相同使用频率的
            if(nd.pre != head && nd.pre.freq == nd.freq) {
                freq.put(nd.freq, nd.pre);
            }
        }

        node.freq++;
        ListNode nl = freq.get(node.freq);    // 出现的频率为该节点频率+1的节点
        freq.put(node.freq, node);

        if(nl != null || (nd != null && nd != node)) {
            if(nl == null) nl = nd;
            // 移除node并插入到nl的后面
            node.pre.next = node.next;
            node.next.pre = node.pre;

            node.next = nl.next;
            node.pre = nl;
            node.next.pre = node;
            node.pre.next = node;
        }
    }

    private int getLength() {
        ListNode node = head.next;
        int size = 0;
        while(node != tail) {
            size++;
            node = node.next;
        }

        return size;
    }

    static class ListNode {
        int key;
        int val;
        int freq;
        ListNode pre;
        ListNode next;

        ListNode(int key, int val, ListNode pre, ListNode next) {
            this.key = key;
            this.val = val;
            this.freq = 0;
            this.pre = pre;
            this.next = next;
        }
    }
}
