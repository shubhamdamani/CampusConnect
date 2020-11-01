package com.hackweber.campusconnect.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.adapter.NotificationAdapter;
import com.hackweber.campusconnect.model.NotificationsItem;
import com.hackweber.campusconnect.notifications.AdminPanel;
import com.hackweber.campusconnect.ui.Auth.Login;
import com.hackweber.campusconnect.ui.CleanlinessPackage.Cleanliness;
import com.hackweber.campusconnect.ui.LostAndFound.LostAndFound;
import com.hackweber.campusconnect.ui.UserProfilePackage.UserProfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private NotificationAdapter adapter;
    private List<NotificationsItem> list;

    private FloatingActionButton fab;
    private Button admin;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        admin=findViewById(R.id.admin);


        FirebaseMessaging.getInstance().subscribeToTopic("lost");
        FirebaseMessaging.getInstance().subscribeToTopic("found");
        FirebaseMessaging.getInstance().subscribeToTopic("events");
        init();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i5=new Intent(MainActivity.this,CanteenActivity.class);
                startActivity(i5);
            }
        });

        if(auth.getCurrentUser()==null)
        {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }


        fetchNotification();
    }

    private void fetchNotification() {
        databaseReference.child("Notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    NotificationsItem obj = ds.getValue(NotificationsItem.class);
                    list.add(obj);
                }
                Collections.reverse(list);
                adapter.setData(list);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        auth = FirebaseAuth.getInstance();
        fab=findViewById(R.id.order_food);
        recyclerView = findViewById(R.id.main_notification_recyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new NotificationAdapter(list,getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<>();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);


        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
//                        Intent i=new Intent(MainActivity.this,MainActivity.class);
//                        startActivity(i);
//                        finish();
                        break;
                    case R.id.lost_found:
                        Intent i2=new Intent(MainActivity.this, LostAndFound.class);
                        startActivity(i2);
                        finish();
                        break;
                    case R.id.report:
                        Intent i3=new Intent(MainActivity.this, Cleanliness.class);
                        startActivity(i3);
                        finish();
                        break;
                    case R.id.profile:
                        Intent i4=new Intent(MainActivity.this, UserProfile.class);
                        startActivity(i4);
                        finish();
                        break;

                }
                return false;
            }
        });
    }
}