package com.example.photos;

import android.graphics.Bitmap;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Photo implements Serializable
{
    //private transient Bitmap image;
    private String imagePath;
    private String caption;
    private ArrayList<String> personTag;
    private ArrayList<String> locationTag;
    private static final long serialVersionUID = 1L;

    /**
     * constructor for the photo object
     */
    public Photo()
    {
        caption = "";
        personTag = new ArrayList<String>();
        locationTag = new ArrayList<String>();
    }

    /**
     * set the path of the photo
     * @param path - path parameter
     */
    public void setPath(String path)
    {
        imagePath = path;
    }

    /**
     * set the file of the photo
     * @param f - file parameter
     */
    /*public void setImage(Bitmap f)
    {
        image = f;
    }*/

    /**
     * set the caption of the photo
     * @param c - string parameter of caption
     */
    public void setCaption(String c)
    {
        caption = c;
    }

    /**
     * get the caption fo the photo
     * @return string for caption
     */
    public String getCaption()
    {
        return caption;
    }

    /**
     * get the file for this photo
     * @return file of photo
     */
    /*public Bitmap getImage()
    {
        return image;
    }*/

    /**
     * gets the path of this photo
     * @return path of photo
     */
    public String getPath()
    {
        return imagePath;
    }

    /**
     * get arraylist of tags of this photo
     * @return tags of the photo
     */
    public ArrayList<String> getPerson()
    {
        return personTag;
    }
    public ArrayList<String> getLocation()
    {
        return locationTag;
    }

    /**
     * add a tag to the photo
     * @param p - tag value
     */
    public void addPerson(String p)
    {
         personTag.add(p);
    }

    public void addLocation(String l)
    {
        locationTag.add(l);
    }

    public void removePerson_tag(int pos)
    {
        personTag.remove(pos);
    }
    public void removeLocation_tag(int pos)
    {
        locationTag.remove(pos);
    }
    /**
     * print out the photo file name
     * @return the file name of photo
     */
    public String toString()
    {
        StringTokenizer st = new StringTokenizer(imagePath, "\\");
        String result = "";
        while(st.hasMoreTokens())
        {
            result = st.nextToken();
        }
        return result;
    }
}

