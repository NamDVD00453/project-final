package com.vinamine.mc.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.vinamine.mc.MainActivity;
import com.vinamine.mc.rest.Voucher;
import com.vinamine.mc.rest.VoucherForView;
import com.vinamine.mc.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ItemLoad extends AsyncTask<Void, Integer, Void> {
    Voucher voucher;
    Bitmap icon = null, img = null;

    public ItemLoad(Voucher voucher) {
        this.voucher = voucher;
    }

    @Override
    protected void onPreExecute() {
        System.out.println("Prepare to download. " + voucher.getName());
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        System.out.println("Loading voucher: " + voucher.getName());
        String iconUrl = voucher.getIcon();
        String imgUrl = voucher.getImage();
        try {
            InputStream inputStream = new URL(iconUrl).openStream();
            icon = BitmapFactory.decodeStream(inputStream);
                inputStream = new URL(imgUrl).openStream();
                img = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Utility-Debug", "Error while downloading image");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        System.out.println("Finish loading voucher: " + voucher.getName());
        VoucherForView voucherForView = new VoucherForView();
        voucherForView.setId(voucher.getId().toString());
        voucherForView.setName(voucher.getName());
        voucherForView.setPercent(voucher.getPercent().toString());
        voucherForView.setStore(voucher.getStore());
        voucherForView.setIcon(icon);
        voucherForView.setImage(img);

        voucherForView.setStartTime(voucher.getStartDay());
        voucherForView.setEndTime(voucher.getExpiredDay());
        MainActivity.voucherForViewList.add(voucherForView);
        System.out.println("Reloading view list ...");
        MainActivity.reloadRv(Util.mainContext);
        super.onPostExecute(aVoid);
    }
}
