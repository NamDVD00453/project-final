package com.vinamine.mc.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinamine.mc.OrderHistoryDetail;
import com.vinamine.mc.R;
import com.vinamine.mc.config.Config;
import com.vinamine.mc.rest.transaction.Data;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<Data> transactionList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public TransactionAdapter(List<Data> transactionList, Context mContext) {
        this.transactionList = transactionList;
        mLayoutInflater = LayoutInflater.from(mContext);;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        final View item = mLayoutInflater.inflate(R.layout.transaction_list_row, viewGroup, false);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.transactionData = transactionList.get((Integer) v.getTag());
                System.out.println("Clicked: " + i + " - " +transactionList.get((Integer) v.getTag()).getCode());
                Intent intent = new Intent(mContext, OrderHistoryDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                mContext.startActivity(intent);
            }
        });
        System.out.println("Holder created!");
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Data data = transactionList.get(i);
        viewHolder.tvOrderDate.setText(data.getDay());
        viewHolder.tvOrderId.setText(data.getId() + "-" + data.getCodeId());
        if (data.getStatus() == 0){
            viewHolder.tvOrderStatus.setText("Đang chờ");
            viewHolder.tvOrderStatus.setTextColor(Color.parseColor("#FF9800"));
        } else {
            viewHolder.tvOrderStatus.setText("Đã hoàn thành");
            viewHolder.tvOrderStatus.setTextColor(Color.parseColor("#00FF00"));
        }
        viewHolder.tvVoucherCode.setText("Voucher " + data.getCode());
        viewHolder.tvOrderStore.setText(data.getStoreName());
        viewHolder.tvOrderAddr.setText(data.getStoreAddress());
        viewHolder.tvOrderTime.setText(data.getTime() + " - Discount " + data.getSale());
        System.out.println("Bind holder: " + data.getId());

        viewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOrderDate, tvOrderId, tvOrderStatus, tvVoucherCode, tvOrderStore, tvOrderAddr, tvOrderTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvVoucherCode = itemView.findViewById(R.id.tvVoucherCode);
            tvOrderStore = itemView.findViewById(R.id.tvOrderStore);
            tvOrderAddr = itemView.findViewById(R.id.tvOrderAddr);
            tvOrderTime = itemView.findViewById(R.id.tvOrderTime);
        }
    }
}
