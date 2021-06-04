package com.quazaru.plannter.myAdapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.quazaru.plannter.R;
import com.quazaru.plannter.ViewModels.PlainNoteViewModel;
import com.quazaru.plannter.database.NoteDatabase.PlainNote;

import java.util.Arrays;

public class ChecklistTapeAdapter extends RecyclerView.Adapter<ChecklistTapeAdapter.MyViewHolder> {
    Context context;
    String[] elements;
    public ChecklistTapeAdapter(Context context, String[] elements) {
        this.context = context;
        this.elements = elements;
        Log.d("RCATCH", "Checklist fragment elements - " + Arrays.toString(elements));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.viewholder_checklist_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("RCATCH_CLELEM", "Checklist fragment element " + elements[position] + " [" + position + "] ");
        CheckBox checkbox = holder.checkBox;
        checkbox.setText(elements[position]);
        checkbox.setOnCheckedChangeListener((v, isChecked) -> {
            if(isChecked == true) {
                v.setPaintFlags(v.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                v.setTextColor(Color.GRAY);
            } else {
                v.setPaintFlags((v.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG)));
                v.setTextColor(Color.BLACK);
            }
        });


    }

    @Override
    public int getItemCount() {
        Log.d("RCATCH", "Checklist fragment elements length - " + elements.length);
        return elements.length;

    }
    public String[] getElementsArray() {
        return elements;
    }
    public String getElementsString() {
        StringBuilder elementsString = new StringBuilder();
        for (String element: elements) {
            elementsString.append(element + "\n");
        }
        return elementsString.toString();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.vholder_checklist_row_checkbox);
        }
    }
}
