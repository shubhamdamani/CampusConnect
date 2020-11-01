package com.hackweber.campusconnect.ui.CleanlinessPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hackweber.campusconnect.LoadingDialog;
import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.ui.CanteenActivity;
import com.hackweber.campusconnect.ui.FoodOrderActivity;
import com.hackweber.campusconnect.ui.LostAndFound.LostAndFound;
import com.hackweber.campusconnect.ui.MainActivity;
import com.hackweber.campusconnect.ui.UserProfilePackage.UserProfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cleanliness extends AppCompatActivity {

    private RecyclerView recyclerView,searchRecyclerView;
    private LinearLayoutManager layoutManager,searchLayoutManager;
    private FloatingActionButton addPlacebtn,fab;
    private placeAdapter adapter;
    private SearchAdapter searchAdapter;
    private List<PlaceInfo> placeList;
    private List<SearchModel> searchList;
    private DatabaseReference databaseReference;
    private LoadingDialog loadingDialog;
    private MenuItem search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleanliness);

        init();
        fab=findViewById(R.id.order_food);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Cleanliness.this, CanteenActivity.class);
                startActivity(i);
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i5=new Intent(UserProfile.this, FoodOrderActivity.class);
//                startActivity(i5);
//                finish();
//            }
//        });

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        Intent i=new Intent(Cleanliness.this, MainActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.lost_found:
                        Intent i2=new Intent(Cleanliness.this, LostAndFound.class);
                        startActivity(i2);
                        finish();
                        break;
                    case R.id.report:
//                        Intent i3=new Intent(C.this, Cleanliness.class);
//                        startActivity(i3);
//                        finish();
                        break;
                    case R.id.profile:
                        Intent i4=new Intent(Cleanliness.this, UserProfile.class);
                        startActivity(i4);
                        finish();
                        break;

                }
                return false;
            }
        });
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
                placeList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    PlaceInfo obj = ds.getValue(PlaceInfo.class);
                    placeList.add(obj);
                }
                Collections.reverse(placeList);
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
        searchRecyclerView = findViewById(R.id.cleanliness_search_recyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        searchLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        searchRecyclerView.setHasFixedSize(true);
        adapter = new placeAdapter(placeList,getApplicationContext());
        searchAdapter = new SearchAdapter(searchList,getApplicationContext());
        addPlacebtn = findViewById(R.id.cleanliness_add_places);
        placeList = new ArrayList<>();
        searchList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        search = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) search.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchList.clear();
                searchRecyclerView.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchRecyclerView.setVisibility(View.VISIBLE);
                searchList.clear();

                Query query = databaseReference.child("Cleanliness Places").orderByChild("placeLocation").
                        startAt(newText.trim()).endAt(newText+"\uf8ff").limitToFirst(20);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            PlaceInfo place = ds.getValue(PlaceInfo.class);
                            SearchModel obj = new SearchModel(place.getPlaceLocation(),place.getPlaceId());
                            searchList.add(obj);
                        }
                        searchAdapter.setData(searchList);
                        searchRecyclerView.setLayoutManager(searchLayoutManager);
                        searchRecyclerView.setAdapter(searchAdapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                search.collapseActionView();
                searchView.setQuery("",false);
                searchView.onActionViewCollapsed();
                searchList.clear();
                return false;
            }
        });

        return true;
    }
}