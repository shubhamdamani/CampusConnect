package com.hackweber.campusconnect.dao;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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

    }

    public void fetchCanteensData(){

    }

    public ArrayList<Canteen> getCanteensData(){
        return canteens;

    }


}
