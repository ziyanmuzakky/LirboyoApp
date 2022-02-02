package com.example.lirboyoapp.networking

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface BaseServiceAPI {
    @Headers("Content-Type:application/json")
    @GET("posts")
    fun getItemData(): Call<JsonArray>
}