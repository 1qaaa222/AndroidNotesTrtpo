package com.example.myapplication.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import static androidx.room.OnConflictStrategy.REPLACE;
import com.example.myapplication.Module.Task;

import java.util.List;

@Dao
public interface TaskDAO {
    @Insert(onConflict = REPLACE)
    void insert(Task task);

    @Query("SELECT * FROM tasks ORDER BY ID DESC")
    List<Task> getAll();

    @Query("UPDATE tasks SET task_title = :title, task_notes = :notes WHERE ID = :id")
    void update(int id, String title, String notes);

    @Delete
    void delete(Task task);

    @Query("UPDATE tasks SET pinned = :pin WHERE ID = :taskId")
    void pin(int taskId, boolean pin);
}