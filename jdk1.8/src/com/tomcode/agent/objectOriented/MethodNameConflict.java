package com.tomcode.agent.objectOriented;


public class MethodNameConflict implements F1, F2{


    public static void main(String[] args) {
        MethodNameConflict m = new MethodNameConflict();
        m.show();
    }

    @Override
    public void show() {
        System.out.println("sd");
    }
}

interface F1 {
    void show();
}

interface F2 {
    void show();
}