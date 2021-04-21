package com.quazaru.plannter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.quazaru.plannter.myAdapters.CommonTapeAdapter;
import com.quazaru.plannter.notes.PlainNote;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    TextView tvTest;
    RecyclerView rvMainTape;
    String noteTitles[], noteDescriptions[];
    PlainNote mainNoteList[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTest = findViewById(R.id.tvTestArea);
        noteTitles = getResources().getStringArray(R.array.tempTitleArr);
        noteDescriptions = getResources().getStringArray(R.array.tempDescriptionArr);
        mainNoteList = fillNotesArray(noteTitles, noteDescriptions);

        rvMainTape = findViewById(R.id.rvCommonNoteList);
        CommonTapeAdapter tapeAdapter = new CommonTapeAdapter(this, mainNoteList);
        rvMainTape.setAdapter(tapeAdapter);
        rvMainTape.setLayoutManager(new LinearLayoutManager(this));


    }







    public PlainNote[] fillNotesArray(String noteTitles[], String noteInnerTexts[] )  {
        if(noteTitles == null || noteInnerTexts == null) return new PlainNote[0];
        if(noteTitles.length > noteInnerTexts.length) {
            String[] newNoteInnerTexts = new String[noteTitles.length];
            Arrays.fill(newNoteInnerTexts, " ");
            for (int i = 0; i < noteInnerTexts.length; i++) {
                newNoteInnerTexts[i] = noteInnerTexts[i];
            }
        }


        PlainNote[] noteArr = new PlainNote[noteTitles.length];
       for(int i = 0; i < noteArr.length; i++) {
           noteArr[i] = new PlainNote(noteTitles[i], noteInnerTexts[i]);
       }

        return noteArr;

    }
}