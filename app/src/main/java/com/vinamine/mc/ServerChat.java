package com.vinamine.mc;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.vinamine.mc.config.ServerConnect;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.Principal;

public class ServerChat extends AppCompatActivity {

    private Socket socket;
    private LinearLayout layoutMsg;
    private ScrollView svChatContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_chat);
        layoutMsg = findViewById(R.id.layoutMsg);
        svChatContent = findViewById(R.id.svChatContent);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Thread running = new Thread(new ClientThread());
        running.start();
        final Button btSend = findViewById(R.id.btSend);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etMSg = findViewById(R.id.etMsg);
                String msg = etMSg.getText().toString();
                try {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(msg);
                    writer.newLine();
                    writer.flush();
                    View tvNewMsg = new TextView(ServerChat.this);
                    ((TextView) tvNewMsg).setText(msg);
                    layoutMsg.addView(tvNewMsg);
                    svChatContent.fullScroll(View.FOCUS_DOWN);
                    btSend.setEnabled(false);
                    Thread.sleep(3000);
                    btSend.setEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("Server-Error", "Error 1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class ClientThread implements Runnable {

        @Override
        public void run() {
            try {
                socket = new Socket("25.14.76.218",8800);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
