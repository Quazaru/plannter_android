package com.quazaru.plannter.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.quazaru.plannter.PlainNoteEditActivity;
import com.quazaru.plannter.R;
import com.quazaru.plannter.ViewModels.PlainNoteViewModel;
import com.quazaru.plannter.database.myListeners.MyEventNotifier;
import com.quazaru.plannter.myAdapters.ChecklistTapeAdapter;
import com.quazaru.plannter.myAdapters.CommonTapeAdapter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChecklistFragment extends Fragment {
    RecyclerView rvChecklistTape;
    String[] rowsArray;

    MyEventNotifier noteDidSaveNotifier;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_checklist, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvChecklistTape = view.findViewById(R.id.ChecklistFragmentRecyclerView);
        rvChecklistTape.setLayoutManager(new LinearLayoutManager(requireContext()));
        PlainNoteViewModel viewModel = new ViewModelProvider(requireActivity()).get(PlainNoteViewModel.class);

        String[] textRows = viewModel.getDataInnerText().getValue().split("\n");
        rowsArray = textRows;
        viewModel.getDataInnerText().observe(requireActivity(), innerText -> {
            String[] newTextRows = innerText.split("\n");
            rowsArray = textRows;
            Log.d("RCATCH", "ChecklistFragment: textRows: " + Arrays.toString(textRows) + " [ " +  textRows.length + " ]");
            ChecklistTapeAdapter newTapeAdapter = new ChecklistTapeAdapter(requireActivity() , textRows);
            rvChecklistTape.setAdapter(newTapeAdapter);
        });
        if(rowsArray != null && rowsArray.length > 0) {
            ChecklistTapeAdapter tapeAdapter = new ChecklistTapeAdapter(requireActivity() , rowsArray);
            rvChecklistTape.setAdapter(tapeAdapter);
        }


        // SAVING NOTE
        noteDidSaveNotifier = viewModel.getNoteDidSaveNotifier().getValue();
        MyEventNotifier noteToSaveNotifier = viewModel.getNoteToSaveNotifier().getValue();
        noteToSaveNotifier.addObserver(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getPropertyName() == "checklist") {
                    String newInnerText = ((ChecklistTapeAdapter)rvChecklistTape.getAdapter()).getElementsString();
                    viewModel.setDataInnerText(newInnerText);
                    noteDidSaveNotifier.notifyObservers();
                }
            }
        });





    }
    public String getRowsArrayString() {
        StringBuilder str = new StringBuilder();
        for (String element:
                rowsArray) {
            str.append(element + "\n");
        }
        return str.toString();
    }
}