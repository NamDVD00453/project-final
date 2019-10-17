package com.vinamine.mc.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vinamine.mc.DetailActivity;
import com.vinamine.mc.R;
import com.vinamine.mc.rest.VoucherForView;

import java.util.List;

public class VoucherForViewAdapter extends RecyclerView.Adapter<VoucherForViewAdapter.ViewHolder> {

    private List<VoucherForView> voucherViewList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public VoucherForViewAdapter(Context context,List<VoucherForView> voucherViewList) {
        this.voucherViewList = voucherViewList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);

    }

    @Override
    public VoucherForViewAdapter.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        final View item = mLayoutInflater.inflate(R.layout.voucher_list_row,viewGroup,false);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("voucherIndex",v.getTag().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        System.out.println("Holder created!");
        return new VoucherForViewAdapter.ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherForViewAdapter.ViewHolder viewHolder, int i) {

        VoucherForView voucherForView = voucherViewList.get(i);
        viewHolder.tvBrand.setText(voucherForView.getName());
        viewHolder.tvPercent.setText(voucherForView.getPercent().toString());
        viewHolder.ivBrand.setImageBitmap(voucherForView.getIcon());
        viewHolder.ivImage.setImageBitmap(voucherForView.getImage());
        viewHolder.itemView.setTag(i);
        System.out.println("Data binding ...");

    }

    @Override
    public int getItemCount() {
        return voucherViewList.size();
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
