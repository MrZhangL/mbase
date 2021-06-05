package com.tomcode.agent.Stream;


import com.tomcode.agent.lambda.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Learn1 {
    public static void main(String[] args) {
        // 1: 通过集合的stream方法获取流
        List<Employee> list = new ArrayList<>();
        Stream<Employee> stream = list.stream();

        // 2: 通过Arrays工具类提供的方法获取数组的流
        Employee[] employees = new Employee[3];
        Arrays.stream(employees);

        // 3: Stream类的静态方法of
        Stream<String> stringStream = Stream.of("aa", "bb", "qqq");
        Stream<String> midStream = stringStream.filter(s -> {
            System.out.println("中间操作");
            return s.length() < 3;
        }).limit(1);
        System.out.println("终止操作");
        midStream.forEach(System.out::println);

        // 4: 创建无限的流，下面x从0开始依次+1，返回x+2无限循环，注意在创建流时数据不会生成，只有使用时才执行生成数据。
        Stream<Integer> iterate = Stream.iterate(0, x -> x + 2);
        //iterate.forEach(System.out::println);   // 使用时生成数据，由于是无限流会一直打印
    }
}
