package com.example.photos;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.provider.MediaStore;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;

import java.io.File;
import java.io.IOException;

public class Pictures extends AppCompatActivity {

    public GridView pictures_grid;
    public ImageAdapter adapter;
    public ArrayList<Photo> photoList;
    public Context cont = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pictures);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pictures_grid = (GridView) findViewById(R.id.gridView);
        photoList = Controller.albums.get(Controller.currentAlbum_pos).getPhotos();
        adapter = new ImageAdapter(this, photoList);
        pictures_grid.setAdapter(adapter);
        // show movie for possible edit when tapped
        pictures_grid.setOnItemClickListener((p, V, pos, id) ->
                {
                    PopupMenu options = new PopupMenu(getApplicationContext(), V);
                    options.getMenuInflater().inflate(R.menu.picture_menu, options.getMenu());
                    options.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item){
                            String operation = (item.getTitle().toString());
                            if(operation.equalsIgnoreCase("open"))
                                openPicture(pos);
                            else if(operation.equalsIgnoreCase("delete"))
                                deletePicture(pos);
                            else if(operation.equalsIgnoreCase("move to"))
                                moveTo(pos);
                        return true;
                        }

                    });
                    options.show();
                }
        );

    }
        private void deletePicture(int pos) {
            Controller.albums.get(Controller.currentAlbum_pos).getPhotos().remove(pos);
            try {
                Controller.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pictures_grid.setAdapter(adapter);
        }

        private void moveTo(int pos) {
            AlertDialog.Builder builder = new AlertDialog.Builder(cont);
            builder.setTitle("Move to");
            builder.setMessage("Which album do you want to move to?");
            EditText input = new EditText(cont);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String albumName = input.getText().toString();
                    for(int i = 0; i < Controller.albums.size(); i++)
                    {
                        if(Controller.albums.get(i).getName().equals(albumName))
                        {
                            Controller.albums.get(i).addPhoto(Controller.albums.get(Controller.currentAlbum_pos).getPhotos().get(pos));
                            return;
                        }
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString(AlbumDialog.MESSAGE_KEY,
                            "Album does not exist");
                    DialogFragment newFragment = new AlbumDialog();
                    newFragment.setArguments(bundle);
                    newFragment.show(getSupportFragmentManager(), "badfields");
                    return;
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog al = builder.create();
            builder.show();
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
                //addAlbum();
                addPicture();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addPicture() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 1);

        pictures_grid.setAdapter(adapter);
    }

    public void openPicture(int pos)
    {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, Slideshow.class);
        bundle.putInt(Slideshow.PIC_POS, pos);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {

        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if(resultCode == RESULT_OK && imageReturnedIntent != null){

            Uri selectedImage = imageReturnedIntent.getData();
            /*ImageView iv = new ImageView(this);
            iv.setImageURI(selectedImage);
            BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
            Bitmap selectedImageGal = drawable.getBitmap();

            Photo photoToAdd = new Photo();
            photoToAdd.setImage(selectedImageGal);
            File f = new File(selectedImage.getPath());
            String pathID = f.getAbsolutePath();

            photoToAdd.setPath(pathID);*/
            Photo photoToAdd = new Photo();
            photoToAdd.setPath(selectedImage.toString());
            String filename = pathToFileName(selectedImage);
            photoToAdd.setCaption(filename);

            Controller.albums.get(Controller.currentAlbum_pos).addPhoto(photoToAdd);
            try {
                Controller.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
            photoList = Controller.albums.get(Controller.currentAlbum_pos).getPhotos();
            adapter.notifyDataSetChanged();
            pictures_grid.setAdapter(adapter);
        }
    }

    private String pathToFileName(Uri selectedImage){

        String filename = "not found";

        String[] column = {MediaStore.MediaColumns.DISPLAY_NAME};

        ContentResolver contentResolver = getApplicationContext().getContentResolver();

        Cursor cursor = contentResolver.query(selectedImage, column,
                null, null, null);

        if(cursor != null) {
            try {
                if (cursor.moveToFirst()){
                    filename = cursor.getString(0);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return filename;
    }

}
