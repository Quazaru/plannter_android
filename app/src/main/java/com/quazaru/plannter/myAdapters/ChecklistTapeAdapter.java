package com.quazaru.plannter.myAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.quazaru.plannter.R;

public class ChecklistTapeAdapter extends RecyclerView.Adapter<ChecklistTapeAdapter.MyViewHolder> {
    Context context;
    String[] elements;
    public ChecklistTapeAdapter(Context context, String[] elements) {
        this.context = context;
        this.elements = elements;
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
        holder.checkBox.setText(elements[position]);
    }

    @Override
    public int getItemCount() {
        return elements.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.vholder_checklist_row_checkbox);
        }
    }
}
