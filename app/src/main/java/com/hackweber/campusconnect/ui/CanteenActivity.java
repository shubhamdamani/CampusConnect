package com.hackweber.campusconnect.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.adapter.CanteenAdapter;
import com.hackweber.campusconnect.dao.CanteenStorage;
import com.hackweber.campusconnect.model.Canteen;

public class CanteenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen);
        final GridView canteenGridView = findViewById(R.id.gridview_canteen);
        CanteenStorage canteenStorage = new CanteenStorage();
        canteenStorage.setCanteensAdapter(canteenGridView,this);
//        canteenGridView.setAdapter(new CanteenAdapter(this,canteenStorage.getCanteensData()));
        canteenGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Canteen canteen = (Canteen) canteenGridView.getItemAtPosition(position);
                Intent activity = new Intent(CanteenActivity.this,FoodOrderActivity.class);
                activity.putExtra("uid",canteen.getUniqueID());
                startActivity(activity);


            }
        });
    }

}