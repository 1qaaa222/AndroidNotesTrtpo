package com.example.myapplication;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.myapplication.DataBase.CalendarDbHelper;
import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.model.DayContainerModel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.skyhope.eventcalenderlibrary.listener.CalenderDayClickListener;

import android.util.Log;
import android.widget.EditText;

public class NotesCalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalenderTest";
    private CalenderEvent calendarView;
    private CalendarDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        dbHelper = new CalendarDbHelper(this);
        calendarView = findViewById(R.id.calender_event);

        calendarView.initCalderItemClickCallback(new CalenderDayClickListener() {
            @Override
            public void onGetDay(DayContainerModel dayContainerModel) {
                String date = dayContainerModel.getDate();
                if (isNoteAvailable(date)) {
                    openNoteDialog(date);
                } else {
                    createNoteDialog(date);
                }
            }
        });
    }

    private boolean isNoteAvailable(String date) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {CalendarDbHelper.COLUMN_NOTE};
        String selection = CalendarDbHelper.COLUMN_DATE + " = ?";
        String[] selectionArgs = {date};
        Cursor cursor = db.query(CalendarDbHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        boolean noteExists = cursor.getCount() > 0;
        cursor.close();
        return noteExists;
    }

    private void openNoteDialog(String date) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {CalendarDbHelper.COLUMN_NOTE};
        String selection = CalendarDbHelper.COLUMN_DATE + " = ?";
        String[] selectionArgs = {date};
        Cursor cursor = db.query(CalendarDbHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            String note = cursor.getString(cursor.getColumnIndexOrThrow(CalendarDbHelper.COLUMN_NOTE));
            showNoteDialog(date, note);
        }
        cursor.close();
    }

    private void createNoteDialog(final String date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(NotesCalendarActivity.this);
        builder.setTitle("Введите текст");

        final EditText input = new EditText(NotesCalendarActivity.this);
        builder.setView(input);

        builder.setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String note = input.getText().toString();
                saveNoteToDatabase(date, note);
            }
        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void showNoteDialog(String date, final String note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(NotesCalendarActivity.this);
        builder.setTitle("Заметка на " + date);
        builder.setMessage(note);
        builder.setPositiveButton("Редактировать", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editNoteDialog(date, note);
            }
        });
        builder.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void editNoteDialog(final String date, final String currentNote) {
        AlertDialog.Builder builder = new AlertDialog.Builder(NotesCalendarActivity.this);
        builder.setTitle("Редактировать заметку");

        final EditText input = new EditText(NotesCalendarActivity.this);
        input.setText(currentNote);
        builder.setView(input);

        builder.setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updatedNote = input.getText().toString();
                updateNoteInDatabase(date, updatedNote);
            }
        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void saveNoteToDatabase(String date, String note) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CalendarDbHelper.COLUMN_DATE, date);
        values.put(CalendarDbHelper.COLUMN_NOTE, note);
        db.insert(CalendarDbHelper.TABLE_NAME, null, values);
        Log.d(TAG, "Note saved for date: " + date);
    }

    private void updateNoteInDatabase(String date, String note) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CalendarDbHelper.COLUMN_NOTE, note);
        String selection = CalendarDbHelper.COLUMN_DATE + " = ?";
        String[] selectionArgs = {date};
        db.update(CalendarDbHelper.TABLE_NAME, values, selection, selectionArgs);
        Log.d(TAG, "Note updated for date: " + date);
    }
}
