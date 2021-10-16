package com.example.photos;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable{
    private String name;
    private ArrayList<Photo> photos;
    private static final long serialVersionUID = 1L;

    /**
     * constructor for album to set its name and create an arraylist for photos
     * @param albumName - name of the album
     */
    public Album(String albumName)
    {
        name = albumName;
        photos = new ArrayList<Photo>();
    }

    public void addPhoto(Photo p)
    {
        photos.add(p);
    }
    /**
     * changes the name of the album
     * @param albumName - name of the album
     */
    public void setName(String albumName)
    {
        name = albumName;
    }

    /**
     * get the name of the album
     * @return the name of the album
     */
    public String getName()
    {
        return name;
    }

    /**
     * get the arraylist of photos in this album
     * @return arraylist of photos
     */
    public ArrayList<Photo> getPhotos()
    {
        return photos;
    }

    /**
     * gets the size of this album
     * @return size of album
     */
    public int size()
    {
        return photos.size();
    }

    /**
     * print out the name of the album
     */

    public String toString()
    {
        return name+" ("+size()+")";
    }
}
