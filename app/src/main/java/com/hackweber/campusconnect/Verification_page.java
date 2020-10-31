package com.hackweber.campusconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Verification_page extends AppCompatActivity {

    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_page);
        init();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Verification_page.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void init() {
        login = findViewById(R.id.verification_login);
    }
}