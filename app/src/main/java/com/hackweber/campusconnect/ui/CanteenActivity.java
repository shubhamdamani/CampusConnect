package com.hackweber.campusconnect.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.adapter.CanteenAdapter;
import com.hackweber.campusconnect.dao.CanteenStorage;
import com.hackweber.campusconnect.model.Canteen;
import com.hackweber.campusconnect.ui.CleanlinessPackage.Cleanliness;
import com.hackweber.campusconnect.ui.LostAndFound.LostAndFound;
import com.hackweber.campusconnect.ui.UserProfilePackage.UserProfile;

public class CanteenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen);
        final GridView canteenGridView = findViewById(R.id.gridview_canteen);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        Intent i=new Intent(CanteenActivity.this,MainActivity.class);
                        startActivity(i);
                        break;
                    case R.id.lost_found:
                        Intent i2=new Intent(CanteenActivity.this, LostAndFound.class);
                        startActivity(i2);
                        break;
                    case R.id.report:
                        Intent i3=new Intent(CanteenActivity.this, Cleanliness.class);
                        startActivity(i3);
                        break;
                    case R.id.profile:
                        Intent i4=new Intent(CanteenActivity.this, UserProfile.class);
                        startActivity(i4);

                }
                return false;
            }
        });
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