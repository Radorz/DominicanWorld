package com.example.dominicanworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter {

    ArrayList<item> birdList = new ArrayList<item>();
    public Adapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        birdList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.rowprovincia, null);
        TextView textView = v.findViewById(R.id.rowtitulo);
        ImageView imageView =  v.findViewById(R.id.imageView);
        textView.setText(birdList.get(position).getbirdName());
        imageView.setImageResource(birdList.get(position).getbirdImage());
        return v;

    }

}