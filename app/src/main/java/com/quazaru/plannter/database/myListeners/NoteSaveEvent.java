package com.quazaru.plannter.database.myListeners;

public class NoteSaveEvent {
    private onNoteSaveListener listener;
    public void setOnNoteSaveListener(onNoteSaveListener listener) {
        this.listener = listener;
    }

    public void doNoteSave() {
        listener.onNoteSave();
    }
}
