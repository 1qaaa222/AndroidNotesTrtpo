package com.example.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.example.myapplication.Adapter.TaskAdapter;

import com.example.myapplication.DataBase.RoomTaskDB;

import com.example.myapplication.Module.Task;


import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recycler;
    Button add_task;
    TaskAdapter adapter;
    RoomTaskDB datab;

    List<Task> task = new ArrayList<>();

    Task selectedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);

        add_task = findViewById(R.id.fab_add_task);

        datab = RoomTaskDB.getInstance(this);
        task = datab.taskDao().getAll();
        ////////////////////////////////////////////////////////////////////

        updateRecycler(task);

        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TaskActivity.this, TaskTakerActivity.class);
                startActivityForResult(intent, 101);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                Task new_task = (Task) data.getSerializableExtra("task");
                datab.taskDao().insert(new_task);
                task.clear();
                task.addAll(datab.taskDao().getAll());
                adapter.notifyDataSetChanged();
            }

        }

        if(requestCode==102){
            if(resultCode == Activity.RESULT_OK){
                Task new_task = (Task)data.getSerializableExtra("task");
                datab.taskDao().update(new_task.getID(), new_task.getTaskTitle(), new_task.getTaskNotes());
                task.clear();
                task.addAll(datab.taskDao().getAll());
                adapter.notifyDataSetChanged();
            }
        }
    }
    private void updateRecycler(List<Task> tasks) {
        recycler = findViewById(R.id.recycler_home); // Добавьте эту строку для инициализации recycler
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        adapter = new TaskAdapter(TaskActivity.this, tasks, taskClickListener);
        recycler.setAdapter(adapter);
    }

    private final TaskClickListener taskClickListener = new TaskClickListener() {
        @Override
        public void onClick(Task tasks) {
            Intent intent =new Intent(TaskActivity.this, TaskTakerActivity.class);
            intent.putExtra("old_task", tasks);
            startActivityForResult(intent,102 );
        }

        @Override
        public void onLongClick(Task tasks, CardView cardView) {

            selectedTask = new Task();
            selectedTask= tasks;
            showPopup (cardView);



        }
    };

    private void showPopup(CardView cardView) {

        PopupMenu popupMenu =new PopupMenu(this,cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_task_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId()==R.id.pin_task) {
            if (selectedTask.isPinned()) {
                datab.taskDao().pin(selectedTask.getID(), false);
                Toast.makeText(TaskActivity.this, "Не выполнена", Toast.LENGTH_SHORT).show();
            } else {
                datab.taskDao().pin(selectedTask.getID(), true);
                Toast.makeText(TaskActivity.this, "Выполнена", Toast.LENGTH_SHORT).show();
            }
            task.clear();
            task.addAll(datab.taskDao().getAll());
            adapter.notifyDataSetChanged();
            return true;
        }
        if(item.getItemId()==R.id.delete) {
            datab.taskDao().delete(selectedTask);
            task.remove(selectedTask);
            adapter.notifyDataSetChanged();
            Toast.makeText(TaskActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}