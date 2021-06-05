package com.tomcode.agent;

import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.*;
import java.util.Arrays;

public class OkSSL {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException {

        TrustManager[] trustAllCerts = buildTrustManagers();
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
        builder.hostnameVerifier((hostname, session) -> true);

        OkHttpClient client = builder.protocols(Arrays.asList(Protocol.HTTP_2, Protocol.HTTP_1_1)).build();

        Request request = new Request.Builder().url("https://localhost:8080/hello").post(RequestBody.create(JSON, "hello")).build();

        try(Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private static TrustManager[] buildTrustManagers() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
    }
}
