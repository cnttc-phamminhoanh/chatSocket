package com.example.chatsocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatGroupActivity extends AppCompatActivity {
    private Socket mSocket;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_group);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        Toast.makeText(getApplicationContext(),url,Toast.LENGTH_SHORT).show();
        try {
            mSocket = IO.socket(url);
            mSocket.connect();
            mSocket.on("",onNewMessage);
        }catch (Exception e){}
    }
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String noidung;
                    try {
                        noidung = data.getString("username");
                    } catch (JSONException e) { return; }
                }
            });
        }
    };
}