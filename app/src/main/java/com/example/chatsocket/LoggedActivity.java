package com.example.chatsocket;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
public class LoggedActivity extends AppCompatActivity {
    private Socket mSocket;
    private Button btnChatGroup;
    private ListView lvUsername;
    ArrayList<String> arrUsername;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);
        lvUsername = findViewById(R.id.lvUsername);
        btnChatGroup = findViewById(R.id.btnChatGroup);
        Intent intent = getIntent();
        url = intent.getStringExtra("url2");
        try {
            mSocket = IO.socket(url);
            mSocket.connect();
            mSocket.on("users",onNewMessage);
        }catch (Exception e){}
        btnChatGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChatGroup = new Intent(LoggedActivity.this,ChatGroupActivity.class);
                intentChatGroup.putExtra("url",url);
                startActivity(intentChatGroup);
            }
        });
    }
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try{
                        JSONArray ar = data.getJSONArray("noidung");
                        arrUsername = new ArrayList<String>();
                        for(int i=0;i<ar.length();i++){
                            String name = ar.getString(i);
                            arrUsername.add(name);
                        }
                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrUsername) {
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent){
                                View view = super.getView(position, convertView, parent);
                                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                                tv.setTextColor(Color.BLACK);
                                return view;
                            }
                        };
                        lvUsername.setAdapter(adapter);
                    }
                    catch (JSONException e){return;}
                }
            });
        }
    };
}