package com.hackweber.campusconnect.ui.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.hackweber.campusconnect.R;

public class VerificationPage extends AppCompatActivity {

    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_page);
        init();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerificationPage.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void init() {
        login = findViewById(R.id.verification_login);
    }
}