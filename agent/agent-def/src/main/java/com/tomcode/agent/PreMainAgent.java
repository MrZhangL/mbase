package com.tomcode.agent;

import java.lang.instrument.Instrumentation;

public class PreMainAgent {

    public static void premain(String agentParams, Instrumentation instrumentation) {
        System.out.println("启动");
    }
}
