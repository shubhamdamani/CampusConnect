package com.hackweber.campusconnect.dao;

import android.content.Context;
import android.util.Log;
import android.widget.GridView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hackweber.campusconnect.adapter.CanteenAdapter;
import com.hackweber.campusconnect.adapter.FoodAdapter;
import com.hackweber.campusconnect.model.Canteen;
import com.hackweber.campusconnect.model.FoodDetails;
import com.hackweber.campusconnect.R;

import java.util.ArrayList;

/**
 * Created by kunal on 31-10-2020.
 */

public class StorageClass {

    private static ArrayList<FoodDetails> foodItems ;
    private static ArrayList<FoodDetails> foodCart ;
    private static DatabaseReference databaseReference;
    private static FirebaseUser user;
    private static StorageReference storageReference;
    public StorageClass(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference();
        if(foodItems==null)
            foodItems = new ArrayList<>();

    }

    public void setMenuAdapter(String uid, final GridView gridView, final Context context){
        databaseReference.child("canteens").child(uid).child("menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodItems.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    //Log.i("snapshot",ds.toString());
                    FoodDetails item = ds.getValue(FoodDetails.class);
                    foodItems.add(item);
                }
                gridView.setAdapter(new FoodAdapter(context,getCatalogData()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public ArrayList<FoodDetails> getCatalogData(){
        return foodItems;
    }

    public ArrayList<FoodDetails> getFoodCart(){
        if (foodCart ==null){
            foodCart = new ArrayList<>();
        }
        return foodCart;
    }

}
