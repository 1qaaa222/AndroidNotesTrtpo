package com.example.myapplication.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Module.Task;
import com.example.myapplication.R;
import com.example.myapplication.TaskClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

    public class TaskAdapter extends RecyclerView.Adapter <com.example.myapplication.Adapter.TaskViewHolder> {
        Context context;
        List<Task> list;

        TaskClickListener listener;

        public TaskAdapter(Context context, List<Task> list, TaskClickListener listener) {
            this.context = context;
            this.list = list;
            this.listener = listener;
        }

        @NonNull
        @Override
        public com.example.myapplication.Adapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new com.example.myapplication.Adapter.TaskViewHolder(LayoutInflater.from(context).inflate(R.layout.task_list, parent,false));

        }

        @Override
        public void onBindViewHolder(@NonNull com.example.myapplication.Adapter.TaskViewHolder holder, int position) {

            holder.task_textView_title.setText(list.get(position).getTaskTitle());
            holder.task_textView_title.setSelected(true);

            holder.textView_task.setText(list.get(position).getTaskNotes());

            if(list.get(position).isPinned()){
                holder.task_imageView_pin.setImageResource(R.drawable.task_do);
            }else{
                holder.task_imageView_pin.setImageResource(0);
            }
            int color_code = getRandomColor();
            holder.task_conteiner.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));

            holder.task_conteiner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(list.get(holder.getAdapterPosition()));
                }
            });

            holder.task_conteiner.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongClick(list.get(holder.getAdapterPosition()),holder.task_conteiner);
                    return true;
                }
            });
        }

        private int getRandomColor(){
            List <Integer> colorCode = new ArrayList<>();
            colorCode.add(R.color.color1);
            colorCode.add(R.color.color2);
            colorCode.add(R.color.color3);
            colorCode.add(R.color.color4);
            colorCode.add(R.color.color5);

            Random random = new Random();
            int random_color = random.nextInt(colorCode.size());
            return colorCode.get(random_color);
        }
        @Override
        public int getItemCount() {
            return list.size();
        }
        public void filterList(List<Task> filteredList){
            list = filteredList;
            notifyDataSetChanged();

        }

    }

    class TaskViewHolder extends RecyclerView.ViewHolder {

        CardView task_conteiner;
        TextView task_textView_title, textView_task;
        ImageView task_imageView_pin;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            task_conteiner = itemView.findViewById(R.id.task_conteiner);
            task_textView_title = itemView.findViewById(R.id.task_textView_title);
            textView_task = itemView.findViewById(R.id.textView_task);
            task_imageView_pin = itemView.findViewById(R.id.task_imageView_pin);

        }
    }
