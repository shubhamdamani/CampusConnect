package com.hackweber.campusconnect.LostAndFound;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackweber.campusconnect.R;

import java.util.ArrayList;
import java.util.List;

public class LostItemsTab extends Fragment {

    private RecyclerView recyclerViewLost;
    private ItemAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<ItemInfo> itemList;
    private FloatingActionButton addItems;
    private int item_type=0;
    //0 for lost 1 for found
    private final String item_Category="LostItems";
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lost,container,false);

        itemList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewLost = view.findViewById(R.id.lost_recylerView);
        recyclerViewLost.setHasFixedSize(true);
        adapter = new ItemAdapter(itemList,view.getContext());
//        addItems = getView().findViewById(R.id.lost_and_found_add_items);
//        recyclerViewLost.setAdapter(adapter);
        fetchItems(item_Category);

//        addItems.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LostItemsTab.this,AddItem.class);
//                intent.putExtra("type",item_type);
//                startActivity(intent);
//            }
//        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
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
                recyclerViewLost.setLayoutManager(layoutManager);
                recyclerViewLost.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

