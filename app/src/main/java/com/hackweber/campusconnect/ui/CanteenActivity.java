package com.hackweber.campusconnect.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.adapter.CanteenAdapter;
import com.hackweber.campusconnect.dao.CanteenStorage;

public class CanteenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen);
        final GridView canteenGridView = findViewById(R.id.gridview_canteen);
        CanteenStorage canteenStorage = new CanteenStorage();
        canteenStorage.fetchCanteensData();
        canteenGridView.setAdapter(new CanteenAdapter(this,canteenStorage.getCanteensData()));
    }

}