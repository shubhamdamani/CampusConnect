package com.hackweber.campusconnect.ui.FoodOrder;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.dao.CanteenStorage;
import com.hackweber.campusconnect.model.Canteen;
import com.hackweber.campusconnect.ui.CleanlinessPackage.Cleanliness;
import com.hackweber.campusconnect.ui.LostAndFound.LostAndFound;
import com.hackweber.campusconnect.ui.MainActivity;
import com.hackweber.campusconnect.ui.UserProfilePackage.UserProfile;

public class CanteenActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen);
        auth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Choose canteen");
        setSupportActionBar(toolbar);
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
                        Intent i=new Intent(CanteenActivity.this, MainActivity.class);
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
                Intent activity = new Intent(CanteenActivity.this, FoodOrderActivity.class);
                activity.putExtra("uid",canteen.getUniqueID());
                startActivity(activity);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.my_orders:
                Intent intent = new Intent(CanteenActivity.this, MyOrders.class);
                startActivity(intent);
                //Toast.makeText(this,"Signout",Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                signOut();
                Toast.makeText(this,"Signed out",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void signOut() {
        auth.signOut();
    }


}