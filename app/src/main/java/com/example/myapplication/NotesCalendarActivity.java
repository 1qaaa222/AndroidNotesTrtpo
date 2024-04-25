package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import com.skyhope.eventcalenderlibrary.CalenderEvent;

import androidx.appcompat.app.AppCompatActivity;

public class NotesCalendarActivity extends AppCompatActivity {

    CalenderEvent calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        calendarView = findViewById(R.id.calender_event);

    }
}