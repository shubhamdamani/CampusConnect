package com.hackweber.campusconnect.ui.CleanlinessPackage;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.ui.LostAndFound.ItemInfo;
import com.squareup.picasso.Picasso;

public class AddPlaces extends AppCompatActivity {

    private ImageView localityImage;
    private Button chooseImage,addPlace;
    private EditText location,date,description;
    private static final int PICK_IMAGE_REQUEST = 2;
    private String TAG="AddPlace_TAG";
    private Uri mImageUri;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);

        init();

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPlaceToList();
            }
        });

    }

    private void addPlaceToList() {
        Log.d(TAG,"uploading2");
        if(mImageUri!=null)
        {
            final String placeId = System.currentTimeMillis()+"";
            final String placeLocation = location.getText().toString().trim();
            final String placeDescription = description.getText().toString().trim();
            final String placeDate = date.getText().toString().trim();
            if(placeLocation.equals("") || placeDescription.equals("") || placeDate.equals(""))
            {
                Toast.makeText(getApplicationContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                return;
            }

            final StorageReference reference = storageReference.child("Cleanliness Places").child(placeId+""+getFileExtension(mImageUri));
            reference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            PlaceInfo obj = new PlaceInfo(placeId,placeLocation,placeDescription,placeDate,uri+"");
                                            databaseReference.child("Cleanliness Places").child(placeId).setValue(obj);
                                            Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                }
            });
        }else
        {
            Toast.makeText(getApplicationContext(),"Please add image",Toast.LENGTH_SHORT).show();
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
            Picasso.get().load(mImageUri).into(localityImage);
        }
    }

    private void init() {
        localityImage = findViewById(R.id.addPlace_image);
        chooseImage = findViewById(R.id.addPlace_chooseImage);
        addPlace = findViewById(R.id.addPlace_AddPlace);
        location = findViewById(R.id.addPlace_place);
        date = findViewById(R.id.addPlace_date);
        description = findViewById(R.id.addPlace_addDescription);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

}