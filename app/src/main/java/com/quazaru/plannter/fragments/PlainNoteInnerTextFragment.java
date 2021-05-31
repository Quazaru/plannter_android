package com.quazaru.plannter.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.quazaru.plannter.R;
import com.quazaru.plannter.ViewModels.PlainNoteViewModel;

public class PlainNoteInnerTextFragment extends Fragment {
    EditText innerTextView;
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
        noteViewModel.getDataInnerText().observe(requireActivity(), innerText -> {
            innerTextView.setText(innerText);
        });
    }
}
