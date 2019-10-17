package com.vinamine.mc.config;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AsyncDownload extends AsyncTask<String, Void, Bitmap> {
    public DownloadBitmapInterface delegate = null;
    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap result = null;
        String url = urls[0];
        try {
            InputStream inputStream = new URL(url).openStream();
            result = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Utility-Debug", "Error while downloading image");
        }
        return result;
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        delegate.processFinish(bitmap);
    }


}
