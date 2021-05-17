package com.quazaru.plannter.database.NoteDatabase;

public class NoteSQLQueryFormer {
    private static String template = "SELECT * FROM note_table ";
    public static String formOrderByQuery(String columnName, String order) {
        return template + "ORDER BY " + columnName + " " + order;
    }
}
