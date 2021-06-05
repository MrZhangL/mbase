package com.tomcode.agent.Stream;

import com.tomcode.agent.lambda.Employee;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Learn2 {

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("zs", true, 25, 6000),
                new Employee("ls", true, 35, 9000),
                new Employee("ww", false, 21, 5000),
                new Employee("lx", true, 45, 12000),
                new Employee("qs", false, 33, 7000)
        );

        employees.stream().map(Employee::getName).flatMap((s -> s.chars().mapToObj(c->(char)c)))
                .sorted((s1, s2) -> s1 - s2)
                .collect(Collectors.toList()).forEach(System.out::print);


    }
}
