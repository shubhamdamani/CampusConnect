package com.hackweber.campusconnect.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.model.NotificationsItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminPanel extends AppCompatActivity {
    EditText desc,category;
    Button sendNotification;
    String body,categ;
    String itemId,itemTitle;
    private DatabaseReference databaseReference;

    private RequestQueue mRequestQue;
    private String URL = "https://fcm.googleapis.com/fcm/send";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        desc=findViewById(R.id.noBody);
        category=findViewById(R.id.noCategory);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        sendNotification=findViewById(R.id.sendNot);

        mRequestQue = Volley.newRequestQueue(this);


        sendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                body=desc.getText().toString();
                categ=category.getText().toString();
                itemId=System.currentTimeMillis()+"";
                itemTitle="New event in "+categ;
                sendNotificatio();




            }
        });

    }
    private void sendNotificatio() {

        JSONObject json = new JSONObject();
        try {
            json.put("to","/topics/"+"events");
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title",itemTitle);
            notificationObj.put("body","Click to see details");

            JSONObject extraData = new JSONObject();
            extraData.put("itemId",itemId);
            extraData.put("category",categ);






            json.put("notification",notificationObj);
            json.put("data",extraData);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            NotificationsItem obj = new NotificationsItem(itemId,"Click to see details",itemTitle,body);
                            databaseReference.child("Notifications").child(itemId).setValue(obj);
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