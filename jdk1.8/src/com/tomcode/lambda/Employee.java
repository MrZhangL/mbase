package com.tomcode.lambda;

public class Employee {
    private String name;
    private boolean sex;
    private int age;
    private int salary;

    public Employee() {
    }

    public Employee(String name, boolean sex, int age, int salary) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
