package com.example.mangaapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class BlurImageView extends androidx.appcompat.widget.AppCompatImageView {
    Paint rectPaint;

    private int blurcolor= Color.parseColor("#ae9C9C9C");
    public BlurImageView(Context context) {
        this(context, null);

    }

    public BlurImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public BlurImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        rectPaint=new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Paint.Style.FILL);
        rectPaint.setColor(blurcolor);
        invalidate();
    }

    public void setBlurcolor(int blurcolor) {
        this.blurcolor = blurcolor;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i("BlurImageView","canvas");

        canvas.drawRect(getLeft(),0,getRight(),getHeight(),rectPaint);
    }
}
