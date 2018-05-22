package com.hb.currencymanage.myview;

import com.hb.currencymanage.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by gaodesong on 18/5/6.
 */

public class ProgressView extends View{



    private Paint paint;

    private int w,h;

    private int progress;

    private Rect rect;

    private int color;

    public ProgressView(Context context) {
        this(context,null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

    }

    private void init() {

        paint=new Paint();
        paint.setAntiAlias(true);
        //paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.FILL);
        progress=100;
        color= Color.parseColor("#E90101");



    }


    public void setColorAndProgress(String colour,int pro){
        progress=pro;
        color=Color.parseColor(colour);
        invalidate();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

       this.w=w;
       this.h=h;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        w=w*progress/100;
        rect=new Rect(0,0,w,h);
        paint.setColor(color);
        canvas.drawRect(rect,paint);

    }








}
