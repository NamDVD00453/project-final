package com.vinamine.mc;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.vinamine.mc.entity.RegisterResponse;
import com.vinamine.mc.entity.RegisterUser;
import com.vinamine.mc.rest.RetroController;

public class RegisterActivity extends AppCompatActivity {

    EditText etPhone, etEmail, etFullname, etPassword, etPasswordRepeat;
    CheckBox cbAccept;
    public static Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etFullname = findViewById(R.id.etFullname);
        etPassword = findViewById(R.id.etPassword);
        etPasswordRepeat = findViewById(R.id.etPasswordRepeat);
        context = this;
    }

    public void handleRegister(View view) {
//        if (!cbAccept.isChecked()){
//            Toast.makeText(this, "You must accept the term", Toast.LENGTH_SHORT).show();
//        } else {
//            RegisterUser registerUser = new RegisterUser();
//            registerUser.setPhone(etPhone.getText().toString());
//            registerUser.setEmail(etEmail.getText().toString());
//            registerUser.setPassword(etPassword.getText().toString());
//            registerUser.setRePassword(etPasswordRepeat.getText().toString());
//            registerUser.setFullName(etFullname.getText().toString());
//            RetroController retroController = new RetroController();
//            RegisterResponse response = retroController.registerResponse(registerUser.toBody());
//        }
        RegisterUser registerUser = new RegisterUser();
        registerUser.setPhone(etPhone.getText().toString());
        registerUser.setEmail(etEmail.getText().toString());
        registerUser.setPassword(etPassword.getText().toString());
        registerUser.setRePassword(etPasswordRepeat.getText().toString());
        registerUser.setFullName(etFullname.getText().toString());
        RetroController retroController = new RetroController();
        System.out.println("Start calling....");
        RegisterResponse response = retroController.handlRegister(registerUser.toBody());
    }

    public static void success(RegisterResponse registerResponse) {
        Toast.makeText(context, "Register Success! " + registerResponse.getData().getUsername(), Toast.LENGTH_SHORT).show();
    }
}
