package com.quazaru.plannter.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.quazaru.plannter.R;
import com.quazaru.plannter.ViewModels.PlainNoteViewModel;
import com.quazaru.plannter.database.NoteDatabase.PlainNote;
import com.quazaru.plannter.database.myListeners.MyEventNotifier;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;

public class PlainNoteInnerTextFragment extends Fragment {
    EditText innerTextView;
    MyEventNotifier noteDidSaveNotifier;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plain_note_inner_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        PlainNoteViewModel noteViewModel = new ViewModelProvider(requireActivity()).get(PlainNoteViewModel.class);
        innerTextView = view.findViewById(R.id.note_edit_etInnerText);
        innerTextView.setVerticalScrollBarEnabled(true);
        innerTextView.setMovementMethod(new ScrollingMovementMethod());
        innerTextView.setText(noteViewModel.getNote().getValue().getInnerText());
        noteViewModel.getNote().observe(requireActivity(), currentNote -> {
            innerTextView.setText(currentNote.getInnerText());
        });


        // SAVING NOTE
        noteDidSaveNotifier = noteViewModel.getNoteDidSaveNotifier().getValue();
        MyEventNotifier noteSaveNotifier = noteViewModel.getNoteToSaveNotifier().getValue();
        noteSaveNotifier.addObserver(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String[] preferences = evt.getPropertyName().split("_");
                Log.d("SCATCH", "preferences[0] = " + preferences[0] + " - fragment type");

                if(!preferences[0].equals("text")) {
                    Log.d("SCATCH", "preferences[0] != " + "text" + " - fragment type checking");
                    return; }
                Log.d("SCATCH", Arrays.toString(preferences));
                PlainNote currentNote = noteViewModel.getNote().getValue();
                currentNote.setInnerText(innerTextView.getText().toString());
                noteViewModel.setNote(currentNote);
                noteDidSaveNotifier.notifyObservers();
            }
        });
    }
}
