package com.tomcode.agent.jdk;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Mp {

    static final int AS_ASE = 1;

    public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException, InterruptedException {
        HashMap<String, String> hsmp = new HashMap<>();
        LinkedHashMap<String, String> lkhsmp = new LinkedHashMap<>();
        TreeMap<String, String> tmp = new TreeMap<>();
        ConcurrentHashMap<String, String> cchsmp = new ConcurrentHashMap<>();

        lkhsmp.put("asd", "ew");
        lkhsmp.put("ewa", "qw");
        lkhsmp.put("asd", "rw");

        lkhsmp.forEach((k, v)-> System.out.println(k));




        System.out.println(System.currentTimeMillis());

        StringBuilder sb = new StringBuilder("..");
        StringBuilder sb1 = new StringBuilder("..");
        System.out.println(sb1.equals(sb));
    }


}
