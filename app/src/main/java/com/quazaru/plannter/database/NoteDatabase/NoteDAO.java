package com.quazaru.plannter.database.NoteDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface NoteDAO {
    @Ignore
    public static String SORT_BY_TITLE_ASC = NoteSQLQueryFormer.formOrderByQuery("title", "ASC");;
    @Ignore
    public static String SORT_BY_TITLE_DESC = NoteSQLQueryFormer.formOrderByQuery("title", "DESC");;
    @Ignore
    public static String SORT_BY_ID_ASC = NoteSQLQueryFormer.formOrderByQuery("id", "ASC");;
    @Ignore
    public static String SORT_BY_ID_DESC = NoteSQLQueryFormer.formOrderByQuery("id", "DESC");
    @Ignore
    public static String SORT_BY_TIME_ASC = NoteSQLQueryFormer.formOrderByQuery("timeInMillis", "ASC");
    @Ignore
    public static String SORT_BY_TIME_DESC = NoteSQLQueryFormer.formOrderByQuery("timeInMillis", "DESC");


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addNote(PlainNote note);
    @Delete
    void delete(PlainNote note);
    @Query("SELECT * FROM note_table ORDER BY id DESC")
    LiveData<List<PlainNote>> readAllData();
    @RawQuery(observedEntities = PlainNote.class)
    LiveData<List<PlainNote>> readSortedDataByQuery(SupportSQLiteQuery query);
    @RawQuery(observedEntities = PlainNote.class)
    List<PlainNote> getSortedDataByQuery(SupportSQLiteQuery query);



    @Query("DELETE FROM note_table WHERE id=:noteId")
    public void deleteById(long noteId);
}
