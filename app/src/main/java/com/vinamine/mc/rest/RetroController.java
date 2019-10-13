package com.vinamine.mc.rest;

import android.util.Log;

import com.vinamine.mc.RegisterActivity;
import com.vinamine.mc.entity.RegisterBody;
import com.vinamine.mc.entity.RegisterResponse;
import com.vinamine.mc.entity.RegisterUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroController {

    List<Voucher> listVouchers = new ArrayList<>();
    RegisterResponse registerResponse = null;

    public RegisterResponse handlRegister(RegisterBody registerBody){
        System.out.println("Handle Register ...");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<RegisterResponse> call = api.register(registerBody);

        System.out.println("Build calling ....");

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()){
                    System.out.println("Register success ...");
                    if (response.body() != null){
                        registerResponse = response.body();
                        System.out.println(registerResponse.getData().getUsername());
                        RegisterActivity.success(registerResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                System.out.println("Register fail");
                System.out.println(t.getMessage());
            }
        });
        System.out.println("Calling done .");
        return registerResponse;
    }

    public List<Voucher> getAllVouchers(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<RetroData> call = api.getData();
        System.out.println("Calling method");


        call.enqueue(new Callback<RetroData>() {
            @Override
            public void onResponse(Call<RetroData> call, Response<RetroData> response) {
                if (response.isSuccessful()) {
                    System.out.println("Calling success");
                    if (response.body() != null) {
                        RetroData retroData = response.body();
                        listVouchers = retroData.getData();
                        for (Voucher voucher: listVouchers
                             ) {
                            System.out.println(voucher.getName());
                        }
                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");
                    }
                } else {
                    System.out.println("Calling error");
                }
            }

            @Override
            public void onFailure(Call<RetroData> call, Throwable t) {
                System.out.println("Calling fail");
                System.out.println(t.getMessage());
                System.out.println(t.getCause().getLocalizedMessage());
            }
        });
        return listVouchers;
    }
}
