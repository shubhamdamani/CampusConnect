package com.hackweber.campusconnect.LostAndFound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackweber.campusconnect.R;

import java.util.ArrayList;
import java.util.List;

public class lostAndFound extends AppCompatActivity {

    private TextView lostItems,foundItems;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<ItemInfo> itemList;
    private FloatingActionButton addItems;
    private int item_type=0;
    //0 for lost 1 for found
    private String item_Category="LostItems";
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found);

        init();

        fetchItems(item_Category);

        lostItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_type=0;
                item_Category="LostItems";
                fetchItems(item_Category);
            }
        });

        foundItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_type=1;
                item_Category="FoundItems";
                fetchItems(item_Category);
            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(lostAndFound.this,AddItem.class);
                intent.putExtra("type",item_type);
                startActivity(intent);
            }
        });
    }

    private void fetchItems(final String item_category) {
        itemList.clear();
        databaseReference.child("LostAndFoundItems").child(item_category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    ItemInfo item = ds.getValue(ItemInfo.class);
                    itemList.add(item);
                }
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void init() {
        itemList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        lostItems = findViewById(R.id.lost_items_list);
        foundItems = findViewById(R.id.found_items_list);
        recyclerView = findViewById(R.id.lost_and_found_recylerView);
        recyclerView.setHasFixedSize(true);
        adapter = new ItemAdapter(itemList,getApplicationContext());
        addItems = findViewById(R.id.lost_and_found_add_items);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}