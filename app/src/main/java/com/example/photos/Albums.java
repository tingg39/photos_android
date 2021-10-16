package com.example.photos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.*;
import android.content.Context;

import java.util.ArrayList;

public class Albums extends AppCompatActivity {

    private static final int EDIT_ALBUM_CODE = 0;
    private static final int ADD_ALBUM_CODE = 1;
    public File file = new File("/data/data/com.example.photos/files/user.dat");
    public ListView albums_list;
    public ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Controller.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums);

        if(!file.exists())
        {
            Context context = this;
            File file = new File(context.getFilesDir(), "data.dat");
            try {
                file.createNewFile();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        //Controller.addAlbum(new Album("Second"));
        albums_list = findViewById(R.id.albums_list);
        adapter = new ArrayAdapter<Album>(this, R.layout.album, Controller.albums);
        albums_list.setAdapter(adapter);

        // show movie for possible edit when tapped
        albums_list.setOnItemClickListener((p, V, pos, id) ->
                showAlbum(pos));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                addAlbum();
                return true;
            case R.id.action_search:
                searchAlbum();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void searchAlbum() {
        Intent intent = new Intent(this, SearchPage.class);
        startActivity(intent);
    }

    private void addAlbum() {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, AddEditAlbum.class);
        bundle.putInt(AddEditAlbum.ALBUM_OPER, ADD_ALBUM_CODE);
        intent.putExtras(bundle);
        startActivityForResult(intent, ADD_ALBUM_CODE);
    }

    public void showAlbum(int pos)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(AddEditAlbum.ALBUM_OPER, EDIT_ALBUM_CODE);
        Intent intent = new Intent(this, AddEditAlbum.class);
        intent.putExtras(bundle);
        Controller.currentAlbum_pos = pos;
        startActivityForResult(intent, EDIT_ALBUM_CODE);
    }

    /**
     * save everything to user.dat when the application is closed
     */


    @Override
    public void onStop()
    {
        super.onStop();
        try
        {
            Controller.save();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            Controller.save();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        try
        {
            Controller.load();
            adapter.notifyDataSetChanged();
            albums_list.setAdapter(adapter);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        try
        {
            Controller.load();
            adapter.notifyDataSetChanged();
            albums_list.setAdapter(adapter);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
