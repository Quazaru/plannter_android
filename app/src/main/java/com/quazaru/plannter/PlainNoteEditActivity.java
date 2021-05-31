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
import android.util.Log;
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
import com.quazaru.plannter.myAdapters.ChecklistTapeAdapter;

public class PlainNoteEditActivity extends AppCompatActivity {
    PlainNote currentNote;
    NoteViewModel viewModel;
    PlainNoteViewModel noteViewModel;


    EditText noteTitleView;
    EditText noteInnerTextView;
    TextView noteTimeView;
    RecyclerView noteTagsView;
    RecyclerView noteCheckListRecycleView;
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

        noteTimeView.setText(currentNote.getNoteTimeString());
        noteTagsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        // set smooth scroll to editTexts
        noteTitleView.setVerticalScrollBarEnabled(true);
        noteTitleView.setMovementMethod(new ScrollingMovementMethod());


        backBtn.setOnClickListener((v) -> {
            saveNote(currentNote.getTitle(), currentNote.getInnerText());
            finish();
        });

        // Init and view fragments
        // set data to fragment's visibility
        noteViewModel = new ViewModelProvider(this).get(PlainNoteViewModel.class);
        noteViewModel.setDataInnerText(currentNote.getInnerText());


        Fragment checklistFragment = new ChecklistFragment();
        Fragment textFragment = new PlainNoteInnerTextFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flEditNoteFragment, textFragment)
                .commit();


        // If note is a checklist, set checklist fragment
        if(currentNote.isCheckList() == 1 ) {
            replaceFragment(R.id.flEditNoteFragment, checklistFragment);
            btnIsChecklistSwitcher.setText(getResources().getString(R.string.isChecklist_change_btn_active));
            Log.d("RCATCH", "Checklist Recycler view  - " + noteCheckListRecycleView);
        }

        // Checklist mode switcher handler
        btnIsChecklistSwitcher.setOnClickListener((v) -> {
            Button clickedBtn = (Button) v;

            PlainNote newNote = prepareNoteToSave();
            newNote.setCheckList(currentNote.isCheckList());
            if(newNote.isCheckList() == 0) {
                currentNote.setCheckList(1);
                replaceFragment(R.id.flEditNoteFragment, checklistFragment);
                noteCheckListRecycleView = findViewById(R.id.ChecklistFragmentRecyclerView);
                clickedBtn.setText(getResources().getString(R.string.isChecklist_change_btn_active));
            } else {
                currentNote.setCheckList(0);
                replaceFragment(R.id.flEditNoteFragment, textFragment);
                clickedBtn.setText(getResources().getString(R.string.isChecklist_change_btn_default));
            }
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
           oldInnerText.equals(noteViewModel.getDataInnerText().getValue())) {
            return; // Return if nothing changed
        }
        currentNote.setTitle(noteTitleView.getText().toString());
        currentNote.setInnerText(noteViewModel.getDataInnerText().getValue());
        viewModel = NoteViewModel.getViewModel(this, this);
        viewModel.addNote(currentNote);
    }
    public void saveNote(PlainNote note) {
        viewModel = NoteViewModel.getViewModel(this, this);
        viewModel.addNote(note);
    }

    public PlainNote prepareNoteToSave() {
        PlainNote newNote;
        noteInnerTextView = findViewById(R.id.note_edit_etInnerText);
        noteCheckListRecycleView = findViewById(R.id.ChecklistFragmentRecyclerView);
            newNote = new PlainNote(noteTitleView.getText().toString(), noteViewModel.getDataInnerText().getValue(), new String[] {""});

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

