package com.quazaru.plannter.database.NoteDatabase;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    Application application;
    NoteRepository repository;
    public LiveData<List<PlainNote>> data;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repository = new NoteRepository(application);
    }

    public static NoteViewModel getViewModel(Context context, AppCompatActivity activity) {
        NoteViewModel viewModel = new ViewModelProvider((ViewModelStoreOwner) context, activity.getDefaultViewModelProviderFactory()).get(NoteViewModel.class);
        return viewModel;
    }

    public LiveData<List<PlainNote>> readAllData() {
        if(data == null) {
            return repository.readAllData();
        }
        return data;
    }

    public LiveData<List<PlainNote>> readSortedData(String sortCondition) {
        return repository.readSortedData(new SimpleSQLiteQuery(sortCondition));
    }


    public void addNote(PlainNote note) {
        repository.addNote(note);
    }
    public void deleteNote(PlainNote note) { repository.deleteNote(note);}
    public void deleteNoteById(long noteId) { repository.deleteNoteById(noteId);}
}
