package com.example.myapplication;

import static com.example.myapplication.R.id.editText_title;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.Module.Notes;

public class NotesTakerActivity extends AppCompatActivity {


    EditText editText_title, editText_notes;
    ImageView imageView_save;

    Notes notes;
    boolean isOldNote =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);

        editText_title=findViewById(R.id.editText_title);
        editText_notes=findViewById(R.id.editText_notes);


        imageView_save=findViewById(R.id.imageView_save);
        if(!isOldNote){
            notes=new Notes();
        }
        notes=new Notes();
        try {
            notes = (Notes) getIntent().getSerializableExtra("old_note");
            editText_title.setText(notes.getTitle());
            editText_notes.setText(notes.getNotes());
            isOldNote=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title= "";
            }
        });

    }
}