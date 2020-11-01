package com.hackweber.campusconnect.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.hackweber.campusconnect.R;

public class eventDetail extends AppCompatActivity {


    private TextView title,body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        init();

        String bodyString = getIntent().getStringExtra("body");
        String titleString = getIntent().getStringExtra("title");

        title.setText(titleString);
        body.setText(bodyString);


    }

    private void init() {
        title = findViewById(R.id.event_title);
        body = findViewById(R.id.event_body);
    }
}