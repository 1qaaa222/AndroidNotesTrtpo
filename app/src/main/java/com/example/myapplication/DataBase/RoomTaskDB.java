package com.example.myapplication.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.Module.Task;

@Database(entities = {Task.class}, version = 2, exportSchema = false)
public abstract class RoomTaskDB extends RoomDatabase {

    private static RoomTaskDB database;
    private static String DATABASE_NAME = "TaskApp";

    public synchronized static RoomTaskDB getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), RoomTaskDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract TaskDAO taskDao();
}
