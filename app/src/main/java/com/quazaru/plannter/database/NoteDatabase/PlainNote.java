package com.quazaru.plannter.database.NoteDatabase;

import android.widget.Toast;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.quazaru.plannter.notes.drawableNote;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Locale;

@Entity(tableName = "note_table")
public class PlainNote implements drawableNote {
    @PrimaryKey(autoGenerate = true)
    protected int id;
    @ColumnInfo(name = "title")
    protected String title ;
    @ColumnInfo(name = "innerText")
    protected String innerText ;
    @Ignore
    protected String[] tagArray;
    @ColumnInfo(name = "tags")
    protected String tags;
    @ColumnInfo(name = "editTime")
    @Ignore
    protected Calendar noteEditTime;
    @ColumnInfo(name = "timeInMillis")
    protected long timeMillis;
    @ColumnInfo(name = "timeString")
    protected String noteTimeString = "";
    @ColumnInfo(name = "isCheckList", defaultValue = "0")
    protected int isCheckList;

    @Ignore
    public static final Comparator<PlainNote> NOTES_ID_COMPARATOR = new Comparator<PlainNote>() {
        @Override
        public int compare(PlainNote o1, PlainNote o2) {
            if(o1.id == o2.id) { return 0; }
            if(o1.id > o2.id) { return 1; }
            return -1;
        }
    };
    @Ignore
    public static final Comparator<PlainNote> NOTES_TIME_COMPARATOR = new Comparator<PlainNote>() {
        @Override
        public int compare(PlainNote o1, PlainNote o2) {
            if(o1.timeMillis == o2.timeMillis) { return 0; }
            if(o1.timeMillis > o2.timeMillis) { return 1; }
            return -1;
        }
    };
    @Ignore
    public static final Comparator<PlainNote> NOTES_TITLE_COMPARATOR = new Comparator<PlainNote>() {
        @Override
        public int compare(PlainNote o1, PlainNote o2) {
            return o1.title.compareTo(o2.title);
        }
    };


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
        timeMillis = this.noteEditTime.getTimeInMillis();
        DateFormat df = new SimpleDateFormat("d MMM yyyy HH:mm");
        this.noteTimeString = df.format(noteEditTime.getTime());
    }
    public PlainNote(String title, String innerText, String timeString) {
        setNoteToDefault();
        this.title = title;
        this.innerText = innerText;
        this.noteTimeString = timeString;
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
        this.isCheckList = 0;

        this.noteEditTime = new GregorianCalendar();
        timeMillis = this.noteEditTime.getTimeInMillis();
        DateFormat df = new SimpleDateFormat("d MMM yyyy HH:mm");
        this.noteTimeString = df.format(noteEditTime.getTime());
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


    public long getTimeMillis() {
        return timeMillis;
    }
    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }
    public int isCheckList() {
        return isCheckList;
    }
    public void setCheckList(int checkList) {
        isCheckList = checkList;
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
    public int getId() {return id;};
    public void setId(int id) {this.id = id; }
}
