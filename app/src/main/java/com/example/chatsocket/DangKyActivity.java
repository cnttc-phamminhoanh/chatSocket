package com.example.chatsocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class DangKyActivity extends AppCompatActivity {

    private Button btnDangKy;
    private EditText edtUserName;
    private EditText edtPassWord;
    private Socket mSocket;
    JSONObject object = new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url1");
        try {
            mSocket = IO.socket(url);
            mSocket.connect();
            mSocket.on("checkSignin",onNewMessage);
        }catch (Exception e){}
        btnDangKy = findViewById(R.id.btnDangKy);
        edtUserName = findViewById(R.id.edtUserNameDN);
        edtPassWord = findViewById(R.id.edtPassWordDN);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    object.put("username",edtUserName.getText().toString().trim());
                    object.put("password",edtPassWord.getText().toString().trim());
                }
                catch (Exception e){ }
                mSocket.emit("client-send-account-signin",object);
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
                            Toast.makeText(getApplicationContext(),"Sign in successful",Toast.LENGTH_SHORT).show();
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