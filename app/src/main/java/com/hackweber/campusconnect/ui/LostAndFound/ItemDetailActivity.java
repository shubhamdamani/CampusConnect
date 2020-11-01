package com.hackweber.campusconnect.ui.LostAndFound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackweber.campusconnect.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ItemDetailActivity extends AppCompatActivity {

    private static final String TAG = "ItemDetailActivityyy";
    private ImageView itemImage;
    private TextView itemCategory,itemAddress,itemPostedBy,itemContactMe,itemDescription;
    private String category="";
    private String itemId="";
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        init();
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        itemId = intent.getStringExtra("itemId");
        itemCategory.setText(category);
        fetchItem();

    }

    private void fetchUser(String userId){
        databaseReference.child("UserInfo").child(userId).child("userName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                itemPostedBy.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void fetchItem() {

        databaseReference.child("LostAndFoundItems").child(category).child(itemId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ItemInfo obj = dataSnapshot.getValue(ItemInfo.class);
                Log.d("ItemDetailActivity",dataSnapshot.getValue()+"");
                Picasso.get().load(Uri.parse(obj.getItemUri())).placeholder(R.mipmap.ic_launcher).into(itemImage);
                itemAddress.setText(obj.getItemPlace());
                itemContactMe.setText(obj.getItemContact());
                itemDescription.setText(obj.getItemDescription());
                fetchUser(obj.getUserId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        itemImage=findViewById(R.id.item_detail_image);
        itemCategory=findViewById(R.id.item_detail_item_category);
        itemAddress=findViewById(R.id.item_detail_item_address);
        itemPostedBy=findViewById(R.id.item_detail_item_postedBy);
        itemContactMe=findViewById(R.id.item_detail_item_contactMe);
        itemDescription=findViewById(R.id.item_detail_item_description);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}