package com.vinamine.mc.rest;

import com.vinamine.mc.entity.RegisterBody;
import com.vinamine.mc.entity.RegisterResponse;
import com.vinamine.mc.rest.login.LoginResponse;
import com.vinamine.mc.rest.login.LoginUser;
import com.vinamine.mc.rest.transaction.Transaction;
import com.vinamine.mc.rest.transaction.TransactionListResponse;
import com.vinamine.mc.rest.transaction.TransactionResponse;
import com.vinamine.mc.rest.voucher.VoucherResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    String JSONURL = "http://13.76.164.246:8080/";
    @GET("unauthentic/stores/store/vouchers")
    Call<RetroData> getData();

    @POST("unauthentic/account/register")
    Call<RegisterResponse> register(@Body RegisterBody body);

    @POST("unauthentic/account/login")
    Call<LoginResponse> login(@Body LoginUser loginUser);

    @GET("/unauthentic/stores/store/vouchers/voucher/-/{id}")
    Call<VoucherResponse> getVoucherById(@Path("id") int id);

    @POST("/api/guest/transactions/transaction")
    Call<TransactionResponse> createTransaction(@Body Transaction transaction);

    @GET("/api/guest/{email}/history/transactions")
    Call<TransactionListResponse> getTransList(@Path("email") String email);
}
