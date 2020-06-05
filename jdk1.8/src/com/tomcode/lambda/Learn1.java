package com.tomcode.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Learn1 {

    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
                new Employee("zs", true, 25, 6000),
                new Employee("ls", true, 35, 9000),
                new Employee("ww", false, 21, 5000),
                new Employee("lx", true, 45, 12000),
                new Employee("qs", false, 33, 7000)
        );

        // 查找年龄小于30的
        List<Employee> employeesBelow30 = filter(employees, e -> e.getAge() < 30);

        // 查找性别为男的
        List<Employee> employeesMan = filter(employees, e -> e.isSex());

        // 查找工资大于5000的
        List<Employee> employeesSalaryExceed5000 = filter(employees, e -> e.getSalary() > 5000);
    }

    public static List<Employee> filter(List<Employee> employees, EmployeeOperater<Employee> employeeFilter){
        List<Employee> employeesMeetCondition = new ArrayList<>();
        for(Employee employee : employees) {
            if(employeeFilter.filter(employee)){
                employeesMeetCondition.add(employee);
            }
        }

        return employeesMeetCondition;
    }
}

@FunctionalInterface
interface EmployeeOperater<T>{
    boolean filter(T t);

}

