package com.vinamine.mc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vinamine.mc.rest.RetroController;
import com.vinamine.mc.rest.error.ErrorBody;
import com.vinamine.mc.rest.login.LoginResponse;
import com.vinamine.mc.rest.login.LoginUser;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    static Button btLogin;
    static Button btRegister;
    private static Context context;
    public static View thisView;

    public static void success(LoginResponse loginResponse) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", loginResponse.getData().getCredential().getAccessToken());
        editor.putString("accountId", loginResponse.getData().getAccount().getId().toString());
        editor.putString("isLogged", "true");
        editor.putString("avatar", loginResponse.getData().getAccount().getAvatar());
        editor.commit();
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("loginUser", loginResponse);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void error(ErrorBody errorBody) {
        Snackbar.make(thisView, errorBody.getMessage(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        btLogin.setEnabled(true);
        btRegister.setEnabled(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        thisView = findViewById(R.id.loginView);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);
        if (intent.hasExtra("username")){
            System.out.println(intent.getStringExtra("username"));
            etEmail.setText(intent.getStringExtra("username"));
        }
    }

    public void handleLogin(View view) {

        if (etEmail.getText().length() == 0){
            Snackbar.make(view, "Vui lòng nhập Email", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else if (etPassword.getText().length() == 0){
            Snackbar.make(view, "Vui lòng nhập mật khẩu", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                md5.update(StandardCharsets.UTF_8.encode(etPassword.getText().toString()));
                String hashPassword = String.format("%032x", new BigInteger(1, md5.digest()));

                LoginUser loginUser = new LoginUser(etEmail.getText().toString(), hashPassword.toUpperCase(), "WEB");
                RetroController retroController = new RetroController();
                retroController.handlLogin(loginUser);

                btLogin.setEnabled(false);
                btRegister.setEnabled(false);

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void handleForgot(View view) {
    }
}
