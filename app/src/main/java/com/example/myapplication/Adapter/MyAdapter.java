package com.example.myapplication.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import co.netguru.sectionsDecorator.SectionsAdapterInterface;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements SectionsAdapterInterface {

    private final int orientation;
    private final Map<String, List<String>> items;

    public MyAdapter(int orientation) {
        this.orientation = orientation;
        this.items = new LinkedHashMap<>();
        items.put("8:00", Arrays.asList("breakfast"));
        items.put("9:00", Collections.singletonList("-"));
        items.put("10:00", Arrays.asList("training"));
        items.put("11:00", Arrays.asList("training"));
        items.put("12:00", Arrays.asList("meeting with Ell"));
        items.put("13:00", Arrays.asList("meeting with Ell"));
        items.put("14:00", Arrays.asList("meeting with Ell"));
        items.put("15:00", Arrays.asList("lunch"));
        items.put("16:00", Arrays.asList("work time"));
        items.put("17:00", Arrays.asList("work time"));
        items.put("18:00", Arrays.asList("work time"));
        items.put("19:00", Collections.singletonList("-"));
        items.put("20:00", Collections.singletonList("-"));
        items.put("21:00", Arrays.asList("dinner"));
        items.put("22:00", Arrays.asList("book time"));

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        int layout = (orientation == LinearLayout.HORIZONTAL) ? R.layout.list_item_horizontal : R.layout.list_item_vertical;
        View view = inflater.inflate(layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        List<Map.Entry<String, String>> flatItems = items.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream().map(value -> new AbstractMap.SimpleEntry<>(entry.getKey(), value)))
                .collect(Collectors.toList());
        String sectionNameOfItem = flatItems.get(position).getKey();
        holder.bind(flatItems.get(position).getValue(), getSectionIndex(sectionNameOfItem));
    }

    @Override
    public int getItemCount() {
        return items.values().stream().mapToInt(List::size).sum();
    }

    @Override
    public int getSectionsCount() {
        return items.keySet().size();
    }

    @Override
    public String getSectionTitleAt(int sectionIndex) {
        List<String> sectionKeys = new ArrayList<>(items.keySet());
        return sectionKeys.get(sectionIndex);
    }

    @Override
    public int getItemCountForSection(int sectionIndex) {
        List<String> sectionKeys = new ArrayList<>(items.keySet());
        String sectionKey = sectionKeys.get(sectionIndex);
        return items.getOrDefault(sectionKey, Collections.emptyList()).size();
    }

    private int getSectionIndex(String sectionName) {
        List<String> sectionKeys = new ArrayList<>(items.keySet());
        return sectionKeys.indexOf(sectionName);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text);
        }

        public void bind(String text, int section) {
            if (section % 2 == 0) {
                itemView.setBackgroundColor(Color.GRAY);
            } else {
                itemView.setBackgroundColor(Color.LTGRAY);
            }
            textView.setText(text);
        }
    }
}