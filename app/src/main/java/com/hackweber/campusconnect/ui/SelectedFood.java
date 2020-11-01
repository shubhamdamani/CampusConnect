package com.hackweber.campusconnect.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.dao.StorageClass;
import com.hackweber.campusconnect.model.FoodDetails;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;


public class SelectedFood extends AppCompatActivity {

    private StorageClass foodData;
    private int index;
    private FoodDetails selectedfood;
    private TextView foodQuantity;
    private int dummyQuantity;
    private DatabaseReference dbRef;
    private String[] dataRetrieve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_food);

        dataRetrieve = getIntent().getStringExtra("itemId").split("#");
        dummyQuantity = 1;  // varaible to store the Quantity Displayed ...
        dbRef = FirebaseDatabase.getInstance().getReference().child("canteens").child(dataRetrieve[0]).child("menu").child(dataRetrieve[1]);
        foodData = new StorageClass();  // giving memory to storageClass object ;
        //Log.d("foodData",foodData.getCatalogData().toString());
        for(FoodDetails fd: foodData.getCatalogData()) {
            //Log.d("IDs",fd.getId());
            if (fd.getId().equals(dataRetrieve[1])) {
                selectedfood = fd;
            }
        }
        //Log.d("dataRe", dataRetrieve[1]);
        final ImageView foodImage = (ImageView) findViewById(R.id.selectedimage);
        final TextView foodName = (TextView) findViewById(R.id.selectedfood);
        final TextView foodPrice = (TextView) findViewById(R.id.selectedprice);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(Uri.parse(dataSnapshot.child("url").getValue().toString())).placeholder(R.drawable.ic_launcher_background).into(foodImage);
                foodName.setText(dataSnapshot.child("name").getValue().toString());
                foodPrice.setText(dataSnapshot.child("price").getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

                /*
                Food Related Details Above ...
                 */

                /*
                 Quantity Related Buttons below ...
                 */
                foodQuantity = (TextView) findViewById(R.id.selectednoofitems);
                foodQuantity.setText(Integer.toString(dummyQuantity));

                /*
                Quantity Related Buttons Above ...
                 */
            }



    public void increaseQuantity(View view){
        dummyQuantity++;
        foodQuantity.setText(Integer.toString(dummyQuantity));
    }
    public void decreaseQuantity(View view){
        if (dummyQuantity>1){
            dummyQuantity--;
            foodQuantity.setText(Integer.toString(dummyQuantity));
        }
        else {
            Toast.makeText(this,"Quantity should atleast be one", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    Invoked Add to Cart button is Pressed and Then It Returns to Main Display and
    Showing The Items Added In Cart ...
     */
    public void addToCart(View view){
        Intent intent = new Intent(this,FoodOrderActivity.class);
        if (isContain(foodData.getFoodCart())){
            Log.i("ERROR","CONATINS ");

                if (foodQuantity.getText().toString().equals(Integer.toString(selectedfood.getFoodQuantity()))) {
                    Toast.makeText(this, "Food is Already In cart", Toast.LENGTH_SHORT).show();
                } else {
                    selectedfood.setFoodQuantity(dummyQuantity);
                    intent.putExtra("From", dataRetrieve[0]);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        else {
            selectedfood.setFoodQuantity(dummyQuantity);
            foodData.getFoodCart().add(selectedfood);
            intent.putExtra("uid",dataRetrieve[0]);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
    /*
    Method to check Whether Food is Already In Food Cart Or Not ...
     */
    private boolean isContain(ArrayList<FoodDetails> checker){
        Log.i("SIZE", Integer.toString(checker.size()));
        for (int i=0;i<checker.size();i++){
            if (selectedfood.getId().equals(checker.get(i).getId())){
                return true;
            }
        }
        return false;
    }

}
