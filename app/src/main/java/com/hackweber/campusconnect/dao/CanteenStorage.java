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
import com.hackweber.campusconnect.model.Canteen;

import java.util.ArrayList;

public class CanteenStorage {

    private static ArrayList<Canteen> canteens ;
    private static DatabaseReference databaseReference;
    private static FirebaseUser user;
    private static StorageReference storageReference;
    public CanteenStorage(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference();
        canteens = new ArrayList<>();

    }

    public void setCanteensAdapter(final GridView mContentGridView, final Context mThis){
        databaseReference.child("canteens").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                canteens.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Log.i("snapshot",ds.toString());
                Canteen canteen = ds.getValue(Canteen.class);
                canteen.setUniqueID(ds.getKey());
                canteens.add(canteen);
                }
                mContentGridView.setAdapter(new CanteenAdapter(mThis,getCanteensData()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public ArrayList<Canteen> getCanteensData(){
        Log.i("count",String.valueOf(canteens.size()));
        return canteens;

    }


}
