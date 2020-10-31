package com.hackweber.campusconnect.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.adapter.FoodAdapter;
import com.hackweber.campusconnect.dao.StorageClass;
import com.hackweber.campusconnect.model.FoodDetails;

public class FoodOrderActivity extends AppCompatActivity {
    private boolean backPressed ;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order);
        auth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //get current user
//        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

//        authListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//
//                if (user == null) {
//                    // user auth state is changed - user is null
//                    // launch login activity
//                    startActivity(new Intent(FoodOrderActivity.this, Login.class));
//                    finish();
//                }
//                else{
//                    String name = user.getEmail();
//                    //Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
//                }
//            }
//        };
        final GridView gridview = findViewById(R.id.gridview);
        StorageClass foodStorage = new StorageClass();
        foodStorage.setCatalogData();
        gridview.setAdapter(new FoodAdapter(this,foodStorage.getCatalogData()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                FoodDetails food = (FoodDetails) gridview.getItemAtPosition(position);
                Intent activity = new Intent(FoodOrderActivity.this,SelectedFood.class);
                // Bundle bundle = new Bundle();
                activity.putExtra("myObject", food);
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
                Intent intent = new Intent(FoodOrderActivity.this,MyOrders.class);
                startActivity(intent);
                //Toast.makeText(this,"Signout",Toast.LENGTH_SHORT).show();
                break;


            case R.id.logout:
                signOut();
                Toast.makeText(this,"Signout",Toast.LENGTH_SHORT).show();
                break;
            case R.id.cart:
                startActivity(new Intent(FoodOrderActivity.this,CartActivity.class));
                default:
                    break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void signOut() {
        auth.signOut();
    }
}