package com.example.photos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddEditAlbum extends AppCompatActivity {

    public static final String ALBUM_OPER = "Operation";
    static ArrayList<Album> albums;
    static int add;
    private EditText name_text;
    boolean confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_album);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name_text = findViewById(R.id.name);

        // get the fields

        // see if info was passed in to populate fields
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            add = bundle.getInt(ALBUM_OPER);
            if(add == 0) {
                Album album = Controller.albums.get(Controller.currentAlbum_pos);
                name_text.setText(album.getName());
            }
        }
    }

    public void saveAlbum(View view) {
        // gather all data from text fields
        String name = name_text.getText().toString();

        // pop up dialog if errors in input, and return
        // name and year are mandatory
        if (name == null || name.equals("")) {
            Bundle bundle = new Bundle();
            bundle.putString(AlbumDialog.MESSAGE_KEY,
                    "Name is required");
            DialogFragment newFragment = new AlbumDialog();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "badfields");
            return; // does not quit activity, just returns from method
        }

        for(Album a:Controller.albums)
        {
            if(a.getName().equals(name))
            {
                Bundle bundle = new Bundle();
                bundle.putString(AlbumDialog.MESSAGE_KEY,
                        "Album already exists");
                DialogFragment newFragment = new AlbumDialog();
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(), "badfields");
                return; // does not quit activity, just returns from method
            }
        }

        if(add == 1)
        {
            Controller.albums.add(new Album(name));
            try
            {
                Controller.save();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            Controller.albums.get(Controller.currentAlbum_pos).setName(name);
            try {
                Controller.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(this, Albums.class);
        Controller.currentAlbum_pos = -1;
        startActivity(intent);
    }

    public void deleteAlbum(View view)
    {
        if(add == 1)
        {
            Bundle bundle = new Bundle();
            bundle.putString(AlbumDialog.MESSAGE_KEY,
                    "Create the album first");
            DialogFragment newFragment = new AlbumDialog();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "badfields");
            return;
        }

        if(Controller.albums.size() == 0)
        {
            Bundle bundle = new Bundle();
            bundle.putString(AlbumDialog.MESSAGE_KEY,
                    "No albums to delete");
            DialogFragment newFragment = new AlbumDialog();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "badfields");
            return;
        }
        String album_name = Controller.albums.get(Controller.currentAlbum_pos).getName();
        name_text.setText(album_name);
        // pop up dialog if errors in input, and return

        Toast.makeText(this,"Album "+album_name+" deleted", Toast.LENGTH_SHORT).show();

        Controller.albums.remove(Controller.currentAlbum_pos);

        // does not quit activity, just returns from method
        try {
            Controller.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, Albums.class);
        Controller.currentAlbum_pos = -1;
        startActivity(intent);
    }

    public void openAlbum(View view)
    {
        Intent intent = new Intent(this, Pictures.class);
        startActivity(intent);
    }
}
