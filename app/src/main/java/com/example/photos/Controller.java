package com.example.photos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.Serializable;

public class Controller implements Serializable
{
    private static final long serialVersionUID = 1L;
    public static ArrayList<Album> albums = new ArrayList<Album>();
    public static int currentAlbum_pos;

    public static void save() throws IOException
    {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/data/data/com.example.photos/files/user.dat"));
        oos.writeObject(albums);
        oos.close();
    }

    public static void load() throws Exception
    {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/data/data/com.example.photos/files/user.dat"));
        albums = (ArrayList<Album>) ois.readObject();
        ois.close();
    }

    public static ArrayList<Photo> searchTagVal(String tagValue)
    {
        ArrayList<Photo> result = new ArrayList<Photo>();
        for(Album a : albums) {
            for (Photo p : a.getPhotos()) {
                for (String t : p.getPerson()) {
                    if (t.toLowerCase().contains(tagValue.toLowerCase())) {
                        result.add(p);
                    }
                }

                for (String t : p.getLocation()) {
                    if (t.toLowerCase().contains(tagValue.toLowerCase())) {
                        result.add(p);
                    }
                }
            }
        }
        return result;
    }

    public static ArrayList<Photo> searchByType(String tagType, String tagValue)
    {
        ArrayList<Photo> result = new ArrayList<Photo>();
        for(Album a : albums) {
            for (Photo p : a.getPhotos()) {
                if(tagType.toLowerCase().equals("person"))
                {
                    for (String t : p.getPerson()) {
                        if (t.toLowerCase().contains(tagValue.toLowerCase())) {
                            result.add(p);
                        }
                    }
                }else if(tagType.toLowerCase().equals("location"))
                {
                    for (String t : p.getLocation()) {
                        if (t.toLowerCase().contains(tagValue.toLowerCase())) {
                            result.add(p);
                        }
                    }
                }
            }
        }
        return result;
    }
}