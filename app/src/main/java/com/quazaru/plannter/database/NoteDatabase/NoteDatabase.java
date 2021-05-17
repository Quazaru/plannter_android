package com.quazaru.plannter.database.NoteDatabase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.Entity;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(version = 2, entities = {PlainNote.class}, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDAO noteDAO();
    private static volatile NoteDatabase INSTANCE = null;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static NoteDatabase getDatabase(Context context) {
        final NoteDatabase tempInstance = INSTANCE;
        if(tempInstance != null) {
            return  tempInstance;
        }
        synchronized (NoteDatabase.class) {
            final NoteDatabase instance =
                    Room.databaseBuilder(context.getApplicationContext(),
                                         NoteDatabase.class, "note_database")
                            .addMigrations(MIGRATION_1_2)
                            .build();
            INSTANCE = instance;
            return instance;
        }

    }
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE note_table ADD COLUMN isCheckList INTEGER DEFAULT 0 NOT NULL");
        }
    };
}
