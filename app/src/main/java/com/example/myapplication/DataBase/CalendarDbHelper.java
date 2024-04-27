package com.example.myapplication.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CalendarDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "calendar.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "CalendarNotes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_NOTE = "note";

    public CalendarDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_NOTE + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public void saveNote(String date, String note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_NOTE, note);
        db.insert(TABLE_NAME, null, values);
    }

    public String getNoteByDate(String date) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {COLUMN_NOTE};
        String selection = COLUMN_DATE + " = ?";
        String[] selectionArgs = {date};
        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        String note = null;
        if (cursor.moveToFirst()) {
            note = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE));
        }
        cursor.close();
        return note;
    }

    public void updateNoteByDate(String date, String note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE, note);
        String selection = COLUMN_DATE + " = ?";
        String[] selectionArgs = {date};
        db.update(TABLE_NAME, values, selection, selectionArgs);
    }
}