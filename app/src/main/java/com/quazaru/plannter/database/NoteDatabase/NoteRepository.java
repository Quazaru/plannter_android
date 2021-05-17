package com.quazaru.plannter.database.NoteDatabase;


import android.app.Application;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.sqlite.db.SupportSQLiteQuery;


import java.util.List;

public class NoteRepository {
    private final NoteDAO noteDAO;
    NoteDatabase db;
    public NoteRepository(Application application) {
        db = NoteDatabase.getDatabase(application);
        noteDAO = db.noteDAO();
    }



    void addNote(PlainNote note) {
        NoteDatabase.databaseWriteExecutor.execute(() -> {
            noteDAO.addNote(note);
        });
    }

    void deleteNote(PlainNote note) {
        NoteDatabase.databaseWriteExecutor.execute(() -> {
            noteDAO.delete(note);
        });
    }

    void deleteNoteById(long noteId) {
        NoteDatabase.databaseWriteExecutor.execute(() -> {
            noteDAO.deleteById(noteId);
        });
    }

    LiveData<List<PlainNote>> readAllData() {
        return noteDAO.readAllData();
    }

    LiveData<List<PlainNote>> readSortedData(SupportSQLiteQuery sortCondition) {
       return noteDAO.readSortedDataByQuery(sortCondition);
    }
    List<PlainNote> getSortedData(SupportSQLiteQuery sortCondition) {
        return noteDAO.getSortedDataByQuery(sortCondition);
    }


}
