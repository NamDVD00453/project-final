package com.vinamine.mc;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vinamine.mc.config.Config;
import com.vinamine.mc.rest.RetroController;
import com.vinamine.mc.rest.VoucherForView;
import com.vinamine.mc.rest.transaction.Transaction;
import com.vinamine.mc.rest.transaction.TransactionResponse;
import com.vinamine.mc.rest.voucher.StoreAddress;
import com.vinamine.mc.rest.voucher.VoucherResponse;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    public static VoucherResponse voucherResponse;
    VoucherForView voucher;
    TextView tvStore, tvName;
    ImageView ivImage, ivBrand;
    EditText etDate, etNote;
    public static TransactionResponse transactionResponse;

    public static TextView tvDesc;
    public static Spinner spSlot, spTime, spAddress;
    public static Context thisContext;
    public static List<String> listAddr = new ArrayList<>();
    public static ArrayAdapter<String> addressAdapter;
    static Activity activity;
    static HashMap<String, Integer> addressIdMapping = new HashMap<>();

    public static void reloadVoucher() {
        tvDesc.setText(voucherResponse.getData().getDescription());
        addressAdapter.clear();
        listAddr = new ArrayList<>();
        addressIdMapping = new HashMap<>();

        for (StoreAddress s : voucherResponse.getData().getStoreAddress()
        ) {
            System.out.println("-----");
            System.out.println("GET: " + s.getAddress());
            listAddr.add(s.getAddress());
            addressIdMapping.put(s.getAddress(), s.getId());
        }
        System.out.println(listAddr.size());
        addressAdapter.addAll(listAddr);
        addressAdapter.notifyDataSetChanged();

        for (String addr : listAddr
        ) {
            System.out.println("LIST: " + addr);
        }

    }

    public static void success() {
        Intent intent = new Intent(thisContext, OrderHistoryDetail.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_detail);
        thisContext = getApplicationContext();
        int index = Integer.parseInt(getIntent().getStringExtra("voucherIndex"));

        voucher = MainActivity.voucherForViewList.get(index);

        System.out.println(index);
        System.out.println(voucher.getName());
        tvStore = findViewById(R.id.tvStore);
        tvStore.setText(voucher.getStore() + ": " + voucher.getPercent() + "% OFF");
        tvName = findViewById(R.id.tvName);
        tvName.setText(voucher.getName());

        ivImage = findViewById(R.id.ivImage);
        ivBrand = findViewById(R.id.ivBrand);
        ivImage.setImageBitmap(voucher.getImage());
        ivBrand.setImageBitmap(voucher.getIcon());

        tvDesc = findViewById(R.id.tvDesc);
        spSlot = findViewById(R.id.spSlot);
        spTime = findViewById(R.id.spTime);
        spAddress = findViewById(R.id.spAddress);

        etDate = findViewById(R.id.etDate);
        Date date=new Date(System.currentTimeMillis());
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
        String dateText = df2.format(date);
        etDate.setText(dateText);
        etDate.setEnabled(false);
        etNote = findViewById(R.id.etNote);

        addressAdapter = new ArrayAdapter(thisContext, android.R.layout.simple_spinner_dropdown_item, listAddr);
        addressAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spAddress.setAdapter(addressAdapter);

        List<String> listSlot = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            if (i == 1) {
                listSlot.add(i + " slot");
            } else listSlot.add(i + " slots");

        }
        ArrayAdapter<String> slotAdapter = new ArrayAdapter(thisContext, android.R.layout.simple_spinner_dropdown_item, listSlot);
        slotAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spSlot.setAdapter(slotAdapter);

        List<String> listTime = new ArrayList<>();
        listTime.add("09:00");
        listTime.add("09:30");
        listTime.add("10:00");
        listTime.add("10:30");
        listTime.add("11:00");
        listTime.add("11:30");
        listTime.add("12:00");
        listTime.add("12:30");
        listTime.add("1:00");
        listTime.add("1:30");

        ArrayAdapter<String> timeAdapter = new ArrayAdapter(thisContext, android.R.layout.simple_spinner_dropdown_item, listTime);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spTime.setAdapter(timeAdapter);

        RetroController retroController = new RetroController();
        retroController.getVoucherById(Integer.parseInt(voucher.getId()));
    }

    public void handleTransaction(View view) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String accountId = sharedPreferences.getString("accountId", "0");
        String token = sharedPreferences.getString("token", "0");

        System.out.println("SharePref: " + accountId + " - " + token);

        if ((!accountId.equalsIgnoreCase("0")) && (!token.equalsIgnoreCase("0")) && Config.ISLOGGED) {
            Transaction transaction = new Transaction();
            transaction.setAccountId(Integer.valueOf(accountId));
            transaction.setStoreId(voucherResponse.getData().getStoreId());
            transaction.setStoreAddressId(addressIdMapping.get(spAddress.getSelectedItem().toString()));
            transaction.setVoucherId(voucherResponse.getData().getId());
            transaction.setAdults(Integer.valueOf(spSlot.getSelectedItem().toString().substring(0, 1)));
            transaction.setChildren(0);
            transaction.setTime(spTime.getSelectedItem().toString());
            transaction.setDay(etDate.getText().toString());
            transaction.setDescription(etNote.getText().toString());
            Gson gson = new Gson();
            System.out.println(gson.toJson(transaction));

            RetroController retroController = new RetroController();
            retroController.handlTransaction(transaction);

        } else {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    public void handleDatePicker(View view) {
        findViewById(R.id.main_detail).post(new Runnable() {
            @Override
            public void run() {
                final DecimalFormat decimalFormat = new DecimalFormat("00");

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                final DatePickerDialog datePickerDialog = new DatePickerDialog(DetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etDate.setText(decimalFormat.format(dayOfMonth) + "/" + decimalFormat.format(monthOfYear) + "/" + year);
                    }
                }, year, month, day);


                String startTime = voucher.getStartTime();
                String endTime = voucher.getEndTime();

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                try {
                    Date date = sdf.parse(startTime);
                    datePickerDialog.getDatePicker().setMinDate(date.getTime());
                    date = sdf.parse(endTime);
                    datePickerDialog.getDatePicker().setMaxDate(date.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                datePickerDialog.show();
            }
        });
    }
}
