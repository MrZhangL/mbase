package com.tomcode.agent.jvm;

import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

public class StrTable {

    public static void main(String[] args) {
        String s1 = "a";
        String s2 = "b";

        String s3 = "ab";
        String s = s1 + s2;
        String ss = s.intern();


        System.out.println(s3 == ss);
        System.out.println(s3 == s);
        System.out.println(s == ss);

        PriorityQueue pq = new PriorityQueue();
        boolean[] flags = new boolean[2];
        System.out.println(flags[0]);

        int a= 1;
        Long b = Long.valueOf(a);

        Scanner sc = new Scanner(System.in);
        Random r = new Random();
        r.nextInt(3);
    }
}
