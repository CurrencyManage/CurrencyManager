package com.hb.currencymanage.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 汪彬 on 2018/4/22.
 */

public class AutoHeightViewPager extends ViewPager
{
    private boolean isCanScroll = true;
    
    public AutoHeightViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // find the current child view
        // and you must cache all the child view
        // use setOffscreenPageLimit(adapter.getCount())
        View view = getChildAt(getCurrentItem());
        if (view != null)
        {
            // measure the current child view with the specified measure spec
            view.measure(widthMeasureSpec, heightMeasureSpec);
        }
        
        setMeasuredDimension(getMeasuredWidth(),
                measureHeight(heightMeasureSpec, view));
    }
    
    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @param view the base view with already measured height
     *
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec, View view)
    {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        
        if (specMode == MeasureSpec.EXACTLY)
        {
            result = specSize;
        }
        else
        {
            // set the height from the base view if available
            if (view != null)
            {
                result = view.getMeasuredHeight();
            }
            if (specMode == MeasureSpec.AT_MOST)
            {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
    
    /**
     * 单独测量view获取尺寸
     *
     * @param view
     */
    public void measeureView(View view)
    {
        
        int intw = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int inth = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        // 重新测量view
        view.measure(intw, inth);
        
        // 以上3句可简写成下面一句
        // view.measure(0,0);
        
        // 获取测量后的view尺寸
        int intwidth = view.getMeasuredWidth();
        int intheight = view.getMeasuredHeight();
    }
    
    /**
     * 设置其是否能滑动换页
     * 
     * @param isCanScroll false 不能换页， true 可以滑动换页
     */
    public void setScanScroll(boolean isCanScroll)
    {
        this.isCanScroll = isCanScroll;
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return isCanScroll && super.onInterceptTouchEvent(ev);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        return isCanScroll && super.onTouchEvent(ev);
        
    }
}