package com.example.myapplication;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class SectionDecorator extends RecyclerView.ItemDecoration {
    private Context context; // Добавленная переменная context
    private Paint linePaint;
    private float lineWidth;

    public SectionDecorator(Context context) {
        this.context = context; // Инициализация переменной context
        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeCap(Paint.Cap.SQUARE);
        linePaint.setStrokeJoin(Paint.Join.MITER);
        linePaint.setColor(ContextCompat.getColor(context, R.color.green));
    }

    public void setLineColor(@ColorRes int colorRes) {
        linePaint.setColor(ContextCompat.getColor(context, colorRes));
    }

    public void setLineWidth(float width) {
        lineWidth = width;
        linePaint.setStrokeWidth(width);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int top = child.getBottom();
            int bottom = top + (int) lineWidth;
            c.drawLine(left, top, right, bottom, linePaint);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(0, 0, 0, (int) lineWidth);
    }
}