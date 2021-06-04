package com.quazaru.plannter.database.myListeners;

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

    public void notifyObservers() {
        for (PropertyChangeListener observer: eventObservers) {
            observer.propertyChange(new PropertyChangeEvent(this, "triggered", 0, 1));
        }
    }

    public void addObserver(PropertyChangeListener newObserver) {
        eventObservers.add(newObserver);
    }
}
