package com.hackweber.campusconnect.ui.Cleanliness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackweber.campusconnect.LoadingDialog;
import com.hackweber.campusconnect.R;
import com.squareup.picasso.Picasso;

public class placeDetail extends AppCompatActivity {

    private ImageView placeImg;
    private TextView locality,description;
    private DatabaseReference databaseReference;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        init();

        String placeId = getIntent().getStringExtra("placeId");
        loadingDialog.startLoadingDialog();
        fetchPlaceInfo(placeId);

    }

    private void fetchPlaceInfo(String placeId) {
        databaseReference.child("Cleanliness Places").child(placeId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PlaceInfo obj = dataSnapshot.getValue(PlaceInfo.class);
                Picasso.get().load(Uri.parse(obj.getPlaceImg())).placeholder(R.mipmap.ic_launcher).into(placeImg);
                locality.setText(obj.getPlaceLocation());
                description.setText(obj.getPlaceDescription());
                loadingDialog.DismissDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        loadingDialog = new LoadingDialog(placeDetail.this);
        placeImg = findViewById(R.id.place_detail_image);
        locality = findViewById(R.id.place_detail_locality);
        description = findViewById(R.id.place_detail_description);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}