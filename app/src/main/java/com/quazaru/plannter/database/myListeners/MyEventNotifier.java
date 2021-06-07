package com.quazaru.plannter.database.myListeners;

import android.content.Context;

import com.quazaru.plannter.database.NoteDatabase.PlainNote;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MyEventNotifier {
    List<PropertyChangeListener> eventObservers = new ArrayList<>();

    public void notifyObservers(PlainNote note) {
        for (PropertyChangeListener observer: eventObservers) {
            observer.propertyChange(new PropertyChangeEvent(this, "note", note, note));
        }
    }
    public void notifyObservers(String propertyName) {
        for (PropertyChangeListener observer: eventObservers) {
            observer.propertyChange(new PropertyChangeEvent(this, propertyName, 0, 1));
        }
    }
    public void notifyObservers(Context context, String propertyName, Object oldValue, Object newValue) {
        for (PropertyChangeListener observer: eventObservers) {
            observer.propertyChange(new PropertyChangeEvent(context, propertyName, oldValue, newValue));
        }
    }

    public void notifyObservers() {
        for (PropertyChangeListener observer: eventObservers) {
            observer.propertyChange(new PropertyChangeEvent(this, "triggered", 0, 1));
        }
    }

    public void addObserver(PropertyChangeListener newObserver) {
        eventObservers.add(newObserver);
    }
}
