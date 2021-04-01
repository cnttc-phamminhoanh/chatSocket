package com.example.chatsocket;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
public class DangNhapActivity extends AppCompatActivity {
    private Button btnDangNhap;
    private EditText edtUserName;
    private EditText edtPassWord;
    private Socket mSocket;
    Intent intentLogged;
    JSONObject object = new JSONObject();
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        url = intent.getStringExtra("url1");
        try {
            mSocket = IO.socket(url);
            mSocket.connect();
            mSocket.on("checkLogin",onNewMessage);
        }catch (Exception e){}

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        btnDangNhap = findViewById(R.id.btnDangKy);
        edtUserName = findViewById(R.id.edtUserNameDN);
        edtPassWord = findViewById(R.id.edtPassWordDN);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    object.put("username",edtUserName.getText().toString().trim());
                    object.put("password",edtPassWord.getText().toString().trim());
                }
                catch (Exception e){ }
                mSocket.emit("client-gui-account",object);
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
                    String check;
                    try {
                        check = data.getString("noidung");
                        if(check == "true") {
                            intentLogged = new Intent(getApplicationContext(), LoggedActivity.class);
                            intentLogged.putExtra("url2",url);
                            startActivity(intentLogged);
//                            Toast.makeText(getApplicationContext(),"Login successful",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),check,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) { return; }

                }
            });
        }
    };
}