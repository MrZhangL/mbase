package com.tomcode.Stream;

import com.tomcode.lambda.Employee;

import java.util.Arrays;
import java.util.List;

public class Learn2 {

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("zs", true, 25, 6000),
                new Employee("ls", true, 35, 9000),
                new Employee("ww", false, 21, 5000),
                new Employee("lx", true, 45, 12000),
                new Employee("qs", false, 33, 7000)
        );

        employees.stream().map(Employee::getName)
                .sorted((s1, s2)-> s1.charAt(0) - s2.charAt(0))
                .forEach(System.out::println);
    }
}
