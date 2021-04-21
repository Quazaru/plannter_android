package com.quazaru.plannter.myAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.quazaru.plannter.PlainNoteEditActivity;
import com.quazaru.plannter.R;
import com.quazaru.plannter.notes.PlainNote;

import java.util.ArrayList;

public class CommonTapeAdapter extends RecyclerView.Adapter<CommonTapeAdapter.MyViewHolder> {
    PlainNote[] plainNotes;
    Context context;
    public CommonTapeAdapter(Context context, PlainNote[] plainNotes) {
        this.plainNotes = plainNotes;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.viewholder_note_bar, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String noteTitle = (plainNotes[position].getTitle());
        String noteInnerText = (plainNotes[position].getInnerText());
        String noteTimeString = (plainNotes[position].getNoteTimeString());
        String noteTagsString = (plainNotes[position].getTags());
        holder.tvTitle.setText(noteTitle);
        holder.tvInnerText.setText(noteInnerText);
        holder.tvDate.setText(noteTimeString);


        holder.viewHolder.setOnClickListener((v) -> {
            Intent intent = new Intent(context, PlainNoteEditActivity.class);
            intent.putExtra("noteTitle", noteTitle);
            intent.putExtra("noteInnerText", noteInnerText);
            intent.putExtra("noteTimeString", noteTimeString);
            intent.putExtra("noteTagsString", noteTagsString);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return plainNotes.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout viewHolder;
        TextView tvTitle;
        TextView tvInnerText;
        TextView tvDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvInnerText = itemView.findViewById(R.id.tvNoteInnerText);
            viewHolder = itemView.findViewById(R.id.clNoteBarViewHolder);
            tvDate = itemView.findViewById(R.id.tvNoteTime);

        }
    }
}
