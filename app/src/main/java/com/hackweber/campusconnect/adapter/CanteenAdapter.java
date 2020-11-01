package com.hackweber.campusconnect.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.model.Canteen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CanteenAdapter extends ArrayAdapter<Canteen> {
    public CanteenAdapter(Context context, ArrayList<Canteen> canteenStorage) {
        super(context, 0, canteenStorage);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            LayoutInflater customInflator = LayoutInflater.from(getContext());
            convertView = customInflator.inflate(R.layout.canteen,parent,false);
        }
        Canteen canteen = getItem(position);

        ImageView iCanteenImage = convertView.findViewById(R.id.canteen_image);
        TextView iCanteenName = convertView.findViewById(R.id.canteen_name);
        TextView iCanteenAddress = convertView.findViewById(R.id.canteen_address);

        iCanteenName.setText(canteen.getName());
        iCanteenAddress.setText(canteen.getAddress());
        Picasso.get().load(Uri.parse(canteen.getUrl())).placeholder(R.mipmap.ic_launcher).into(iCanteenImage);


        return convertView;
    }
}
