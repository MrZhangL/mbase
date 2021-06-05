package com.tomcode.agent.lambda;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Learn2 {

    public static void main(String[] args) {
        // 对象::实例方法名================
        List<Integer> list = Arrays.asList(2,3,4,1,5);
        PrintStream printStream = System.out;
        Consumer<Integer> consumer = printStream::println;
        list.forEach(consumer);
        // 上面三行变为一行
        list.forEach(System.out::println);      // foreach需要提供Consumer接口的实现

        // 类名::静态方法名
        Comparator<Integer> comparable = Integer::compare;      // T compare(T t1, T t2);

        // 类名::实例方法名，注意这种形式有一定的要求，即第一个参数为方法的调用者，第二个参数为被引用方法的参数。不建议新手使用
        BiPredicate<String, String> predicate0 = (x, y) -> x.equals(y);
        BiPredicate<String, String> predicate = String::equals;     // boolean test(T t1, T t2);

        Supplier<Employee> supplier = Employee::new;
    }
}

