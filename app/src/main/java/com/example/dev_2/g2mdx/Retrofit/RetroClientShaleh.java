package com.example.dev_2.g2mdx.Retrofit;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mark on 4/24/2017.
 */

public class RetroClientShaleh {
    static boolean created = false;
    static RetroClientShaleh client;
    private static String ROOT_URL;
    //Context context;

    private RetroClientShaleh() {
        //this.context = context;
        ROOT_URL = "https://shaleh4u.com/app/android/";
    }

    public static RetroClientShaleh getInstanceRetrofit(Context con) {

        if (!created)
            client = new RetroClientShaleh();
        created = true;
        return client;
    }

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public ShalehAPI getApiSh() {
        return getRetrofitInstance().create(ShalehAPI.class);}
    public PagingService getProd() {
        return getRetrofitInstance().create(PagingService.class);
    }



}
