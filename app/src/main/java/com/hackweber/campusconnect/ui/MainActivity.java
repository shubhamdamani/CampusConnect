package com.hackweber.campusconnect.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.hackweber.campusconnect.R;

public class MainActivity extends AppCompatActivity {


    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab=findViewById(R.id.order_food);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i5=new Intent(MainActivity.this,FoodOrderActivity.class);
                       startActivity(i5);
            }
        });

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        Intent i=new Intent(MainActivity.this,MainActivity.class);
                        startActivity(i);
                    case R.id.lost_found:
                        Intent i2=new Intent(MainActivity.this,MainActivity.class);
                        startActivity(i2);
//                    case R.id.order_food:
//                        Intent i5=new Intent(MainActivity.this,FoodOrderActivity.class);
//                        startActivity(i5);
                    case R.id.report:
                        Intent i3=new Intent(MainActivity.this,MainActivity.class);
                        startActivity(i3);
                    case R.id.profile:
                        Intent i4=new Intent(MainActivity.this,MainActivity.class);
                        startActivity(i4);

                }
                return false;
            }
        });

    }
}