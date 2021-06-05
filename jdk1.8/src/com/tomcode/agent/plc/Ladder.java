package com.tomcode.agent.plc;

import java.util.*;

public class Ladder {

    private Node p1;
    private Node p2;
    Node root = new Node(4, "root");

    Deque<Node> or = new LinkedList<>();
    Deque<Node> and = new LinkedList<>();

    public static void main(String[] args) {
        Ladder ladder = new Ladder();
        ladder.start();
    }

    public void start() {
        Node vp1 = new Node(1, "11");
        Node vp2 = new Node(1, "12");
        Node vp3 = new Node(1, "13");
        Node vp4 = new Node(1, "14");

        Node x1 = new Node(0, "1");
        Node x2 = new Node(0, "2");
        Node x3 = new Node(0, "3");
        Node x4 = new Node(0, "4");
        Node x5 = new Node(0, "5");
        Node x6 = new Node(0, "6");

        Node y1 = new Node(1, "31");
        {
            vp1.next = Arrays.asList(x1, x2, x4);
            vp2.next = Arrays.asList(x3);
            vp2.pre = Arrays.asList(x1, x2);

            vp3.next = Arrays.asList(x5, x6);
            vp3.pre = Arrays.asList(x3, x4);

            vp4.next = Arrays.asList(y1);
            vp4.pre = Arrays.asList(x5, x6);

            x1.next = Arrays.asList(vp2);
            x1.pre = Arrays.asList(vp1);

            x2.next = Arrays.asList(vp2);
            x2.pre = Arrays.asList(vp1);

            x3.next = Arrays.asList(vp3);
            x3.pre = Arrays.asList(vp2);

            x4.next = Arrays.asList(vp3);
            x4.pre = Arrays.asList(vp1);

            x5.next = Arrays.asList(vp4);
            x5.pre = Arrays.asList(vp3);

            x6.next = Arrays.asList(vp4);
            x6.pre = Arrays.asList(vp3);

            y1.pre = Arrays.asList(vp4);

        }

        p1 = vp1;
        p2 = root;

        while(p1 != null) {
            p1.accessTime++;
            if(p1.accessTime >= p1.getIndegree()) {
                if(p1.getIndegree() >= 2) {
                    p2 = and.pop();
                    if(p2 == null) System.out.println(1);
                }

                if(p1.getOutDegree() >= 2) {
                    s8();
                } else {
                    Node p3 = new Node(2);
                    if(p2.left == null) p2.left = p3;
                    else p2.right = p3;
                    p3.left = p1;

                    p2 = p3;
                    p1 = p1.next.size() == 0? null : p1.next.get(0);
                }
            } else {
                p1 = or.pop();
                p2 = or.pop();
                if(p2 == null) System.out.println(2);
            }
        }

        printBfs();
    }

    void s8() {
        Node p3 = new Node(2);
        Node p4 = new Node(2);

        Node[] p = new Node[p1.getOutDegree() - 1];
        for (int i = 0; i < p.length; i++) {
            p[i] = new Node(3);
        }

        if(p2.left == null) p2.left = p3;
        else p2.right = p3;
        p3.left = p4;
        p4.left = p1;
        p4.right = p[0];

        for (int i = 1; i < p.length; i++) {
            p[i-1].left = p1;
        }

        and.push(p3);
        for (int i = 0; i < p.length; i++) {
            or.push(p[i]);
            or.push(p1.next.get(i+1));
        }

        p1 = p1.next.size() == 0? null : p1.next.get(0);
        p2 = p[p.length - 1];
    }

    static class Node {
        int accessTime = 0; // 访问次数
        int type;    // 0:普通节点，1:虚节点，2:与节点，3:或节点，4:根节点

        List<Node> next = new LinkedList<>();    // 后继节点
        List<Node> pre = new LinkedList<>();     // 前驱节点

        Node left;
        Node right;

        String name;

        public Node(int type) {
            this.type = type;
            next.add(null);
        }

        public Node(int type, String name) {
            this.type = type;
            this.name = name;
        }

        int getIndegree() {return pre.size();}
        int getOutDegree() {return next.size();}
    }

    static class TreeNode {

        Object obj;

        TreeNode left;
        TreeNode right;
    }

    private void printBfs() {
        Queue<Node> q = new LinkedList<>();
        q.offer(root);

        int size = 1;
        int newSize = 0;

        while(!q.isEmpty()) {
            newSize = 0;
            for (int i = 0; i < size; i++) {
                Node poll = q.poll();
                if(poll == null) {
                    System.out.println("null");
                    continue;
                }
                else if(poll.type == 2) System.out.println("+");
                else if(poll.type == 3) System.out.println("|");
                else System.out.println(poll.name);

                q.offer(poll.left);
                q.offer(poll.right);
                newSize += 2;
            }

            size = newSize;
        }
    }
}
