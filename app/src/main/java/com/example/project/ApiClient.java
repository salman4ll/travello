package com.example.project;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiClient {
    private static final String API_BASE_URL = "http://103.171.182.206:8070/";

    private static  final OkHttpClient httpClient = new OkHttpClient();
    public static String login(String email, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        String requestBody = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);
        RequestBody body = RequestBody.create(mediaType, requestBody);

        Request request = new Request.Builder()
                .url(API_BASE_URL + "login")
                .post(body)
                .build();

        Response response = httpClient.newCall(request).execute();
        String responseData = response.body().string();
        response.close();

        return responseData;
    }

    public  static String registration(String firstName, String lastname, String email, String password) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        String requestBody = String.format("{\"first_name\":\"%s\",\"last_name\":\"%s\",\"email\":\"%s\",\"password\":\"%s\"}",firstName, lastname,email, password);
        RequestBody body = RequestBody.create(mediaType, requestBody);

        Request request = new Request.Builder()
                .url(API_BASE_URL + "register")
                .post(body)
                .build();

        Response response = httpClient.newCall(request).execute();
        String responseData = response.body().string();
        response.close();

        return responseData;
    }

    public static String getUser(String token) throws IOException {
        Request request = new Request.Builder()
                .url(API_BASE_URL + "user")
                .get()
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Response response = httpClient.newCall(request).execute();
        String responseData = response.body().string();
        response.close();

        return responseData;
    }

    public static String updatePassword(String token, String oldPassword, String newPassword) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        String requestBody = String.format("{\"old_password\":\"%s\",\"new_password\":\"%s\"}",oldPassword, newPassword);
        RequestBody body = RequestBody.create(mediaType, requestBody);

        Request request = new Request.Builder()
                .url(API_BASE_URL + "userpassword")
                .put(body)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Response response = httpClient.newCall(request).execute();
        String responseData = response.body().string();
        response.close();

        return responseData;
    }

    public static String updateUser(String token, String name, String email) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        String requestBody = String.format("{\"name\":\"%s\",\"email\":\"%s\"}",name, email);
        RequestBody body = RequestBody.create(mediaType, requestBody);

        Request request = new Request.Builder()
                .url(API_BASE_URL + "user")
                .put(body)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Response response = httpClient.newCall(request).execute();
        String responseData = response.body().string();
        response.close();

        return responseData;
    }

    public static String getDestinations() throws IOException {
        Request request = new Request.Builder()
                .url(API_BASE_URL + "destination")
                .get()
                .build();
        Response response = httpClient.newCall(request).execute();
        String responseData = response.body().string();
        response.close();

        return responseData;
    }

    public static String getUlasanbyDestination(String destinationId, String token) throws IOException {
        Request request = new Request.Builder()
                .url(API_BASE_URL+"ulasan?destination="+destinationId)
                .get()
                .addHeader("Authorization", "Bearer " + token)
                .build();
        Response response = httpClient.newCall(request).execute();
        String responseData = response.body().string();
        response.close();

        return responseData;
    }

    public static String postUlasan(String token, String destinationId, String message, float rating) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        String requestBody = String.format("{\"destination_id\":\"%s\",\"message\":\"%s\",\"rating\":%.2f}",destinationId, message, rating);
        RequestBody body = RequestBody.create(mediaType, requestBody);

        Request request = new Request.Builder()
                .url(API_BASE_URL + "ulasan")
                .post(body)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Response response = httpClient.newCall(request).execute();
        String responseData = response.body().string();
        response.close();

        return responseData;
    }
}
