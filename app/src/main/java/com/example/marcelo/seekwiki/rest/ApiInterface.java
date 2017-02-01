package com.example.marcelo.seekwiki.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Marcelo on 31/01/2017.
 */

public interface ApiInterface {

    @GET("/w/api.php")
    Call<Object> getWikipedia(@Query("action") String action,@Query("search") String search,@Query("limit") String limit);
}

