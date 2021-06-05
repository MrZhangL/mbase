import lee.ListNode;

import java.util.*;

public class main6 {
    public static void main(String[] args) {
        
        /*Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        char[] chars = line.toCharArray();
        line = new String(chars, 1, chars.length - 2);
        String[] split = line.split(",");

        int[] arr = new int[split.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(split[i]);
        }
        Map<Integer, Integer> mp = new HashMap<>();
        mp.put(5, 2);
        mp.put(10, 0);
        mp.put(20, 0);

        int i = 0;
        for (; i < arr.length; i++) {
            int z = arr[i] - 5;

            if(z == 5) {
                Integer p = mp.get(z);
                if(p == 0) break;

                mp.put(z, p - 1);
            } else if(z == 15) {
                Integer p1 = mp.get(5);
                Integer p2 = mp.get(10);

                if(p1 == 0 || p2 == 0) {
                    if(p1 >= 3) {
                        mp.put(5, p1 - 3);
                        continue;
                    } else {
                        break;
                    }
                }
                mp.put(5, p1 - 1);
                mp.put(10, p2 - 1);
            }
            mp.put(arr[i], mp.get(arr[i]) + 1);
        }

        System.out.println(i);*/
        Solution s = new Solution();
        System.out.println(s.ans(3, new int[][]{{0, 1, 3}, {1, 2, 2},{0,2,1}}));
    }

    public boolean lemonadeChange(int[] bills) {
        int five = 0, ten = 0;
        int cnt = 0;
        for (int bill : bills) {
            if (bill == 5) {
                five++;
            } else if (bill == 10) {
                if (five == 0) {
                    break;
                }
                five--;
                ten++;
            } else {
                if (five > 0 && ten > 0) {
                    five--;
                    ten--;
                } else if (five >= 3) {
                    five -= 3;
                } else {
                    break;
                }
            }
            cnt++;
        }
        return true;
    }

    public int ans(ListNode l1, ListNode l2) {
        int h1 = l1.val;
        int h2 = l2.val;

        ListNode ll1 = reverseList(l1);
        ListNode ll2 = reverseList(l2);

        return 1;
    }
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode res = new ListNode(0);
        ListNode helper = res;
        int temp = 0;
        int up = 0;

        while (l1 != null && l2 != null) {
            int sum = l1.val + l2.val + up;
            if ( sum > 9) {
                temp = sum - 10;
                up = 1;
            } else {
                temp = sum;
                up = 0;
            }
            helper.next = new ListNode(temp);
            helper = helper.next;
            l1 = l1.next;
            l2 = l2.next;
        }

        ListNode helper2 = l1 == null ? l2 : l1;
        while (helper2 != null) {
            int sum = helper2.val + up;
            if ( sum > 9) {
                temp = sum - 10;
                up = 1;
            } else {
                temp = sum;
                up = 0;
            }
            helper.next = new ListNode(temp);
            helper = helper.next;
            helper2 = helper2.next;
        }

        if (up == 1) {
            helper.next = new ListNode(up);
            helper = helper.next;
        }
        return res.next;
    }



    static class Solution {
        public int ans(int n, int[][] edges) {
            // 通过给出的边构造邻接图
            List<int[]>[] graph = new List[n];
            for (int i = 0; i < edges.length; i++) {
                if(graph[edges[i][0]] == null) graph[edges[i][0]] = new LinkedList<>();
                graph[edges[i][0]].add(new int[]{edges[i][1], edges[i][2] });
                if(graph[edges[i][1]] == null) graph[edges[i][1]] = new LinkedList<>();
                graph[edges[i][1]].add(new int[]{edges[i][0], edges[i][2]});
            }

            int mm = Integer.MAX_VALUE;
            int id = 0;
            for (int i = 0; i < n; i++) {
                int s = djk(graph, i, n);
                if(s < mm){
                    id = i;
                    mm = s;
                }
            }

            return id;
        }

        int djk(List<int[]>[] graph, int index, int n) {
            // 每个节点到节点n的距离，通过djikstra求解
            int[] dists = new int[n];
            Arrays.fill(dists, Integer.MAX_VALUE);

            dists[index] = 0;	// 最后一个为0
            // djikstra需要的优先队列，每次弹出最小的值，贪心
            PriorityQueue<int[]> pq = new PriorityQueue<>((i1, i2)->i1[1] - i2[1]);
            pq.offer(new int[]{index, 0});

            // 记录每个节点是否已经被弹出，弹出了的是已经确定最短路径的节点
            boolean[] visited = new boolean[n];

            while(!pq.isEmpty()) {
                int[] cur = pq.poll();
                // 某个节点可能在优先队列中出现两次，因为我们可能找到了某个节点到目标点一个更短的路径
                // 我们不需要再去队列中删除那个长的路径，只需要在弹出时忽略
                if(dists[cur[0]] >= cur[1]) {
                    dists[cur[0]] = cur[1];
                } else {
                    continue;
                }
                visited[cur[0]] = true;
                if(graph[cur[0]] != null) {
                    for (int[] next : graph[cur[0]]) {
                        // 对于某个未弹出的节点，找到了一条更短的路径，就放入优先队列中
                        if(!visited[next[0]] && next[1] + cur[1] < dists[next[0]])
                            pq.offer(new int[]{next[0], cur[1] + next[1]});
                    }
                }
            }

            int sum = 0;
            for (int dist : dists) {
                if(dist == Integer.MAX_VALUE) return Integer.MAX_VALUE;
                sum += dist;
            }

            return sum;
        }
    }
}
   /* public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = sc.nextInt();
        }

        int mx = 0;
        int idx1 = 0;

        while(idx1 + 2 < n) {
            int idx2 = idx1 + 1;
            if(arr[idx1] < arr[idx1 + 1]) {
                while(idx2 + 1 < n && arr[idx2] < arr[idx2 + 1]) {
                    idx2++;
                }
            }

            if(idx2 + 1 < n && arr[idx2] > arr[idx2 + 1]) {
                while(idx2 + 1 < n && arr[idx2] < arr[idx2 + 1]) {
                    idx2++;
                }
                mx = mx = Math.max(mx , idx2 - idx1 + 1);
            } else {
                idx2++;
            }
            idx1 = idx2;
        }

        System.out.println(mx);
    }*/


