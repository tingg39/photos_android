package com.example.photos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.*;
import android.os.Bundle;
import java.util.*;
import android.content.Context;

public class SearchPage extends AppCompatActivity{

    public GridView gridview;
    public ImageAdapter adapter;
    public ArrayList<Photo> pl = new ArrayList<Photo>();
    public Context context = this;
    public EditText tt;
    public EditText tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tt = findViewById(R.id.tagtype);
        tv = findViewById(R.id.tagvalue);
        gridview = (GridView) findViewById(R.id.gridView);
        adapter = new ImageAdapter(this, pl);
        gridview.setAdapter(adapter);
    }

    public void searchPhoto(View view)
    {
        String tagvalue = tv.getText().toString();
        String tagtype = tt.getText().toString();

        if (tagvalue == null || tagvalue.equals("")) {
            Bundle bundle = new Bundle();
            bundle.putString(AlbumDialog.MESSAGE_KEY,
                    "Please enter a tag value");
            DialogFragment newFragment = new AlbumDialog();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "badfields");
            return;
        }

        if(tagtype.toLowerCase().equals("person"))
        {
            pl = Controller.searchByType("person", tagvalue);
            adapter = new ImageAdapter(this, pl);
            gridview.setAdapter(adapter);
        }else if(tagtype.toLowerCase().equals("location"))
        {
            pl = Controller.searchByType("location", tagvalue);
            adapter = new ImageAdapter(this, pl);
            gridview.setAdapter(adapter);
        }else if(tagtype == null || tagtype.equals(""))
        {
            pl = Controller.searchTagVal(tagvalue);
            adapter = new ImageAdapter(this, pl);
            gridview.setAdapter(adapter);
        }else
        {
            Bundle bundle = new Bundle();
            bundle.putString(AlbumDialog.MESSAGE_KEY,
                    "Please enter a correct tag type: person or location");
            DialogFragment newFragment = new AlbumDialog();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "badfields");
            return;
        }
    }
}
