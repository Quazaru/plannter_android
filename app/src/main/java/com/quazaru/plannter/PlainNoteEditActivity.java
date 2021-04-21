package com.quazaru.plannter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.TextView;

public class PlainNoteEditActivity extends AppCompatActivity {
    String noteTitleString;
    String noteInnerTextString;
    String noteTimeString;
    String noteTagsString;

    EditText noteTitleView;
    EditText noteInnerTextView;
    TextView noteTimeView;
    TextView noteTagsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain_note_edit);

        Intent intent = getIntent();
        noteTitleString = intent.getStringExtra("noteTitle");
        noteInnerTextString = intent.getStringExtra("noteInnerText");
        noteTimeString = intent.getStringExtra("noteTimeString");
        noteTagsString = intent.getStringExtra("noteTagsString");

        noteTitleView = findViewById(R.id.tvNoteTitle_opened);
        noteInnerTextView = findViewById(R.id.etNoteInnerText_opened);
        noteTimeView = findViewById(R.id.tvNoteTime_opened);
        noteTagsView = findViewById(R.id.tvNoteTags_opened);

        noteTitleView.setText(noteTitleString);
        noteInnerTextView.setText(noteInnerTextString);
        noteTimeView.setText(noteTimeString);
        noteTagsView.setText(noteTagsString);

        noteTitleView.setVerticalScrollBarEnabled(true);
        noteTitleView.setMovementMethod(new ScrollingMovementMethod());
        noteInnerTextView.setVerticalScrollBarEnabled(true);
        noteInnerTextView.setMovementMethod(new ScrollingMovementMethod());




    }
}