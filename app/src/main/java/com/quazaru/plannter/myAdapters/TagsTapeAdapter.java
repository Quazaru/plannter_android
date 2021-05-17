package com.quazaru.plannter.myAdapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quazaru.plannter.PlainNoteEditActivity;
import com.quazaru.plannter.R;

import java.util.zip.Inflater;

public class TagsTapeAdapter extends RecyclerView.Adapter<TagsTapeAdapter.MyViewHolder> {
    Context context;
    String[] tags;
    public TagsTapeAdapter(Context context, String[] tags) {
        this.context = context;
        this.tags = tags;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.viewholder_note_tag_bar, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tagName.setText(tags[position]);
        holder.closeBtn.setOnClickListener((v) -> {
            
        });
    }

    @Override
    public int getItemCount() {
        return tags.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tagName;
        ImageButton closeBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tagName = itemView.findViewById(R.id.vholder_noteTag_tagName);
            closeBtn = itemView.findViewById(R.id.vholder_noteTag_ibClose);
        }
    }

}
