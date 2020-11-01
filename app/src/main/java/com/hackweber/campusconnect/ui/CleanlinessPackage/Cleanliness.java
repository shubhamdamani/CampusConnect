package com.hackweber.campusconnect.ui.CleanlinessPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackweber.campusconnect.LoadingDialog;
import com.hackweber.campusconnect.R;

import java.util.ArrayList;
import java.util.List;

public class Cleanliness extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FloatingActionButton addPlacebtn;
    private placeAdapter adapter;
    private List<PlaceInfo> placeList;
    private DatabaseReference databaseReference;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleanliness);

        init();

        loadingDialog.startLoadingDialog();
        addPlacebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cleanliness.this, AddPlaces.class);
                startActivity(intent);
            }
        });

        fetchList();

    }

    private void fetchList() {
        databaseReference.child("Cleanliness Places").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    PlaceInfo obj = ds.getValue(PlaceInfo.class);
                    placeList.add(obj);
                }
                adapter.setData(placeList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                loadingDialog.DismissDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        loadingDialog = new LoadingDialog(this);
        recyclerView = findViewById(R.id.cleanliness_recyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        adapter = new placeAdapter(placeList,getApplicationContext());
        addPlacebtn = findViewById(R.id.cleanliness_add_places);
        placeList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}