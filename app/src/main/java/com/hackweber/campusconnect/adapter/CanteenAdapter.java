package com.hackweber.campusconnect.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.hackweber.campusconnect.model.Canteen;

import java.util.ArrayList;

public class CanteenAdapter extends ArrayAdapter<Canteen> {
    public CanteenAdapter(Context context, ArrayList<Canteen> canteenStorage) {
        super(context, 0, canteenStorage);
    }



}
