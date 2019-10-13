package com.vinamine.mc.rest;

import com.vinamine.mc.entity.RegisterBody;
import com.vinamine.mc.entity.RegisterResponse;
import com.vinamine.mc.entity.RegisterUser;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    String JSONURL = "http://13.77.45.22:8080/";
    @GET("unauthentic/stores/store/vouchers")
    Call<RetroData> getData();

    @POST("unauthentic/account/register")
    Call<RegisterResponse> register(@Body RegisterBody body);

}
