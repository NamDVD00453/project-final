package com.vinamine.mc.config;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerConnect extends AsyncTask<String,Void,String> {
    private Socket socket = null;
    private BufferedWriter writer;
    @Override
    protected String doInBackground(String... strings) {
        try {
            socket = new Socket("127.0.0.1",8800);
            Log.d("Server-test","SERVER CONNECTED");
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Server-test","ERROR NULL");
        }
        return "ERROR";
    }

    @Override
    protected void onPostExecute(String s) {

    }

    public void sendMsg(Activity activity, String msg){
        try {
            writer.write(msg);
            writer.newLine();
            writer.flush();
            Toast.makeText(activity, "Message sended!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Cannot send!", Toast.LENGTH_SHORT).show();
        }
    }
}
