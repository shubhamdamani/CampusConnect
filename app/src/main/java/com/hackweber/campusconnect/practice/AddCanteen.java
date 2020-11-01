package com.hackweber.campusconnect.practice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.model.Canteen;
import com.hackweber.campusconnect.model.MenuItem;

import java.io.IOException;
import java.util.Objects;

public class AddCanteen extends AppCompatActivity implements View.OnClickListener {
    private Button buttonUpload;
    private EditText et_title,et_address;
    public static final int REQUEST_CODE = 1234;
    private StorageReference mStorageRef;
    private DatabaseReference mDataBaseRefadmin;
    private Uri imgUri;
    public static final String FB_STORAGE_PATH = "canteens/";
    private ImageView imageView;
    String s1,username ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_canteen);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDataBaseRefadmin = FirebaseDatabase.getInstance().getReference().child("canteens");
        et_title=findViewById(R.id.title12);
        et_address = findViewById(R.id.address);
        buttonUpload = findViewById(R.id.buttonUplaod);
        imageView = findViewById(R.id.imageView2);
        imageView.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == imageView) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "select image"), REQUEST_CODE);
        }


        if(v == buttonUpload){
            if(et_title.getText().toString().equals(""))
                Toast.makeText(this, "Write the Name of Canteen", Toast.LENGTH_SHORT).show();
            else if(et_address.getText().toString().equals(""))
                Toast.makeText(this, "Write the Address of the Canteen", Toast.LENGTH_SHORT).show();
            else {
                if (imgUri!=null)
                {

                    final String Name = et_title.getText().toString().trim();
                    final String address = et_address.getText().toString().trim();
                    final ProgressDialog dialog = new ProgressDialog(this);
                    dialog.setTitle("uploading image");
                    dialog.show();
                    final String uploadId = mDataBaseRefadmin.push().getKey();
                    final StorageReference ref = mStorageRef.child(FB_STORAGE_PATH +"/"+uploadId+"/" + System.currentTimeMillis() + "." + getImageExt(imgUri));
                    ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            dialog.dismiss();

                            Toast.makeText(getApplicationContext(), "image uploaded", Toast.LENGTH_LONG).show();
                            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onComplete(@NotNull Task<Uri> task) {
                                    String Url = Objects.requireNonNull(task.getResult()).toString();
                                    Canteen item = new Canteen(uploadId,Name,address,Url);
                                    assert uploadId != null;
                                    mDataBaseRefadmin.child(uploadId).setValue(item);
                                }
                            });
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                    dialog.setMessage("uploaded " + (int) progress + "%");
                                }
                            });
                }
                else
                    Toast.makeText(this, "Select Image to upload", Toast.LENGTH_SHORT).show();
            }
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getData() != null && requestCode == REQUEST_CODE && resultCode == RESULT_OK)
            imgUri = data.getData();

        try {
            if(imgUri!=null) {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                imageView.setImageBitmap(bm);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}