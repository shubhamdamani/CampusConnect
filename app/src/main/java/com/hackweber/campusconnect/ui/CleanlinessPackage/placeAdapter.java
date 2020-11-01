package com.hackweber.campusconnect.ui.CleanlinessPackage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackweber.campusconnect.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class placeAdapter extends RecyclerView.Adapter<placeAdapter.PlaceViewHolder> {

    private List<PlaceInfo> placesList;
    private Context context;

    public placeAdapter(List<PlaceInfo> placesList, Context context) {
        this.placesList = placesList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_place_item,parent,false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        PlaceInfo obj = placesList.get(position);
        Picasso.get().load(Uri.parse(obj.getPlaceImg())).placeholder(R.mipmap.ic_launcher).into(holder.placeImage);
        holder.locality.setText(obj.getPlaceLocation());
        holder.placeId = obj.getPlaceId();
    }

    public void setData(List<PlaceInfo> placesList){
        this.placesList = placesList;
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder{

        public ImageView placeImage;
        public TextView locality;
        public String placeId;
        public DatabaseReference databaseReference;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage = itemView.findViewById(R.id.raw_place_item_image);
            locality = itemView.findViewById(R.id.raw_place_item_locality);
            databaseReference = FirebaseDatabase.getInstance().getReference();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,placeDetail.class);
                    intent.putExtra("placeId",placeId);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }
    }

}
