package com.hb.currencymanage.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hb.currencymanage.R;
import com.orhanobut.logger.Logger;

/**
 * Created by 汪彬 on 2018/5/22.
 */

public class TriangleView extends View
{
    private Paint mPaint;
    
    private Path mPath;
    
    private float mWidth;
    
    public TriangleView(Context context)
    {
        this(context, null, 0);
    }
    
    public TriangleView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }
    
    public TriangleView(Context context, @Nullable AttributeSet attrs,
            int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    
    private void init(Context context, AttributeSet attrs)
    {
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.TriangleView);
        int color = ta.getColor(R.styleable.TriangleView_triangle_color,
                Color.parseColor("#D02C2F"));
        mWidth = ta.getDimension(R.styleable.TriangleView_triangle_width,
                dp2px(context, 8));
        mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPath = new Path();
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Logger.d(getWidth() + " , " + getHeight());
        int centerW = getWidth() / 2;
        int h = getHeight();
        mPath.moveTo(centerW - mWidth / 2, h);
        mPath.lineTo(centerW, 0);
        mPath.lineTo(centerW + mWidth / 2, h);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }
    
    public int dp2px(Context context, float dp)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
