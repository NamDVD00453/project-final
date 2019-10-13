package com.vinamine.mc.config;

import android.graphics.Bitmap;

public class DownloadUtil implements DownloadBitmapInterface {

    public static Bitmap bitmap = null;

    @Override
    public void processFinish(Bitmap output) {
        bitmap = output;
    }

    public void downloadImage(String url){
        AsyncDownload asyncDownload = new AsyncDownload();
        asyncDownload.delegate = this;
        asyncDownload.doInBackground(url);
    }

    public Bitmap getBitmap(String url){
        downloadImage(url);
        return bitmap;
    }
}
