package com.example.androidregisterandlogin;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    public static final String BASE_URL = "https://techdudesolutions.com/android_register_login/";
    public static Retrofit retrofit;
    public static final String BASE = BASE_URL.trim();


    public static Retrofit getApiClient() {

  /*      Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(BASE_URL));
        reader.setLenient(true);
         retrofit = gson.fromJson(reader,T);*/


        if(retrofit==null){
            retrofit = new Retrofit.Builder().
                    baseUrl(BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


        }
        return  retrofit;

    }





}

