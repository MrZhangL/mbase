package com.tomcode.agent;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.util.ListReferenceResolver;

public class KryoLearn {
    public static void main(String[] args) {
        Kryo kryo = new Kryo();
        kryo.setReferenceResolver(new ListReferenceResolver());

        System.out.println(System.currentTimeMillis());
    }
}
