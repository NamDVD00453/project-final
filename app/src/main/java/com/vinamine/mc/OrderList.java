package com.vinamine.mc;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vinamine.mc.rest.RetroController;
import com.vinamine.mc.rest.transaction.Data;
import com.vinamine.mc.rest.transaction.TransactionListResponse;
import com.vinamine.mc.util.TransactionAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderList extends AppCompatActivity {

    static Context thisContext;
    private static TransactionAdapter transactionAdapter;
    static RecyclerView rvTransList;

    public static void success(TransactionListResponse transactionListResponse) {
        List<Data> listTransaction = transactionListResponse.getDatas();
        Collections.sort(listTransaction, new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });

        transactionAdapter = new TransactionAdapter(transactionListResponse.getDatas(), thisContext);
        rvTransList.setAdapter(transactionAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        thisContext = getApplicationContext();

        rvTransList = findViewById(R.id.rvTransList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());;
        rvTransList.setLayoutManager(layoutManager);

        RetroController retroController = new RetroController();
        retroController.getAllOrderHistory();
    }

}
