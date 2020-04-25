package com.example.androidregisterandlogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("recylerviewretrofit.php")
    Call<List<ListItem>> getList(@Query("key") String keyword);

}