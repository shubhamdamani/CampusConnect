package com.hackweber.campusconnect.ui.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.model.UserInfo;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email,userName,userPswd,userCnfPswd,userPhone;
    private Button signup_btn;
    private String TAG = "SignuP";
    String email_text,userName_text,pswd_text,cnfPswd_text,userPhone_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpFunction();
            }
        });

    }

    private void SignUpFunction() {

        email_text = email.getText().toString().trim();
        userName_text = userName.getText().toString().trim();
        pswd_text = userPswd.getText().toString().trim();
        cnfPswd_text = userCnfPswd.getText().toString().trim();
        userPhone_text = userPhone.getText().toString().trim();
        if(pswd_text.equals(cnfPswd_text) && !email_text.isEmpty() && !userName_text.isEmpty() && !userPhone_text.isEmpty() && !pswd_text.isEmpty())
        {
            mAuth.createUserWithEmailAndPassword(email_text, pswd_text)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("UserInfo");
                                UserInfo userObj = new UserInfo(user.getUid(),email_text,userName_text,userPhone_text);
                                myRef.child(user.getUid()).setValue(userObj);

                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent intent = new Intent(SignUp.this, VerificationPage.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUp.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(),"Please enter invalid details",Toast.LENGTH_SHORT).show();

        }
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.signup_email);
        userName = findViewById(R.id.signup_userName);
        userPhone = findViewById(R.id.signup_phone);
        userPswd = findViewById(R.id.signup_password);
        userCnfPswd = findViewById(R.id.signup_cnf_password);
        signup_btn = findViewById(R.id.signup_signup_btn);
    }
}