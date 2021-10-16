package com.example.make;

import android.app.DownloadManager;

import com.example.make.service.RequestService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static Retrofit retrofit;
    private static RequestService service;
    private static RetrofitManager manager;
    private static final String BASE_URL = "http://222.114.75.123:8080/";

    public static RequestService getInstance() {
        if (service == null) {
            retrofit =new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(RequestService.class);
            return service;
        }
        return service;
    }
}
