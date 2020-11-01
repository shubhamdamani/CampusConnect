package com.hackweber.campusconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.model.NotificationsItem;
import com.hackweber.campusconnect.ui.LostAndFound.ItemDetailActivity;
import com.hackweber.campusconnect.ui.LostAndFound.ItemInfo;
import com.hackweber.campusconnect.ui.eventDetail;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>{

    private List<NotificationsItem> notificationList;
    private Context context;

    public NotificationAdapter(List<NotificationsItem> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_notification_item,parent,false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationsItem obj = notificationList.get(position);
        holder.categoryId = obj.getCategory();
        holder.itemId = obj.getItemId();
        holder.title.setText(obj.getTitle());
        holder.body.setText(obj.getBody());
    }


    public void setData(List<NotificationsItem> list)
    {
        this.notificationList = list;
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder{

        public TextView title,body;
        public String categoryId="",itemId="";

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.raw_notification_title);
            body = itemView.findViewById(R.id.raw_notification_body);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(categoryId.equals("LostItems") ||(categoryId.equals("FoundItems")))
                    {
                        Intent intent = new Intent(context, ItemDetailActivity.class);
                        intent.putExtra("category",categoryId);
                        intent.putExtra("itemId",itemId);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }else{
                        Intent intent = new Intent(context, eventDetail.class);
                        intent.putExtra("body",categoryId);
                        intent.putExtra("title",title.getText().toString());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });


        }
    }


}
