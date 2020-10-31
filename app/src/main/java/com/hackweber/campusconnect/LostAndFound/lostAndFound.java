package com.hackweber.campusconnect.LostAndFound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hackweber.campusconnect.R;

public class lostAndFound extends AppCompatActivity {

    private TextView lostItems,foundItems;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FloatingActionButton addItems;
    private int item_type=0;
    //0 for lost 1 for found
    private String item_Category="LostItems";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found);

        init();

        lostItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_type=0;
                item_Category="LostItems";
            }
        });

        foundItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_type=1;
                item_Category="FoundItems";
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

    private void init() {
        layoutManager = new LinearLayoutManager(getApplicationContext());
        lostItems = findViewById(R.id.lost_items_list);
        foundItems = findViewById(R.id.found_items_list);
        recyclerView = findViewById(R.id.lost_and_found_recylerView);
        recyclerView.setHasFixedSize(true);
        addItems = findViewById(R.id.lost_and_found_add_items);
    }
}