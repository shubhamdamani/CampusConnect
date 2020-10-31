package com.hackweber.campusconnect.ui.LostAndFound;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.ui.LostAndFound.ItemDetailActivity;
import com.hackweber.campusconnect.ui.LostAndFound.ItemInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private List<ItemInfo> itemList;
    private Context context;
    private FirebaseUser user;

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
        holder.itemId = item.getItemId();
        holder.itemCategory = item.getItemCategory();
        holder.clientID = item.getUserId();
    }

    public void setData(List<ItemInfo> itemList){
        this.itemList = itemList;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        public ImageView itemImage,menu;
        public TextView itemName,itemPlace,itemDate;
        public String itemId,itemCategory,clientID;
        public DatabaseReference databaseReference;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            menu = itemView.findViewById(R.id.three_dot_menu);
            itemImage = itemView.findViewById(R.id.raw_lost_and_found_item_image);
            itemName = itemView.findViewById(R.id.raw_lost_and_found_item_itemName);
            itemPlace = itemView.findViewById(R.id.raw_lost_and_found_item_itemPlace);
            itemDate = itemView.findViewById(R.id.raw_lost_and_found_item_itemDate);
            user = FirebaseAuth.getInstance().getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference();

            if(!user.getUid().equals(clientID))
            {
                menu.setVisibility(View.GONE);
            }

            menu.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View view) {
                    showPopupMenu(menu);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra("category",itemCategory);
                    intent.putExtra("itemId",itemId);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private void showPopupMenu(View view)
        {
            PopupMenu popup = new PopupMenu(context, view, Gravity.END);
            MenuInflater inflater = popup.getMenuInflater();

            inflater.inflate(R.menu.delete_menu, popup.getMenu());

            MenuItem item = popup.getMenu().findItem(R.id.delete);

            popup.setOnMenuItemClickListener(new MyMenuItemClickListener(getAdapterPosition()));

            popup.show();
        }

        class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
            int position;

            /**
             * @param position
             */
            MyMenuItemClickListener(int position) {

                this.position = position;
            }


            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.delete:
                        databaseReference.child("LostAndFoundItems").child(itemCategory).child(itemId).removeValue();
                        return true;
                }
                return false;
            }

        }



    }

}
