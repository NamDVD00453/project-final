package com.vinamine.mc.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vinamine.mc.R;
import com.vinamine.mc.config.DownloadUtil;
import com.vinamine.mc.rest.Voucher;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.ViewHolder> {

    private List<Voucher> voucherList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public VoucherAdapter(Context context,List<Voucher> voucherList) {
        this.voucherList = voucherList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View item = mLayoutInflater.inflate(R.layout.voucher_list_row,viewGroup,false);
        System.out.println("Holder created!");
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Voucher voucher = voucherList.get(i);
        viewHolder.tvBrand.setText(voucher.getName());
        viewHolder.tvPercent.setText(voucher.getPercent().toString());
        DownloadUtil downloadUtil = new DownloadUtil();
        Bitmap img = downloadUtil.getBitmap(voucher.getIcon());
        viewHolder.ivBrand.setImageBitmap(img);
        img = downloadUtil.getBitmap(voucher.getImage());
        viewHolder.ivImage.setImageBitmap(img);
        System.out.println("Data binding ...");

    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivImage, ivBrand;
        public TextView tvBrand, tvPercent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            ivBrand = itemView.findViewById(R.id.ivBrand);
            tvBrand = itemView.findViewById(R.id.tvBrand);
            tvPercent = itemView.findViewById(R.id.tvPercent);

            System.out.println("Accept View Holder Data");
        }

    }
}
