package com.example.chatsocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import io.socket.emitter.Emitter;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import io.socket.client.IO;
import io.socket.client.Socket;

public class MainActivity2 extends AppCompatActivity {
    private Socket mSocket;
    private Button btnConnect;
    private EditText edtUrl;
    Intent intent;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        edtUrl = findViewById(R.id.url);
        btnConnect = findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = edtUrl.getText().toString();
//                Toast.makeText(MainActivity2.this,url,Toast.LENGTH_SHORT).show();
                try {
                    mSocket = IO.socket(url);
                    mSocket.connect();
                }
                catch (Exception e) {}
                mSocket.on("checkAlive",onNewMessage);
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
                        if(check == "true"){
                            intent = new Intent(MainActivity2.this,MainActivity.class);
                            intent.putExtra("url",url);
                            startActivity(intent);
                        }
                    } catch (JSONException e) { return; }
                }
            });
        }
    };
}