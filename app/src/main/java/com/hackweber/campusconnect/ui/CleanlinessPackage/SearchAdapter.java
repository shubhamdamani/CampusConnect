package com.hackweber.campusconnect.ui.CleanlinessPackage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackweber.campusconnect.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<SearchModel> list;
    private Context context;

    public SearchAdapter(List<SearchModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_search_item,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.search_text.setText(list.get(position).getSearchName());
        holder.placeId = list.get(position).getPlaceId();
    }
    public void setData(List<SearchModel> list)
    {
        this.list =list;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{

        public TextView search_text;
        public DatabaseReference databaseReference;
        public String placeId;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            search_text = itemView.findViewById(R.id.search_text);
            databaseReference = FirebaseDatabase.getInstance().getReference();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context,placeDetail.class);
                    intent.putExtra("placeId",placeId);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    list.clear();
                    notifyDataSetChanged();
                    context.startActivity(intent);
                }
            });




        }

    }


}
