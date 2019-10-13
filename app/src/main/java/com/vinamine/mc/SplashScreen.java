package com.vinamine.mc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vinamine.mc.config.AsyncDownload;
import com.vinamine.mc.config.Config;
import com.vinamine.mc.config.DownloadBitmapInterface;
import com.vinamine.mc.config.Utility;
import com.vinamine.mc.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final ProgressBar pbLoad = findViewById(R.id.pbLoad);
        pbLoad.setProgress(1);
        pbLoad.setMax(100);
        System.out.println("TEST OK!");
        CountDownTimer countDownTimer = new CountDownTimer(1000 * Config.SPLASH_INTERVAL, 10 * Config.SPLASH_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                int current = pbLoad.getProgress();
                pbLoad.setProgress(current + 1);
            }

            @Override
            public void onFinish() {
                pbLoad.setProgress(100);
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
            }
        };
        countDownTimer.start();

    }



}
