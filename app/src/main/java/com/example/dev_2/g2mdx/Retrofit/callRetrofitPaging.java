package com.example.dev_2.g2mdx.Retrofit;

import android.content.Context;
import android.util.Log;

import com.example.dev_2.g2mdx.Retrofit.Models.Products;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


/**
 * Created by nayir on 5/4/2017.
 */

public class callRetrofitPaging {
    static String s = "name";
    static boolean callCreatedReviews = false;
    static callRetrofitPaging RetrofitReviews;
    Context con;
    ArrayList<Products> counts;
    static int from;
   static int count;
    private callRetrofitPaging(Context context) {
        con = context;
    }

    public static callRetrofitPaging getInstance(Context conn, int co, int f) {
        if (!callCreatedReviews)
            RetrofitReviews = new callRetrofitPaging(conn);
        callCreatedReviews = true;
        count=co;
        from=f;
        return RetrofitReviews;
    }

    public void retrofitCall(final NetworkResponse< ArrayList<Products>> reviewsResponse) {
        RetroClientPaging retro = RetroClientPaging.getInstanceRetrofit(con);
        PagingService api =  retro.getProd();
        Call< ArrayList<Products>> call = api.getProduc(count,from);
        call.enqueue(new Callback< ArrayList<Products>>() {


            @Override
            public void onResponse(Call< ArrayList<Products>> call, Response< ArrayList<Products>> response) {
                if (response.isSuccessful()) {
                    counts = response.body();
                    reviewsResponse.onSucess( counts );


                }
            }

            @Override
            public void onFailure(Call < ArrayList<Products>> call, Throwable t) {
                Log.e(TAG, t.getMessage() );

            }


        });
    }
}
