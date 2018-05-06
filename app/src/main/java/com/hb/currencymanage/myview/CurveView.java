package com.hb.currencymanage.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hb.currencymanage.R;

/**
 * Created by gaodesong on 18/5/6.
 */

public class CurveView extends View{



    private Paint paint;

    private Path path=null;

    private int w,h;

    public CurveView(Context context) {
        this(context,null);
    }

    public CurveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CurveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

    }

    private void init() {

        path=new Path();
        paint=new Paint();
        paint.setAntiAlias(true);
        //paint.setStrokeJoin(Paint.Join.ROUND);
        CornerPathEffect cornerPathEffect = new CornerPathEffect(200);
        paint.setPathEffect(cornerPathEffect);
        paint.setStyle(Paint.Style.STROKE);


    }





    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        w=getWidth();
        h=getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(getResources().getColor(R.color.colorAccent));
        path.reset();
        path.moveTo(0,h/2);

        path.lineTo(w/6,h/2-10);
        path.lineTo(w*2/6,h/2-10);
        path.lineTo(w*3/6,h/2+10);
        path.lineTo(w*4/6,h/2-30);
        path.lineTo(w*5/6,h/2+25);

        path.lineTo(w,h/2);

        canvas.drawPath(path,paint);

        paint.setColor(getResources().getColor(R.color.colorPrimary));
        path.reset();
        path.moveTo(0,h/2);

        path.lineTo(w/6,h/2-13);
        path.lineTo(w*2/6,h/2+10);
        path.lineTo(w*3/6,h/2+15);
        path.lineTo(w*4/6,h/2-30);
        path.lineTo(w*5/6,h/2+15);

        path.lineTo(w,h/2);

        canvas.drawPath(path,paint);


        paint.setColor(getResources().getColor(R.color.tv_bg_yellow));
        path.reset();
        path.moveTo(0,h/2);

        path.lineTo(w/6,h/2+20);
        path.lineTo(w*2/6,h/2+10);
        path.lineTo(w*3/6,h/2+15);
        path.lineTo(w*4/6,h/2+30);
        path.lineTo(w*5/6,h/2-25);

        path.lineTo(w,h/2);

        canvas.drawPath(path,paint);



    }








}
