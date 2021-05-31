package com.quazaru.plannter.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quazaru.plannter.database.NoteDatabase.PlainNote;

public class PlainNoteViewModel extends ViewModel {
    private final MutableLiveData<PlainNote> note = new MutableLiveData<PlainNote>();
    private final MutableLiveData<String> innerText = new MutableLiveData<>();
    public void setData(PlainNote note) {
        Log.d("RCATCH", "Note to save - " + note.toString());
        this.note.setValue(note);
    }

    public LiveData<PlainNote> getData() {
        return this.note;
    }
    public void setDataInnerText(String innerText) {
        this.innerText.setValue(innerText);
    }
    public LiveData<String> getDataInnerText() {
        return this.innerText;
    }
}
