package com.example.myapplication;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {
    private API_Service request_API;

    public HttpRequest() {
        request_API = new Retrofit.Builder()
                .baseUrl("http://192.168.1.54:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(API_Service.class);
    }
    public API_Service callAPI() {
        return request_API;
    }

}
