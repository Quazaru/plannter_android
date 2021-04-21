package com.quazaru.plannter.notes;

import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


public class PlainNote implements drawableNote{
    protected String title ;
    protected String innerText ;
    protected String[] tagArray;
    protected String tags;

    public Calendar noteEditTime;
    protected String noteTimeString = "";

    public PlainNote(String title, String innerText, String[] tagArray) {
        this.title = title;
        this.innerText = innerText;
        this.tagArray = tagArray;
        StringBuilder tagStr = new StringBuilder("");
        if(tagArray.length > 0) {
            for (String tag: tagArray) {
                tagStr.append(tag + ", ");
            }
        } else if(tagArray.length == 1) {
            tagStr.append(tagArray[0]);
        }
        this.tags = tagStr.toString();

        this.noteEditTime = new GregorianCalendar();
        this.noteTimeString = String.format(Locale.getDefault(), "%1$td.%1$tm.%1$tY %1$tT", noteEditTime.getTime());
    }

    public PlainNote() {
        setNoteToDefault();
    }
    public PlainNote(String title, String innerText) {
        setNoteToDefault();
        this.title = title;
        this.innerText = innerText;
    }




    public void setNoteToDefault() {
        this.title  = " " ;
        this.innerText = " " ;
        this.tagArray = new String[]{" "};
        this.tags = " ";

        this.noteEditTime = new GregorianCalendar();
        this.noteTimeString = String.format(Locale.getDefault(), "%1$td.%1$tm.%1$tY %1$tT", noteEditTime.getTime());

    }

    @Override
    public String toString() {
        return "PlainNote{ " +
                "title= '" + title + '\'' +
                ", innerText= '" + innerText + '\'' +
                ", tagArray= " + Arrays.toString(tagArray) +
                ", tags= '" + tags + '\'' +
                ", noteEditTime= " + noteEditTime +
                ", noteTimeString= '" + noteTimeString + '\'' +
                '}';
    }

    public Calendar getNoteEditTime() {
        return noteEditTime;
    }
    public void setNoteEditTime(Calendar noteEditTime) {
        this.noteEditTime = noteEditTime;
    }
    public String getNoteTimeString() {
        return noteTimeString;
    }
    public void setNoteTimeString(String noteTimeString) {
        this.noteTimeString = noteTimeString;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getInnerText() {
        return innerText;
    }
    public void setInnerText(String innerText) {
        this.innerText = innerText;
    }

    public String[] getTagArray() {
        return tagArray;
    }

    public void setTagArray(String[] tagArray) {
        this.tagArray = tagArray;
    }

    public String getTags() {
        return tags;
    }

}
