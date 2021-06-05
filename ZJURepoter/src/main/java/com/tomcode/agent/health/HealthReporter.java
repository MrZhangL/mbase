package com.tomcode.agent.health;


import okhttp3.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HealthReporter implements Runnable {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient.Builder().protocols(Arrays.asList(Protocol.HTTP_2, Protocol.HTTP_1_1))
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS)).build();

        Request request = new Request.Builder().url("https://localhost:8080/hello").post(RequestBody.create(JSON, "hello")).build();

        try(Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private List<ZJUHealthReport> reports = new ArrayList<>();
    private boolean started;

    public void addUser(User user) {
        ZJUHealthReport report = new ZJUHealthReport(user);
        reports.add(report);
        started = true;
    }

    @Override
    public void run() {
        while(started) {
            try {
                for (ZJUHealthReport report : reports) {
                    report.registerAndNotify();
                }
                Thread.sleep(24 * 60 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
