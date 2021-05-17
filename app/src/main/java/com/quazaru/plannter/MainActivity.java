package com.quazaru.plannter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.quazaru.plannter.database.NoteDatabase.NoteSQLQueryFormer;
import com.quazaru.plannter.database.NoteDatabase.NoteViewModel;
import com.quazaru.plannter.myAdapters.CommonTapeAdapter;
import com.quazaru.plannter.database.NoteDatabase.PlainNote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvTest;
    RecyclerView rvMainTape;
    FloatingActionButton addBtn;
    NoteViewModel viewModel;
    Spinner sortSpinner;

    MutableLiveData<String> selectedSort = new MutableLiveData<>();
    MutableLiveData<List<PlainNote>> notes = new MutableLiveData<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvTest = findViewById(R.id.tvTestArea);
        addBtn = findViewById(R.id.fabAddNote);
        sortSpinner = findViewById(R.id.mainActivity_sortingSpinner);

        // set initial value
        notes.setValue(new ArrayList<PlainNote>(Arrays.asList(new PlainNote[0])));
        selectedSort.setValue("id DESC");

        rvMainTape = findViewById(R.id.rvCommonNoteList);
        CommonTapeAdapter tapeAdapter = new CommonTapeAdapter(this, notes.getValue());
        rvMainTape.setAdapter(tapeAdapter);
        rvMainTape.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this, getDefaultViewModelProviderFactory()).get(NoteViewModel.class);
        // Observe database changes, renew notes
        viewModel.readSortedData(
                NoteSQLQueryFormer.formOrderByQuery(
                        selectedSort.getValue().split(" ")[0],
                        selectedSort.getValue().split(" ")[1] ))
                .observe(this, (notes) -> {
                    this.notes.setValue(getSortedNotes(notes, selectedSort.getValue()));
                });
        // Observe sortType changing, renew notes
        selectedSort.observe(this, (sortConditions) -> {
            notes.setValue(getSortedNotes(notes.getValue(), sortConditions));
        });
        // Observe notes and renew UI
        notes.observe(this, (notes) -> {
            rvMainTape.setAdapter(new CommonTapeAdapter(this, notes));
        });



        // Take clicked note data and open it to edit
        addBtn.setOnClickListener((v) -> {
            PlainNote newNote = new PlainNote("Hello world", "");
            Intent intent = new Intent(this, PlainNoteEditActivity.class);
            intent.putExtra("noteTitle", newNote.getTitle());
            intent.putExtra("noteInnerText", newNote.getInnerText());
            intent.putExtra("noteTimeString", newNote.getNoteTimeString());
            intent.putExtra("noteTagsString", newNote.getTags());
            intent.putExtra("noteIsCheckList", newNote.isCheckList());
            this.startActivity(intent);
        });
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = getResources().getStringArray(R.array.sort_spinner_entries)[position];
                String[] sortConditions = itemValue.split(" ");
                StringBuilder newSelectedSort = new StringBuilder();
                switch (sortConditions[0]) {
                    case "Date":
                        newSelectedSort.append("timeInMillis ");
                        break;
                    case "Alphabet":
                        newSelectedSort.append("title ");
                        break;
                    default:
                        newSelectedSort.append("id ");

                }
                switch (sortConditions[1]) {
                    case "↑":
                        newSelectedSort.append("ASC");
                        break;
                    default:
                        newSelectedSort.append("DESC");
                }
                selectedSort.setValue(newSelectedSort.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    // Sort notes List
    public List<PlainNote> getSortedNotes(List<PlainNote> notes, String sortConditions) {
        List<PlainNote> newNotes = notes;
        String[] conditions = sortConditions.split(" ");
        if(newNotes.size() > 0) {
            switch (conditions[0]) {
                case("timeInMillis"):
                    newNotes.sort(PlainNote.NOTES_TIME_COMPARATOR);
                    break;
                case("title"):
                    newNotes.sort(PlainNote.NOTES_TITLE_COMPARATOR);
                    break;
                default:
                    newNotes.sort(PlainNote.NOTES_ID_COMPARATOR);
            }
            if(conditions[1].equals("DESC")) {Collections.reverse(newNotes); }
            return newNotes;
        }
        return new ArrayList<PlainNote>(Arrays.asList(new PlainNote[0]));
    }

    

    

}