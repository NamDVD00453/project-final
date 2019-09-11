package com.vinamine.mc.config;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Utility implements DownloadBitmapInterface{

    public Bitmap finishedBitmap = null;

    public void getFinishedBitmap(String url) {
        AsyncDownload asyncDownload = new AsyncDownload();
        asyncDownload.delegate = this;
        asyncDownload.execute(url);
    }

    @Override
    public void processFinish(Bitmap output) {
        finishedBitmap = output;
    }


}
