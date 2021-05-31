package com.quazaru.plannter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.quazaru.plannter.ViewModels.PlainNoteViewModel;
import com.quazaru.plannter.database.NoteDatabase.NoteViewModel;
import com.quazaru.plannter.database.NoteDatabase.PlainNote;
import com.quazaru.plannter.fragments.ChecklistFragment;
import com.quazaru.plannter.fragments.PlainNoteInnerTextFragment;

public class PlainNoteEditActivity extends AppCompatActivity {
    PlainNote currentNote;
    NoteViewModel viewModel;

    EditText noteTitleView;
    EditText noteInnerTextView;
    TextView noteTimeView;
    RecyclerView noteTagsView;
    NestedScrollView nsvOptionsMenu;
    ImageButton imgBtnShowOptions;
    Button btnIsChecklistSwitcher;

    FloatingActionButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain_note_edit);

        // Getting data from clicked note
        Intent intent = getIntent();
        currentNote = new PlainNote();
        currentNote.setTitle(intent.getStringExtra("noteTitle"));
        currentNote.setInnerText(intent.getStringExtra("noteInnerText"));
        currentNote.setNoteTimeString(intent.getStringExtra("noteTimeString"));
        currentNote.setId(intent.getIntExtra("noteId", 0));
        currentNote.setNoteTimeString(intent.getStringExtra("noteTagsString"));
        currentNote.setCheckList(intent.getIntExtra("noteIsCheckList", 0));
        // Initialization of views
        noteTitleView = findViewById(R.id.tvNoteTitle_opened);
        noteInnerTextView = findViewById(R.id.etNoteInnerText_opened);
        noteTimeView = findViewById(R.id.tvNoteTime_opened);
        noteTagsView = findViewById(R.id.rvNoteTags_opened);
        backBtn = findViewById(R.id.noteEdit_fabBackArrow);
        nsvOptionsMenu = findViewById(R.id.noteEdit_nsvOptionsMenu);
        imgBtnShowOptions = findViewById(R.id.noteEdit_imgBtnShowOptions);
        btnIsChecklistSwitcher = findViewById(R.id.noteEdit_btnChangeIsChecklist);

        nsvOptionsMenu.setVisibility(View.GONE);
        imgBtnShowOptions.setOnClickListener((v) -> {
            if(nsvOptionsMenu.getVisibility() == View.VISIBLE) {
                nsvOptionsMenu.setVisibility(View.GONE);
            } else { nsvOptionsMenu.setVisibility(View.VISIBLE); }
        });



        // Set view's text
        noteTitleView.setText(currentNote.getTitle());
        noteInnerTextView.setText(currentNote.getInnerText());
        noteTimeView.setText(currentNote.getNoteTimeString());
        noteTagsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        // set smooth scroll to editTexts
        noteTitleView.setVerticalScrollBarEnabled(true);
        noteTitleView.setMovementMethod(new ScrollingMovementMethod());
        noteInnerTextView.setVerticalScrollBarEnabled(true);
        noteInnerTextView.setMovementMethod(new ScrollingMovementMethod());

        backBtn.setOnClickListener((v) -> {
            saveNote(currentNote.getTitle(), currentNote.getInnerText());
            finish();
        });

        // Init and view fragments
        Fragment checklistFragment = new ChecklistFragment();
        Fragment textFragment = new PlainNoteInnerTextFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flEditNoteFragment, textFragment)
                .commit();
        noteInnerTextView.setVisibility(View.GONE);

        // set data to fragment's visibility
        PlainNoteViewModel noteViewModel = new ViewModelProvider(this).get(PlainNoteViewModel.class);
        noteViewModel.setData(currentNote);

        // If note is a checklist, set checklist fragment
        if(currentNote.isCheckList() == 1 ) {
            replaceFragment(R.id.flEditNoteFragment, checklistFragment);
            btnIsChecklistSwitcher.setText(getResources().getString(R.string.isChecklist_change_btn_active));
            noteViewModel.setData(currentNote);

        }



        // Checklist mode switcher handler
        btnIsChecklistSwitcher.setOnClickListener((v) -> {
            Button clickedBtn = (Button) v;

            PlainNote newNote = prepareNoteToSave();
            newNote.setCheckList(currentNote.isCheckList());

            if(newNote.isCheckList() == 0) {
                currentNote.setCheckList(1);
                replaceFragment(R.id.flEditNoteFragment, checklistFragment);
                clickedBtn.setText(getResources().getString(R.string.isChecklist_change_btn_active));
            } else {
                currentNote.setCheckList(0);
                replaceFragment(R.id.flEditNoteFragment, textFragment);
                clickedBtn.setText(getResources().getString(R.string.isChecklist_change_btn_default));
            }
            noteViewModel.setData(currentNote);
            newNote.setCheckList(currentNote.isCheckList());
            saveNote(newNote);

        });





    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = getIntent();
        saveNote(intent.getStringExtra("noteTitle"),
                intent.getStringExtra("noteInnerText"));

    }

    public void saveNote(String oldTitle, String oldInnerText) {
        if(oldTitle.equals(noteTitleView.getText().toString()) &&
           oldInnerText.equals(noteInnerTextView.getText().toString())) {
            return; // Return if nothing changed
        }
        currentNote.setTitle(noteTitleView.getText().toString());
        currentNote.setInnerText(noteInnerTextView.getText().toString());
        viewModel = NoteViewModel.getViewModel(this, this);
        viewModel.addNote(currentNote);
    }
    public void saveNote(PlainNote note) {
        viewModel = NoteViewModel.getViewModel(this, this);
        viewModel.addNote(note);
    }

    public PlainNote prepareNoteToSave() {
        PlainNote newNote = new PlainNote(noteTitleView.getText().toString(), noteInnerTextView.getText().toString(), new String[] {""});
        newNote.setId(currentNote.getId());
        newNote.setCheckList(currentNote.isCheckList());
        return  newNote;
    }

    public void replaceFragment(int viewId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(viewId, fragment)
                .commit();
    }


}

