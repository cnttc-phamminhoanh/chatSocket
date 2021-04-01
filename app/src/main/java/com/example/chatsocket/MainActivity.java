package com.example.chatsocket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Button btnDangNhap;
    Button btnDangky;
    Intent intenDN;
    Intent intenDK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
//        Toast.makeText(MainActivity.this,url,Toast.LENGTH_SHORT).show();
        btnDangky = findViewById(R.id.btnDK);
        btnDangNhap = findViewById(R.id.btnDN);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intenDN = new Intent(MainActivity.this, DangNhapActivity.class);
                intenDN.putExtra("url1",url);
                startActivity(intenDN);
             }
        });
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intenDK = new Intent(MainActivity.this, DangKyActivity.class);
                intenDK.putExtra("url1",url);
                startActivity(intenDK);
            }
        });
    }
}