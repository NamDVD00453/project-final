package com.vinamine.mc;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.vinamine.mc.entity.RegisterResponse;
import com.vinamine.mc.entity.RegisterUser;
import com.vinamine.mc.rest.RetroController;

public class RegisterActivity extends AppCompatActivity {

    EditText etPhone, etEmail, etFullname, etPassword, etPasswordRepeat;
    CheckBox cbAccept;
    static Button btRegister;
    public static Context context;
    public static View view;

    public static void error(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG);
        btRegister.setEnabled(true);
        btRegister.setText("ĐĂNG KÝ TÀI KHOẢN");
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etFullname = findViewById(R.id.etFullname);
        etPassword = findViewById(R.id.etPassword);
        etPasswordRepeat = findViewById(R.id.etPasswordRepeat);
        btRegister = findViewById(R.id.btRegister);
        view = findViewById(R.id.registerLayout);
        setDefault();
        context = getApplicationContext();
    }

    public void setDefault(){
        etPhone.setText("0988345345");
        etEmail.setText("namdvd00453@fpt.edu.vn");
        etPassword.setText("123123aa");
        etPasswordRepeat.setText("123123aa");
        etFullname.setText("Full name hahaha");
    }

    public void handleRegister(View view) {
        RegisterUser registerUser = new RegisterUser();
        registerUser.setPhone(etPhone.getText().toString());
        registerUser.setEmail(etEmail.getText().toString());
        registerUser.setPassword(etPassword.getText().toString());
        registerUser.setRePassword(etPasswordRepeat.getText().toString());
        registerUser.setFullName(etFullname.getText().toString());

        System.out.println(registerUser.toString());
        RetroController retroController = new RetroController();
        System.out.println("Start calling....");
        btRegister.setEnabled(false);
        btRegister.setText("Đang kiểm tra ...");
        RegisterResponse response = retroController.handlRegister(registerUser.toBody());
    }

    public static void success(RegisterResponse registerResponse) {
        Toast.makeText(context, "Register Success! " + registerResponse.getData().getUsername(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("username", registerResponse.getData().getUsername());
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }
}
