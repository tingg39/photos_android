package com.example.photos;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Slideshow extends AppCompatActivity {

    ImageView img;
    int pic_pos;
    public static final String PIC_POS = "position";
    public TextView personTags;
    public TextView locationTags;
    public EditText pt;
    public EditText lt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slideshow);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        pic_pos = bundle.getInt(PIC_POS);

        fillPicture(pic_pos);
        pt = findViewById(R.id.personTag);
        lt = findViewById(R.id.locationTag);
        personTags = findViewById(R.id.person_tags);
        locationTags = findViewById(R.id.location_tags);
        populateTags();
    }

    private void fillPicture(int pos) {
        ImageView iv = (ImageView) findViewById(R.id.bigImage);
        iv.setImageURI(Uri.parse(Controller.albums.get(Controller.currentAlbum_pos).getPhotos().get(pos).getPath()));

    }

    public void addPerson(View view)
    {
        String pv = pt.getText().toString();
        if (pv == null || pv.equals("")) {
            Bundle bundle = new Bundle();
            bundle.putString(AlbumDialog.MESSAGE_KEY,
                    "please enter a person tag value");
            DialogFragment newFragment = new AlbumDialog();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "badfields");
            return;
        }
        Controller.albums.get(Controller.currentAlbum_pos).getPhotos().get(pic_pos).addPerson(pv);
        try
        {
            Controller.save();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        populateTags();
    }

    public void addLocation(View view)
    {
        String lv = lt.getText().toString();
        if (lv == null || lv.equals("")) {
            Bundle bundle = new Bundle();
            bundle.putString(AlbumDialog.MESSAGE_KEY,
                    "please enter a person tag value");
            DialogFragment newFragment = new AlbumDialog();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "badfields");
            return;
        }
        Controller.albums.get(Controller.currentAlbum_pos).getPhotos().get(pic_pos).addLocation(lv);
        try
        {
            Controller.save();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        populateTags();
    }
    private void populateTags(){
        Photo p = Controller.albums.get(Controller.currentAlbum_pos).getPhotos().get(pic_pos);
        ArrayList<String> pt = p.getPerson();
        ArrayList<String> lt = p.getLocation();
        if(pt == null)
        {
            personTags.setText("");
        }else
        {
            String pv = "";
            for(String s : pt)
            {
                pv += s;
            }
            personTags.setText(pv);
        }

        if(lt == null)
        {
            locationTags.setText("");
        }else
        {
            String lv = "";
            for(String s : lt)
            {
                lv += s;
            }
            locationTags.setText(lv);
        }
    }

    public void prev(View view)
    {
        if(pic_pos == 0)
        {
            Bundle bundle = new Bundle();
            bundle.putString(AlbumDialog.MESSAGE_KEY,
                    "Already the first photo");
            DialogFragment newFragment = new AlbumDialog();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "badfields");
            return;
        }
        pic_pos -= 1;
        fillPicture(pic_pos);
        populateTags();
    }

    public void next(View view)
    {
        if(pic_pos == Controller.albums.get(Controller.currentAlbum_pos).size()-1)
        {
            Bundle bundle = new Bundle();
            bundle.putString(AlbumDialog.MESSAGE_KEY,
                    "Already the last photo");
            DialogFragment newFragment = new AlbumDialog();
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "badfields");
            return;
        }
        pic_pos += 1;
        fillPicture(pic_pos);
        populateTags();
    }
}
