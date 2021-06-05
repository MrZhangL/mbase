package com.tomcode.agent.health;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Uuser {

    private String make;
    private double age;
    private User user;

    public static void main(String[] args) throws ClassNotFoundException {
        int[] a = new int[]{12};
        System.out.println(a.getClass().getName());
    }
}
