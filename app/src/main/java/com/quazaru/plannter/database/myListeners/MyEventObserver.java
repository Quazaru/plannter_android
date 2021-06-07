package com.quazaru.plannter.database.myListeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MyEventObserver {
    public PropertyChangeListener listener;

    public MyEventObserver(PropertyChangeListener listener) {
        this.listener = listener;
    }


}
