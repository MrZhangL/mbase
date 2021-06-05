package com.tomcode.agent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class Json {
    public static void main(String[] args) throws NoSuchMethodException {
        Json j = new Json();
        HashMap<String, List<List<String>>> test = j.test();


        System.out.println(test);

    }

    public HashMap<String, List<List<String>>> met() {
        return new HashMap<>();
    }

    public String met1() {
        return "dsa";
    }

    public HashMap<String, List<List<String>>> test() throws NoSuchMethodException {
        Method met = Json.class.getMethod("met1");
        Type returnType = met.getGenericReturnType();

        TypeReference<HashMap<String, List<List<String>>>> typeReference = new TypeReference<HashMap<String, List<List<String>>>>() {
        };


        String json = "{\"name\":[[\"ewq\", \"eqw\"],[\"sdfs\", \"gfdf\"]], \"age\":[[\"12\", \"32\"],[\"14\", \"11\"]]}";
        Object o = JSON.parseObject(json, returnType);

        return (HashMap<String, List<List<String>>>) o;
    }
}
