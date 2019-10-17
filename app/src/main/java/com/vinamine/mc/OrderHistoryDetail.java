package com.vinamine.mc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vinamine.mc.config.Config;
import com.vinamine.mc.rest.transaction.Data;
import com.vinamine.mc.rest.transaction.TransactionResponse;

public class OrderHistoryDetail extends AppCompatActivity {

    TextView tvId, tvGuestName, tvGuestPhone, tvEmail, tvCode, tvSale, tvStore, tvStoreAddress, tvDay, tvTime, tvAdults, tvChildren, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_detail);

        System.out.println("Load new Order history");

        tvId = findViewById(R.id.tvTransId);

        tvGuestName = findViewById(R.id.tvGuestName);
        tvGuestPhone = findViewById(R.id.tvGuestPhone);
        tvEmail = findViewById(R.id.tvEmail);

        tvCode = findViewById(R.id.tvCode);
        tvSale = findViewById(R.id.tvSale);
        tvStore = findViewById(R.id.tvStoreName);
        tvStoreAddress = findViewById(R.id.tvStoreAddress);

        tvDay = findViewById(R.id.tvDay);
        tvTime = findViewById(R.id.tvTime);
        tvAdults = findViewById(R.id.tvAdults);
        tvChildren = findViewById(R.id.tvChildren);
        tvDescription = findViewById(R.id.tvDescription);

        Data data = null;

        if (Config.transactionData != null){
            data = Config.transactionData;
            System.out.println("Load config");
            System.out.println(data.getCode());
            printInfo(data);
        } else if (DetailActivity.transactionResponse != null){
            data = DetailActivity.transactionResponse.getData();
            System.out.println("Load detail");
            System.out.println(data.getCode());
            printInfo(data);
        }
    }

    private void printInfo(Data data) {

        tvId.setText("#" + data.getId().toString() + data.getCodeId());

        tvGuestName.setText(data.getGuestName());
        tvGuestPhone.setText(data.getGuestPhone());
        tvEmail.setText(data.getGuestEmail());

        tvCode.setText(data.getCode());
        tvSale.setText(data.getSale() + " OFF");
        tvStore.setText(data.getStoreName());
        tvStoreAddress.setText(data.getStoreAddress());

        tvDay.setText(data.getDay());
        tvTime.setText(data.getTime());

        if (data.getAdults().equalsIgnoreCase("0") || data.getAdults().equalsIgnoreCase("1")){
            tvAdults.setText(data.getAdults() + " slot");
        } else tvAdults.setText(data.getAdults() + " slots");

        if (data.getChildren().equalsIgnoreCase("0") || data.getChildren().equalsIgnoreCase("1")){
            tvChildren.setText(data.getChildren() + " slot");
        } else tvChildren.setText(data.getChildren() + " slots");

        tvDescription.setText(data.getDescription());

    }

    public void handleBack(View view) {
        DetailActivity.transactionResponse = null;
        Config.transactionData = null;
        finish();
    }
}
