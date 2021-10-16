package com.example.photos;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.*;
import java.util.ArrayList;

public class ImageAdapter extends ArrayAdapter {
    Context context;
    ArrayList pictures;
    public ImageAdapter(Context context, ArrayList pictures)
    {
        super(context, R.layout.grid_item, pictures);
        this.context = context;
        this.pictures = pictures;
    }

    @Override
    public View getView(int position, View currentView, ViewGroup parent){

        if (currentView == null){
            LayoutInflater i = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            currentView = (View) i.inflate(R.layout.grid_item, parent, false);
        }

        ImageView iv = (ImageView) currentView.findViewById(R.id.image);
        TextView tv = (TextView) currentView.findViewById(R.id.caption);

        Photo photo = (Photo) pictures.get(position);
        //iv.setImageBitmap(photo.getImage());
        iv.setImageURI(Uri.parse(photo.getPath()));
        tv.setText(photo.getCaption());

        return currentView;

    }
}
