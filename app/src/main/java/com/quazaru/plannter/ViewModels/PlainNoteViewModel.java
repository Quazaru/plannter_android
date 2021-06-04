package com.quazaru.plannter.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quazaru.plannter.database.NoteDatabase.PlainNote;
import com.quazaru.plannter.database.myListeners.MyEventNotifier;
import com.quazaru.plannter.database.myListeners.NoteSaveEvent;

public class PlainNoteViewModel extends ViewModel {

    private final MutableLiveData<PlainNote> note = new MutableLiveData<>();
    private final MutableLiveData<String> innerText = new MutableLiveData<>();
    private final MutableLiveData<String> checkedIndexes = new MutableLiveData<>();
    private final MutableLiveData<MyEventNotifier> noteToSaveNotifier = new MutableLiveData<>();
    private final MutableLiveData<MyEventNotifier> noteDidSaveNotifier = new MutableLiveData<>();

    public void setDataInnerText(String innerText) {
        this.innerText.setValue(innerText);
    }
    public LiveData<String> getDataInnerText() {
        return innerText;
    }

    public MutableLiveData<String> getCheckedIndexes() {return checkedIndexes;}
    public void setCheckedIndexes(String checkedIndexes) {this.checkedIndexes.setValue(checkedIndexes); }

    public MutableLiveData<PlainNote> getNote() {return note;}
    public void setNote(PlainNote note) {this.note.setValue(note);}

    public MutableLiveData<MyEventNotifier> getNoteToSaveNotifier() { return noteToSaveNotifier;}
    public void setNoteToSaveNotifier(MyEventNotifier noteToSaveNotifier) { this.noteToSaveNotifier.setValue(noteToSaveNotifier);}

    public MutableLiveData<MyEventNotifier> getNoteDidSaveNotifier() { return noteDidSaveNotifier;}
    public void setNoteDidSaveNotifier(MyEventNotifier noteDidSaveNotifier) { this.noteDidSaveNotifier.setValue(noteDidSaveNotifier);}
}
