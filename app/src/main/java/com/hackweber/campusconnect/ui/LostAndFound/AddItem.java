package com.hackweber.campusconnect.ui.LostAndFound;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.model.NotificationsItem;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddItem extends AppCompatActivity {

    private ImageView itemImage;
    private Button chooseImage,addItem;
    private EditText itemName,itemPlace,itemDate,itemDescription,itemContactMe;
    private String itemName_text,itemPlace_text,itemDate_text,itemDescription_text,itemContactMe_text,itemCategory;
    private static final int PICK_IMAGE_REQUEST = 2;
    private String TAG="AddItem_TAG";
    private Uri mImageUri;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private int item_type;
    private RequestQueue mRequestQue;
    private String currentTopic;
    private String successId;
    private String URL = "https://fcm.googleapis.com/fcm/send";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        item_type = getIntent().getIntExtra("type",0);

        mRequestQue = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("lost");
        FirebaseMessaging.getInstance().subscribeToTopic("found");
        if(item_type==0)
        {
            currentTopic="lost";

        }else
        {

            currentTopic="found";
        }
        init();

        item_type = getIntent().getIntExtra("type",0);
        if(item_type==0)
        {
            itemCategory = "LostItems";
            addItem.setText("Add lost item");
        }else{
            itemCategory="FoundItems";
            addItem.setText("Add found item");
        }



        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadItem();
            }
        });

    }

    private void uploadItem() {
        Log.d(TAG,"uploading2");
        convertTextToString();
        if(itemName_text!=null && itemDescription_text!=null && itemPlace_text!=null && itemDate_text!=null)
        {
            final String itemId = System.currentTimeMillis()+"";
            successId=itemId;
            if(mImageUri==null)
            {
               // ItemInfo obj = new ItemInfo(itemId,user.getUid(),itemName_text,itemPlace_text,itemDate_text,itemDescription_text,itemContactMe_text,itemCategory);
                //databaseReference.child("LostAndFoundItems").child(itemCategory).child(itemId).setValue(obj);



                Toast.makeText(getApplicationContext(),"Please add image",Toast.LENGTH_SHORT).show();
                //sendNotification();
            }else{
                Log.d(TAG,"uploading");
                final StorageReference reference = storageReference.child("LostAndFoundItems").child("LostItems").child(itemId+""+getFileExtension(mImageUri));
                reference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                ItemInfo obj = new ItemInfo(itemId,user.getUid(),itemName_text,itemPlace_text,itemDate_text,itemDescription_text,itemContactMe_text,uri+"",itemCategory);
                                                databaseReference.child("LostAndFoundItems").child(itemCategory).child(itemId).setValue(obj);
                                                Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_SHORT).show();
                                                sendNotification();
                                            }
                                        });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }else
        {
            Toast.makeText(getApplicationContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show();

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("2323232","111111");

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(itemImage);
        }
    }

    private void convertTextToString()
    {
        itemName_text = itemName.getText().toString();
        itemPlace_text = itemPlace.getText().toString();
        itemDate_text = itemDate.getText().toString();
        itemDescription_text = itemDescription.getText().toString();
        itemContactMe_text = itemContactMe.getText().toString();
    }

    private void init() {
        itemImage = findViewById(R.id.addItem_image);
        chooseImage = findViewById(R.id.addItem_chooseImage);
        addItem= findViewById(R.id.addItem_AddItem);
        itemName = findViewById(R.id.addItem_itemName);
        itemPlace = findViewById(R.id.addItem_addPlace);
        itemDate = findViewById(R.id.addItem_date);
        itemDescription = findViewById(R.id.addItem_addDescription);
        itemContactMe = findViewById(R.id.addItem_contactMe);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

    }
    private void sendNotification() {

        JSONObject json = new JSONObject();
        try {
            json.put("to","/topics/"+currentTopic);
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title","One item " +currentTopic);
            notificationObj.put("body","Click to see the item");

            JSONObject extraData = new JSONObject();
            extraData.put("itemId",successId);
            final String categor;
            if(currentTopic.equals("lost"))
            {
                categor="LostItems";
                extraData.put("category","LostItems");
            }
            else
            {
                categor="FoundItems";
                extraData.put("category","FoundItems");
            }




            json.put("notification",notificationObj);
            json.put("data",extraData);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            NotificationsItem obj = new NotificationsItem(successId,"Click to see the item",itemName_text +" is "+currentTopic,categor);
                            databaseReference.child("Notifications").child(successId).setValue(obj);
                            Log.d("MUR", "onResponse: ");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("MUR", "onError: "+error.networkResponse);
                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAAJnUq71Q:APA91bFPX6h1jweB072PbEikMvTG300HVuvun0ATUUMMYe6J-RXGp-6Sun0bcTe5jef_Ig9XnFufKFHuWgJjujnkhl25Da9Wf82GQ9JIL39QTf23r15M17PpEPZNsV9-b-ELV9OeoTgE");
                    return header;
                }
            };
            mRequestQue.add(request);
        }
        catch (JSONException e)

        {
            Log.d("notifE",e.toString());
            e.printStackTrace();
        }
    }
}