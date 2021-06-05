package com.tomcode.agent;

import okhttp3.*;

import java.io.IOException;

import static com.tomcode.agent.OkSSL.JSON;

public class Ok {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return null;
            }
        }).build();

        Request request = new Request.Builder().url("https://localhost:8080/hello").post(RequestBody.create(JSON, "hello")).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
