package com.example.dev_2.g2mdx.Retrofit;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mark on 4/24/2017.
 */

public class RetroClientPaging {
    static boolean created = false;
    static RetroClientPaging client;
    private static String ROOT_URL;
    //Context context;

    private RetroClientPaging() {
        //this.context = context;
        ROOT_URL = "https://grapes-n-berries.getsandbox.com/";
    }

    public static RetroClientPaging getInstanceRetrofit(Context con) {

        if (!created)
            client = new RetroClientPaging();
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
