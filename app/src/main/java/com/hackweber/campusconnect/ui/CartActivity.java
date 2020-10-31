package com.hackweber.campusconnect.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.adapter.CartAdapter;
import com.hackweber.campusconnect.dao.StorageClass;
import com.hackweber.campusconnect.model.FoodDetails;
import com.hackweber.campusconnect.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CartActivity extends AppCompatActivity {
    private ListView listview;
    private Button placeOrder;
    private String formattedDate;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private DatabaseReference dbRef;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if(user!=null){
            String userEmail = user.getEmail();
            username   = userEmail.substring(0, userEmail.lastIndexOf("@"));
        }
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
        //Toast.makeText(getContext(), formattedDate, Toast.LENGTH_SHORT).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("orders/" + username );
        placeOrder = findViewById(R.id.placeorder);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<FoodDetails> storage = new StorageClass().getFoodCart();
                String orderItems = "";
                int amount=0;
                for(int i=0;i<storage.size();i++){
                    FoodDetails foodItem = storage.get(i);
                    orderItems = orderItems+foodItem.getFoodQuantity()+" X "+foodItem.getFoodName()+" ,";
                    amount = amount + foodItem.getFoodQuantity()*foodItem.getPrice();

                }
                String key = dbRef.push().getKey();
                dbRef.child(key).setValue(new Order(orderItems,String.valueOf(amount),formattedDate)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        storage.clear();
                        startActivity(new Intent(CartActivity.this,FoodOrderActivity.class));
                        finish();
                    }
                });

            }
        });
        listview = (ListView) findViewById(R.id.custom_ListView);
        listview.setAdapter(new CartAdapter(this,new StorageClass().getFoodCart()));

        FloatingActionButton addItems = findViewById(R.id.addItems);
        listview.setEmptyView(findViewById(R.id.empty_view));
        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CartActivity.this,"Add Some Items", Toast.LENGTH_SHORT).show();
            }
        });
    }

}