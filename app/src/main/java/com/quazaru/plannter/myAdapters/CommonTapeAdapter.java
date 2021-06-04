package com.quazaru.plannter.myAdapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.quazaru.plannter.PlainNoteEditActivity;
import com.quazaru.plannter.R;
import com.quazaru.plannter.database.NoteDatabase.NoteViewModel;
import com.quazaru.plannter.database.NoteDatabase.PlainNote;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CommonTapeAdapter extends RecyclerView.Adapter<CommonTapeAdapter.MyViewHolder> {
    List<PlainNote> plainNotes;
    Context context;
    NoteViewModel viewModel;
    public CommonTapeAdapter(Context context, List<PlainNote> plainNotes) {
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
        String innerText = plainNotes.get(position).getInnerText();
        String noteTitle = (plainNotes.get(position).getTitle());
        String noteInnerText = (plainNotes.get(position).getInnerText());
        String noteTimeString = (plainNotes.get(position).getNoteTimeString());
        String noteTagsString = (plainNotes.get(position).getTags());
        int noteId = plainNotes.get(position).getId();
        int isCheckList = plainNotes.get(position).isCheckList();

        holder.tvTitle.setText(noteTitle);
        holder.tvInnerText.setText(innerText);
        holder.tvDate.setText(noteTimeString);

        holder.viewHolder.setOnClickListener((v) -> {
            Intent intent = new Intent(context, PlainNoteEditActivity.class);

            intent.putExtra("noteTitle", noteTitle);
            intent.putExtra("noteInnerText", noteInnerText);
            intent.putExtra("noteTimeString", noteTimeString);
            intent.putExtra("noteTagsString", noteTagsString);
            intent.putExtra("noteId", noteId);
            intent.putExtra("noteIsCheckList", isCheckList);
            context.startActivity(intent);
        });

        holder.viewHolder.setOnLongClickListener((v) -> {
            AlertDialog dialog = createNoteDeleteConfirmDialog(context,
                    "Do you really want to delete \"" + noteTitle + "\"",
                    noteId);
            dialog.show();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return plainNotes.size();
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

    public AlertDialog createNoteDeleteConfirmDialog(Context context, String alertMessage, long noteId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete this note?")
                .setMessage(alertMessage)
                .setPositiveButton(R.string.ALERT_CONFIRM, ((dialog, which) -> {
                    NoteViewModel.getViewModel(context, (AppCompatActivity) context).deleteNoteById(noteId);
                }))
                .setNegativeButton(R.string.ALERT_DENY, (dialog, which) -> {});
        AlertDialog dialog = builder.create();

        return dialog;
    }


}
