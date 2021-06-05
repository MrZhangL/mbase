package com.zl.future;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Complete {

    static ExecutorService service = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CompletableFuture.supplyAsync(()->"hello")
                .thenApply(s -> s + " world")
                .thenRun(System.out::println);

    }

    @Test
    public static void t1() {
        List<Integer> ls = new CopyOnWriteArrayList<>();
    }
}
