package com.example.dev_2.g2mdx.Retrofit;

import android.content.Context;

import com.example.dev_2.g2mdx.Retrofit.Models.ListEX;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nayir on 5/4/2017.
 */

public class callRetrofitShaleh {
    static String s = "name";
    static boolean callCreatedReviews = false;
    static callRetrofitShaleh RetrofitReviews;
    Context con;
ListEX count;

    private callRetrofitShaleh(Context context) {
        con = context;
    }

    public static callRetrofitShaleh getInstance(Context conn) {
        if (!callCreatedReviews)
            RetrofitReviews = new callRetrofitShaleh(conn);
        callCreatedReviews = true;
        return RetrofitReviews;
    }

    public void retrofitCall(final NetworkResponse<ListEX> reviewsResponse) {
        RetroClientShaleh retro = RetroClientShaleh.getInstanceRetrofit(con);
        ShalehAPI api =  retro.getApiSh();
        Call<ListEX> call = api.getMyJSON();
        call.enqueue(new Callback<ListEX>() {


            @Override
            public void onResponse(Call<ListEX> call, Response<ListEX> response) {
                if (response.isSuccessful()) {
                    count = response.body();
                    reviewsResponse.onSucess(count);


                }
            }

            @Override
            public void onFailure(Call <ListEX> call, Throwable t) {

            }


        });
    }
}
