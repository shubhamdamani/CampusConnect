package com.hackweber.campusconnect.LostAndFound;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackweber.campusconnect.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private List<ItemInfo> itemList;
    private Context context;

    public ItemAdapter(List<ItemInfo> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_lost_and_found_item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemInfo item = itemList.get(position);
        Picasso.get().load(Uri.parse(item.getItemUri())).placeholder(R.mipmap.ic_launcher).into(holder.itemImage);
        holder.itemName.setText(item.getItemName());
        holder.itemPlace.setText(item.getItemPlace());
        holder.itemDate.setText(item.getItemDate());
    }

    public void setData(List<ItemInfo> itemList){
        this.itemList = itemList;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        public ImageView itemImage;
        public TextView itemName,itemPlace,itemDate;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.raw_lost_and_found_item_image);
            itemName = itemView.findViewById(R.id.raw_lost_and_found_item_itemName);
            itemPlace = itemView.findViewById(R.id.raw_lost_and_found_item_itemPlace);
            itemDate = itemView.findViewById(R.id.raw_lost_and_found_item_itemDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                }
            });

        }
    }

}
