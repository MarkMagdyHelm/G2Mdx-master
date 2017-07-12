package com.example.dev_2.g2mdx.Retrofit;

import com.example.dev_2.g2mdx.Retrofit.Models.Products;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dev-2 on 06/07/2017.
 */

public interface PagingService {
    @GET("new_products")
    Call<ArrayList<Products>> getProduc(
            @Query("count") int apiKey, @Query("from") int language


    );
}