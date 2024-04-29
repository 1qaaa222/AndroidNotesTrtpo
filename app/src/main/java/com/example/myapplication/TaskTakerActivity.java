package com.example.myapplication;



import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.myapplication.Module.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskTakerActivity extends AppCompatActivity {


    EditText task_editText_title, editText_task;
    ImageView task_imageView_save;

    Task tasks;
    boolean isOldTask =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task_taker);
        task_editText_title=findViewById(R.id.task_editText_title);
        editText_task=findViewById(R.id.editText_task);
        task_imageView_save=findViewById(R.id.imageView_save);

        if(!isOldTask){
            tasks=new Task();
        }
        tasks=new Task();
        try {
            tasks = (Task) getIntent().getSerializableExtra("old_task");
            task_editText_title.setText(tasks.getTaskTitle());
            editText_task.setText(tasks.getTaskNotes());
            isOldTask=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        task_imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title= task_editText_title.getText().toString();
                String description= editText_task.getText().toString();

                if(description.isEmpty()){
                    Toast.makeText(TaskTakerActivity.this, "Please. enter description", Toast.LENGTH_SHORT).show();
                    return;
                }

                tasks = new Task();
                tasks.setTaskTitle(title);
                tasks.setTaskNotes(description);

                Intent intent = new Intent();
                intent.putExtra("task", tasks);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }
}
