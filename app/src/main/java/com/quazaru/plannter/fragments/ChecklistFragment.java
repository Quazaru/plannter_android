package com.quazaru.plannter.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.quazaru.plannter.PlainNoteEditActivity;
import com.quazaru.plannter.R;
import com.quazaru.plannter.ViewModels.PlainNoteViewModel;
import com.quazaru.plannter.myAdapters.ChecklistTapeAdapter;
import com.quazaru.plannter.myAdapters.CommonTapeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChecklistFragment extends Fragment {
    RecyclerView rvChecklistTape;
    String[] rowsArray;
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
        PlainNoteViewModel viewModel = new ViewModelProvider(requireActivity()).get(PlainNoteViewModel.class);
        viewModel.getData().observe(requireActivity(), note -> {
            String[] textRows = note.getInnerText().split("\n");
            rowsArray = textRows;
            /// ↓ PROBLEM ///
            for (String row: textRows) {
                Log.d("RCATCHARR", row + " - row");
            }
            ///
            Log.d("RCATCH", "innerText: " + note.getInnerText());
            Log.d("RCATCH", "textRows: " + Arrays.toString(textRows) + " [ " +  textRows.length + " ]");
            ChecklistTapeAdapter tapeAdapter = new ChecklistTapeAdapter(requireActivity() , textRows);
            rvChecklistTape.setAdapter(tapeAdapter);
        });
        if(rowsArray.length > 0) {
            ChecklistTapeAdapter tapeAdapter = new ChecklistTapeAdapter(requireActivity() , rowsArray);
            rvChecklistTape.setAdapter(tapeAdapter);
        }




    }
}