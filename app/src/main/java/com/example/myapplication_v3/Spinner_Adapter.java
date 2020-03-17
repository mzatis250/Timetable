package com.example.myapplication_v3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Spinner_Adapter extends ArrayAdapter<String> {
    private Context context;
    private String[]oranev;
    private int[]images;
    Spinner_Adapter(Context context, String[] oranev, int[] images) {
        super(context, R.layout.spinner_images,oranev);
        this.context = context;
        this.oranev = oranev;
        this.images = images;
    }
    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") View row=inflater.inflate(R.layout.spinner_images, null);
            TextView spinner_tw= row.findViewById(R.id.spinner_tw);
            ImageView imageView= row.findViewById(R.id.imageView2);
            spinner_tw.setText(oranev[position]);
            imageView.setImageResource(images[position]);
        return row;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint({"ViewHolder", "InflateParams"}) View row=inflater.inflate(R.layout.spinner_images, null);
        TextView spinner_tw=row.findViewById(R.id.spinner_tw);
        ImageView imageView=row.findViewById(R.id.imageView2);
        spinner_tw.setText(oranev[position]);
        imageView.setImageResource(images[position]);
        return row;
    }
}
