package com.tomcode.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

public class JavaAgentUse {


    public static void main(String[] args) {


    }

    public static void premain(String agentparam, Instrumentation instrumentation) {
        AgentBuilder.Transformer transformer = new Transform();
        new AgentBuilder.Default().type(ElementMatchers.nameStartsWith("com.tomcode"))
                .transform(transformer).installOn(instrumentation);
    }

    private static class Transform implements AgentBuilder.Transformer {

        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                                TypeDescription typeDescription,
                                                ClassLoader classLoader,
                                                JavaModule javaModule) {
            return builder.method(ElementMatchers.any()).intercept(MethodDelegation.to(MyIntercept.class));
        }
    }


}
