package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DataBase.RoomTaskDB;
import com.example.myapplication.Module.Task;


public class EditTaskActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etNotes;
    private Button btnSave;
    private Button btnDelete;
    private RoomTaskDB database;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        etTitle = findViewById(R.id.etTitle);
        etNotes = findViewById(R.id.etNotes);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        database = RoomTaskDB.getInstance(this);

        // Получение задачи из интента
        task = (Task) getIntent().getSerializableExtra("task");

        // Проверка, если задача не передана, то создаем новую задачу
        if (task == null) {
            task = new Task();
        }

        // Заполнение полей данными задачи
        etTitle.setText(task.getTaskTitle());
        etNotes.setText(task.getTaskNotes());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Обновление данных задачи
                task.setTaskTitle(etTitle.getText().toString());
                task.setTaskNotes(etNotes.getText().toString());

                // Обновление задачи в базе данных
                database.taskDao().update(task.getID(), task.getTaskTitle(), task.getTaskNotes());

                // Возвращение результата и закрытие активности
                setResult(RESULT_OK);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Удаление задачи из базы данных
                database.taskDao().delete(task);

                // Возвращение результата и закрытие активности
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}