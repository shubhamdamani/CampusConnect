package com.hackweber.campusconnect.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.hackweber.campusconnect.R;
import com.hackweber.campusconnect.model.FoodDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by kunal on 31-10-2020.
 */

public class FoodAdapter extends ArrayAdapter<FoodDetails> {

    public FoodAdapter(Context context, ArrayList<FoodDetails> storage){
        super(context,0,storage);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater customInflater = LayoutInflater.from(getContext());
            convertView = customInflater.inflate(R.layout.menu_food_item,parent,false);
        }
        FoodDetails foodItem = getItem(position);
        ImageView ifoodimage = (ImageView) convertView.findViewById(R.id.foodimage);
        TextView ifoodName = (TextView) convertView.findViewById(R.id.foodname);
        TextView ifoodPrice = (TextView) convertView.findViewById(R.id.foodprice);
        Picasso.get().load(Uri.parse(foodItem.getUrl())).placeholder(R.drawable.ic_launcher_background).into(ifoodimage);
        ifoodName.setText(foodItem.getName());
        ifoodPrice.setText(foodItem.getPrice());
        return convertView;
    }
}
