package com.hackweber.campusconnect.ui.UserProfilePackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hackweber.campusconnect.LoadingDialog;
import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.model.UserInfo;
import com.squareup.picasso.Picasso;

public class UserProfile extends AppCompatActivity {

    private ImageView editInfoIcon,userProfileImage;
    private EditText userName,userEmail,userPhone;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private StorageReference storageReference;
    private Button updateBtn;
    private LoadingDialog loadingDialog;

    private static final int PICK_IMAGE_REQUEST = 2;
    private String TAG="UserProfile_TAG";
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        init();

        editTextSetEnablity(false);
        loadingDialog.startLoadingDialog();
        fetchUserInfo();



        editInfoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextSetEnablity(true);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfo();
                updateBtn.setEnabled(false);
            }
        });

        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

    }

    private void updateInfo() {
        final String userName_text = userName.getText().toString();
        final String userEmail_text = userEmail.getText().toString();
        final String userPhone_text = userPhone.getText().toString();

        if(mImageUri!=null)
        {
            final StorageReference reference = storageReference.child("UserImage").child(user.getUid()+""+getFileExtension(mImageUri));
            reference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl()
                               .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                   @Override
                                   public void onSuccess(Uri uri) {
                                       UserInfo obj = new UserInfo(user.getUid(),userEmail_text,userName_text,userPhone_text,uri+"");
                                       databaseReference.child("UserInfo").child(user.getUid()).setValue(obj);
                                   }
                               });
                        }
                    });

        }else{
            UserInfo obj = new UserInfo(user.getUid(),userEmail_text,userName_text,userPhone_text);
            databaseReference.child("UserInfo").child(user.getUid()).setValue(obj);
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
            Picasso.get().load(mImageUri).into(userProfileImage);
        }
    }


    private void editTextSetEnablity(boolean b) {
        userProfileImage.setEnabled(b);
        userName.setEnabled(b);
        userEmail.setEnabled(b);
        userPhone.setEnabled(b);
    }


    private void fetchUserInfo() {
        databaseReference.child("UserInfo").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInfo obj = dataSnapshot.getValue(UserInfo.class);
                userName.setText(obj.getUserName());
                userPhone.setText(obj.getUserPhone());
                userEmail.setText(obj.getUserEmail());
                if(obj.getUserImage()!=null)
                {
                    Picasso.get().load(Uri.parse(obj.getUserImage())).placeholder(R.mipmap.ic_launcher).into(userProfileImage);
                }
                loadingDialog.DismissDialog();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void init() {
        editInfoIcon = findViewById(R.id.user_profile_edit);
        userProfileImage = findViewById(R.id.user_profile_image);
        userName = findViewById(R.id.user_profile_name);
        userEmail = findViewById(R.id.user_profile_email);
        userPhone = findViewById(R.id.user_profile_phone);
        updateBtn = findViewById(R.id.user_profile_update);
        loadingDialog = new LoadingDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference();
    }
}