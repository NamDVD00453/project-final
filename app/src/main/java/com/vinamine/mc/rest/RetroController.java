package com.vinamine.mc.rest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.vinamine.mc.DetailActivity;
import com.vinamine.mc.LoginActivity;
import com.vinamine.mc.MainActivity;
import com.vinamine.mc.OrderList;
import com.vinamine.mc.RegisterActivity;
import com.vinamine.mc.config.Config;
import com.vinamine.mc.entity.RegisterBody;
import com.vinamine.mc.entity.RegisterResponse;
import com.vinamine.mc.rest.error.ErrorBody;
import com.vinamine.mc.rest.login.LoginResponse;
import com.vinamine.mc.rest.login.LoginUser;
import com.vinamine.mc.rest.transaction.Transaction;
import com.vinamine.mc.rest.transaction.TransactionListResponse;
import com.vinamine.mc.rest.transaction.TransactionResponse;
import com.vinamine.mc.rest.voucher.StoreAddress;
import com.vinamine.mc.rest.voucher.VoucherResponse;
import com.vinamine.mc.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroController {

    public static List<Voucher> listVouchers = new ArrayList<>();
    RegisterResponse registerResponse = null;
    LoginResponse loginResponse = null;

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
                System.out.println("Calling success ! ");
                if (response.isSuccessful()){
                    System.out.println("Register success ...");
                    if (response.body() != null){
                        registerResponse = response.body();
                        System.out.println(registerResponse.getData().getUsername());
                        RegisterActivity.success(registerResponse);
                    }
                } else {
//                    System.out.println(response.body().getMessage());
                    Gson gson = new Gson();
                    System.out.println(response.code() + " Error register ...");
                    try {
                        ErrorBody errorBody = gson.fromJson(response.errorBody().string(), ErrorBody.class);
                        System.out.println(errorBody.getMessage());
                        RegisterActivity.error(errorBody.getError());
                    } catch (IOException e) {
                        e.printStackTrace();
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

    public void getAllVouchers(){
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
                        MainActivity.loadAllRv(listVouchers);
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

            }
        });
    }

    public void handlLogin(final LoginUser loginUser) {
        System.out.println("Handle Login ...");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<LoginResponse> call = api.login(loginUser);

        System.out.println("Build calling ....");

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                System.out.println("Calling success ! ");
                if (response.isSuccessful()){
                    System.out.println("Login success ...");
                    if (response.body() != null){
                        loginResponse = response.body();
                        System.out.println(loginResponse.getData().getAccount().getUsername());
                        System.out.println(loginUser.getPassword());
                        Util.token = loginResponse.getData().getCredential().getAccessToken();
                        System.out.println("TOKEN: " + Util.token);
                        System.out.println(new Gson().toJson(loginResponse));
                        Config.ISLOGGED = true;
                        Config.aLoginResponse = loginResponse;
                        LoginActivity.success(loginResponse);
                    }
                } else {
                    Gson gson = new Gson();
                    try {
                        ErrorBody errorBody = gson.fromJson(response.errorBody().string(), ErrorBody.class);
                        System.out.println(errorBody.getMessage());
                        System.out.println(loginUser.getUsername());
                        System.out.println(loginUser.getPassword());
                        LoginActivity.error(errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                System.out.println("Login fail");
                System.out.println(t.getMessage());
            }
        });
        System.out.println("Calling done .");
    }

    public void getVoucherById(int id){
        final int thisId = id;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<VoucherResponse> call = api.getVoucherById(id);
        call.enqueue(new Callback<VoucherResponse>() {
            @Override
            public void onResponse(Call<VoucherResponse> call, Response<VoucherResponse> response) {
                if (response.isSuccessful()){
                    System.out.println("Get Voucher id: " + thisId + " success");
                    if (response.body() != null){
                        DetailActivity.voucherResponse = response.body();
                        DetailActivity.reloadVoucher();
                    }
                } else {
                    System.out.println("Error getting voucher.");
                }
            }

            @Override
            public void onFailure(Call<VoucherResponse> call, Throwable t) {
                System.out.println("get Voucher id: " + thisId + " failed!");
                System.out.println();
            }
        });
    }

    public void handlTransaction(final Transaction transaction) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.getAppContext());
        final String token = sharedPreferences.getString("token", "0");
        System.out.println(token);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .addHeader("Authorization", token)
                        .addHeader("Content-Type", "application/json")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<TransactionResponse> call = api.createTransaction(transaction);
        call.enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                if (response.isSuccessful()){
                    System.out.println("Transaction Created!");
                    if (response.body() != null){
                        TransactionResponse transactionResponse = response.body();
                        System.out.println(new Gson().toJson(transactionResponse));
                        DetailActivity.transactionResponse = transactionResponse;
                        DetailActivity.success();
                    }
                } else {
                    System.out.println("ERROR " + response.code() + ": " + new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
                System.out.println("Transaction create failed!");
            }
        });
    }

    public void getAllOrderHistory() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.getAppContext());
        final String token = sharedPreferences.getString("token", "0");
        System.out.println(token);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .addHeader("Authorization", token)
                        .addHeader("Content-Type", "application/json")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.JSONURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<TransactionListResponse> call = api.getTransList(Config.aLoginResponse.getData().getAccount().getEmail());
        call.enqueue(new Callback<TransactionListResponse>() {
            @Override
            public void onResponse(Call<TransactionListResponse> call, Response<TransactionListResponse> response) {
                if (response.isSuccessful()){
                    System.out.println("Transaction list loaded!");
                    if (response.body() != null){
                        TransactionListResponse transactionListResponse = response.body();
                        System.out.println("Total order: " + transactionListResponse.getDatas().size());
                        OrderList.success(transactionListResponse);
                    }
                } else {
                    System.out.println("ERROR " + response.code() + ": " + new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<TransactionListResponse> call, Throwable t) {
                System.out.println("Transaction create failed!");
            }
        });
    }
}
