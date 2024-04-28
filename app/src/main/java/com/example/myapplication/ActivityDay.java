package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.MyAdapter;

import co.netguru.sectionsDecorator.SectionDecorator;

public class ActivityDay extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        recyclerView = findViewById(R.id.recycler_day);
        adapter = new MyAdapter(LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(adapter);

        // Установите LinearLayoutManager, если он еще не установлен
        if (recyclerView.getLayoutManager() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }


        SectionDecorator decorator = new SectionDecorator(this);
        decorator.setLineColor(R.color.green);
        decorator.setLineWidth(15f);
        recyclerView.addItemDecoration(decorator);
    }
}
