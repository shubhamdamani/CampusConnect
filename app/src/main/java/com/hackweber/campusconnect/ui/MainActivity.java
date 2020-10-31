package com.hackweber.campusconnect.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.hackweber.campusconnect.ui.LostAndFound.LostAndFound;
import com.hackweber.campusconnect.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if(auth.getCurrentUser()==null)
        {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LostAndFound.class);
                startActivity(intent);
            }
        });

    }

    private void init() {
        auth = FirebaseAuth.getInstance();
        btn = findViewById(R.id.lost_and_found);
    }
}