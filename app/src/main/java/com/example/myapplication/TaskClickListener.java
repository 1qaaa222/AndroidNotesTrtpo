package com.example.myapplication;

import androidx.cardview.widget.CardView;

import com.example.myapplication.Module.Notes;
import com.example.myapplication.Module.Task;

public interface TaskClickListener {

    void onClick (Task tasks);
    void onLongClick(Task tasks, CardView cardView);

}
