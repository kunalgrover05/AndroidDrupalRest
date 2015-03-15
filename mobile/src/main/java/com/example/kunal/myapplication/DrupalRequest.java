package com.example.kunal.myapplication;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

public interface DrupalRequest {
    @Headers("Accept:  application/json")
    @GET("/node/{id}")
    DrupalResponse requester(
            @Path("id") String id
    );
}